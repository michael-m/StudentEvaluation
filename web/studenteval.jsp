<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>

    <s:actionerror theme="jquery" />
    <s:fielderror theme="jquery" />
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                    <title>Student Evaluation Application - Sign in</title>
                    <!-- Simple OpenID Selector -->
                    <link type="text/css" rel="stylesheet" href="css/openid.css" />
                    <script type="text/javascript" src="js/jquery-1.2.6.min.js"></script>
                    <script type="text/javascript" src="js/openid-jquery.js"></script>
                    <script type="text/javascript" src="js/openid-en.js"></script>
                    <script type="text/javascript">
                            $(document).ready(function() {
                                    openid.init('openid_identifier');
                                    //openid.setDemoMode(true); //Stops form submission for client javascript-only test purposes
                            });
                    </script>
                    <!-- /Simple OpenID Selector -->
                    <style type="text/css">
                            /* Basic page formatting */
                            body {
                                    font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
                            }
                    </style>            
        </head>
        <body>
                <h2 style="text-align:center;">Student Evaluation Application</h2>
            <br/>
            <!-- Simple OpenID Selector -->
            <div style="vertical-align: middle;margin-left: 25%;">
                <s:form action="doOpenIdAuth" namespace="/" method="post" id="openid_form">
<!--                    <input type="hidden" name="action" value="https://www.google.com/accounts/o8/id"/>-->
                    <fieldset style="text-align:center;">
                        <legend>Sign in using your Google account</legend>
<!--                        <div id="openid_choice">
                            <p>Please pick your account provider:</p>
                            <div id="openid_btns"></div>
                        </div>
                        <div id="openid_input_area">
                            <input id="openid_identifier" name="openid_identifier" type="text"
                                   value="http://" />
                            <input id="openid_submit" type="submit" value="Sign-In"/>
                        </div>-->
                        <div>
                            <input type="image" value="https://www.google.com/accounts/o8/id"
                                   src="google-large.jpg" style="width: 32%;height: 17%" />
                        </div>
<!--                        <noscript>
                            <p>OpenID is service that allows you to log-on to many different
                                web sites using a single identity.
                            Find out <a href="http://openid.net/what/">more about OpenID</a>
                            and <a href="http://openid.net/get/">how to get an OpenID enabled
                                account</a>.</p>
                        </noscript>-->
                    </fieldset>
                </s:form>
            </div>
        </body>
    </html>