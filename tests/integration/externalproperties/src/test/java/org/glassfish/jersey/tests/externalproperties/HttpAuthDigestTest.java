package org.glassfish.jersey.tests.externalproperties;


import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.security.UserStore;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpAuthDigestTest {

    private String AUTHENTICATION_INFO = "Authentication-Info";
    private String PROXY_AUTHORIZATION = "Proxy-Authorization";
    private String PROXY_AUTHENTICATION = "Proxy-Authentication";
    private Server server;
    private final int PORT = 9997;
    private final URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(PORT).build();

    @Test
    public void testValidateServer() {
        Response response = ClientBuilder.newClient()
                .target(BASE_URI + "/resource")
                .register(HttpAuthenticationFeature.digest("Bob", "bob'sPassword"))
                .request()
                .get();

        Assert.assertEquals(401, response.getStatus());

        Logger.getLogger(HttpAuthDigestTest.class.getName())
                .log(Level.INFO, String.valueOf(response.getHeaders()));
    }

    @Before
    public void createServer() {
        server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setSecurityHandler(digestAuth());
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloWorldServlet()), "/*");
        try {
            server.start();
        } catch (Exception e) {

        }

    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    private SecurityHandler digestAuth() {
        UserStore userStore = new UserStore();
        userStore.addUser("Bob", Credential.getCredential("bob'sPassword"), new String[]{"user"});

        HashLoginService loginService = new HashLoginService();
        loginService.setUserStore(userStore);
        loginService.setName("Private!");

        Constraint constraint = new Constraint();
        constraint.setName(Constraint.__DIGEST_AUTH);
        constraint.setRoles(new String[]{"user"});
        constraint.setAuthenticate(true);

        ConstraintMapping cm = new ConstraintMapping();
        cm.setConstraint(constraint);
        cm.setPathSpec("/*");

        ConstraintSecurityHandler csh = new ConstraintSecurityHandler();
        csh.setAuthenticator(new BasicAuthenticator());
        csh.setRealmName("myrealm");
        csh.addConstraintMapping(cm);
        csh.setLoginService(loginService);

        return csh;
    }

    private class HelloWorldServlet extends HttpServlet {

        private final String HELLO = "Hello World !";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setStatus(200);
            resp.setContentType("text/plain");
            resp.setContentLength(HELLO.length());
            resp.getWriter().write(HELLO);
        }
    }
}
