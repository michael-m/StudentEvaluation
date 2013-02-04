package it.unitn.sisl.studenteval;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.jboss.logging.Logger;

public class OpenIdSignOutAction extends ActionSupport{
    static Logger logger = Logger.getLogger(OpenIdAuthAction.class);

    @Override
    public String execute() throws Exception{
        HttpServletRequest httpRequest = (HttpServletRequest)
        ActionContext.getContext().get( ServletActionContext.HTTP_REQUEST);
        if(httpRequest.getSession(true).getAttribute("authenticatedUser") != null){
            httpRequest.getSession(true).setAttribute("authenticatedUser", null);
            Map session = ActionContext.getContext().getSession();
            session.put("loggedin","false");
            return "SUCCESS";
        }
        return "ERROR";
    }
}
