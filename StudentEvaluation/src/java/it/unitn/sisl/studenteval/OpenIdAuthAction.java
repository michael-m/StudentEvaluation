package it.unitn.sisl.studenteval;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.jboss.logging.Logger;

public class OpenIdAuthAction extends ActionSupport {
    static Logger logger = Logger.getLogger(OpenIdAuthAction.class);
    //The idenfier the user has chosen
    private String openid_identifier;
    //A hibernate session
    private Session hibernateSession;
    //Servlet Request and Response objects
    private HttpServletRequest request;
    private HttpServletResponse response;
    //A session for storing User object
    Map<String, Object> httpSession;
    //Application information to be used by OpenId4Java
    Map<String, Object> studentEvalApp;
    //Return action which handles the OpenId provider's response
    private final String RETURN_ACTION = "/getAuthenticatedUser";
    private String returnUrl;
    private User authenticatedUser;

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }
    
    public OpenIdAuthAction() {
        httpSession = new HashMap<String, Object>();
        studentEvalApp = new HashMap<String, Object>();
    }
    
    public String getOpenid_identifier() {
        return openid_identifier;
    }

    public void setOpenid_identifier(String openid_identifier) {
        this.openid_identifier = openid_identifier;
    }

    public Session getHibernateSession() {
        return hibernateSession;
    }

    public void setHibernateSession(Session hibernateSession) {
        this.hibernateSession = hibernateSession;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Map<String, Object> getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(Map<String, Object> httpSession) {
        this.httpSession = httpSession;
    }

    public Map<String, Object> getStudentEvalApp() {
        return studentEvalApp;
    }

    public void setStudentEvalApp(Map<String, Object> studentEvalApp) {
        this.studentEvalApp = studentEvalApp;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    //OpenId selector passes the choice to this method
    public String validateOpenId() throws Exception {
        logger.debug("Entering validateOpenId()");
        setOpenid_identifier("https://www.google.com/accounts/o8/id");
        //setOpenid_identifier("https://idp.unitn.it/idp/Authn/UserPassword");
        // get rid of trailing slash
//        if (getOpenid_identifier().endsWith("/")) {
//            setOpenid_identifier(getOpenid_identifier().substring(0, getOpenid_identifier()
//                    .length() - 1));
//        }
        System.out.println(getOpenid_identifier());
        logger.debug("The requested OpenId identifier is: " + getOpenid_identifier());
        setRequest((HttpServletRequest)
                ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST));
        //Get the request
        // determine a return_to URL where the application will receive
        // the authentication responses from the OpenID provider
        setReturnUrl(getServerContext(getRequest()) + RETURN_ACTION);
        System.out.println(returnUrl);
        // construct the destination Url to send to the Open Id provider
        String destinationUrl = 
           OpenIdAuthenticator.getValidateOpenIdUrl
           (returnUrl, this.getOpenid_identifier(), getHttpSession(), getStudentEvalApp()); 

        setResponse((HttpServletResponse)
                ActionContext.getContext().get( ServletActionContext.HTTP_RESPONSE));
        // redirect to the Auth Request
        
        if(response == null){
            System.out.println("empty response");
        }
        response.sendRedirect(destinationUrl);

        // no need to return a view
        return NONE;
    }
    private String getServerContext(final HttpServletRequest request){
        // Get the base url.
        final StringBuilder serverPath = new StringBuilder();
        if(request == null){
            System.out.println("empty request.");
        }
        serverPath.append(request.getScheme()).append("://");
        serverPath.append(request.getServerName());

        if (request.getServerPort() != 80) {
            serverPath.append(":").append(request.getServerPort());
        }
        serverPath.append(request.getContextPath());
        
        return serverPath.toString();
    }
    public String verifyResponse(){
        HttpServletRequest httpRequest = (HttpServletRequest)
                ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        Map<String, String[]> httpResponse = httpRequest.getParameterMap();
        try{
            setAuthenticatedUser(OpenIdAuthenticator.getAuthenticatedUser
                    (httpResponse, httpRequest.getRequestURL(), httpSession, studentEvalApp));
        }catch(Exception e){
        }
        if(getAuthenticatedUser() != null){
            Map session = ActionContext.getContext().getSession();
            httpRequest.getSession().setAttribute("authenticatedUser", getAuthenticatedUser());
            session.put("loggedin","true");
            return "SUCCESS";
        }
        return "ERROR";
    }
    @Override
    public String execute() throws Exception{
        HttpServletRequest httpRequest = (HttpServletRequest)
                ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        setAuthenticatedUser((User)httpRequest.getSession().getAttribute("authenticatedUser"));
        if(getAuthenticatedUser() != null){
            return "SUCCESS";
        }
        return validateOpenId();
    }
    
}
