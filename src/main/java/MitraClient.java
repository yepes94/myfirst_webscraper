import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;

public class MitraClient{
        //Create a new WebClient with any BrowserVersion. WebClient belongs to the
        //HtmlUnit library.
        private final WebClient WEB_CLIENT = new WebClient(BrowserVersion.CHROME);

        //This is pretty self explanatory, these are your credentials.
        private final String username;
        private final String password;

        //Our constructor. Sets our username and password and does some client config.
        public MitraClient(String username, String password){
            this.username = username;
            this.password = password;

            //Retreives our WebClient's cookie manager and enables cookies.
            //This is what allows us to view pages that require login.
            //If this were set to false, the login session wouldn't persist.
            WEB_CLIENT.getCookieManager().setCookiesEnabled(true);
        }

        public void login(){
            //This is the URL where we log in, easy.
            String loginURL = "https://mitra.upc.es/SIA/ACCES_PERFIL.AUTENTIFICAR?v_procediment=MAPA.INICI&v_comentari=Recordeu%20que%20la%20informaci%26oacute;%20que%20cont%26eacute;%20aquesta%20intranet%20est%26aacute;%20protegida%20per%20la%20Llei%20de%20Protecci%26oacute;%20de%20Dades,%20de%20manera%20que%20sota%20cap%20sup%26ograve;sit%20pot%20ser%20facilitada%20a%20tercers.";
            try {

                //Okay, bare with me here. This part is simple but it can be tricky
                //to understand at first. Reference the login form above and follow
                //along.

                //Create an HtmlPage and get the login page.
                HtmlPage loginPage = WEB_CLIENT.getPage(loginURL);

                //Create an HtmlForm by locating the form that pertains to logging in.
                //"//form[@id='login-form']" means "Hey, look for a <form> tag with the
                //id attribute 'login-form'" Sound familiar?
                //<form id="login-form" method="post" ...
                HtmlForm loginForm = loginPage.getFirstByXPath("//form[@action='/SIA/ACCES_PERFIL.AUTENTIFICAR2']");

                //This is where we modify the form. The getInputByName method looks
                //for an <input> tag with some name attribute. For example, user or passwd.
                //If we take a look at the form, it all makes sense.
                //<input value="" name="user" id="user_login" ...
                //After we locate the input tag, we set the value to what belongs.
                //So we're saying, "Find the <input> tags with the names "user" and "passwd"
                //and throw in our username and password in the text fields.
                loginForm.getInputByName("v_username").setValueAttribute(username);
                loginForm.getInputByName("v_password").setValueAttribute(password);

                loginForm.getInputByValue("Identifica").click();

            } catch (FailingHttpStatusCodeException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String get(String URL){
            try {
                //All this method does is return the HTML response for some URL.
                //We'll call this after we log in!
                return WEB_CLIENT.getPage(URL).getWebResponse().getContentAsString();
            } catch (FailingHttpStatusCodeException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
}

