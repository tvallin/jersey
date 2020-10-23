package org.glassfish.jersey.tests.externalproperties;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.glassfish.jersey.ExternalProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeepAliveTest extends JerseyTest {

    private static final String SERVER_URI = "http://localhost:9997/";
    private static final String CONNECTION_HEADER = "Connection";
    private static final String RESPONSE_HEADER = "result";
    private static final String FALSE = "false";
    private static final int PORT = 9997;

    @Path("resource")
    public static class Resource {

        @GET
        public String getOK() {
            return "OK";
        }

    }

    @Override
    protected Application configure() {
        return new ResourceConfig(Resource.class);
    }

    @Before
    public void setKeepAliveProperty() {
        System.setProperty(ExternalProperties.HTTP_KEEPALIVE, FALSE);
    }

    @Test
    public void testHttpKeepAlive() {
        startHandler();

        Response response = getClient().target(SERVER_URI).request().get();
        Object result = response.getHeaders().getFirst(RESPONSE_HEADER);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("close", result.toString());
    }

    private void startHandler()  {
        Server server = new Server(PORT);
        server.setHandler(new CustomHandler());
        try {
            server.start();
        } catch (Exception e) {
            Logger.getLogger(HttpAgentTest.class.getName())
                    .log(Level.SEVERE, e.getMessage());
        }
    }

    class CustomHandler extends AbstractHandler {
        @Override
        public void handle(String target,
                           Request baseRequest,
                           HttpServletRequest request,
                           HttpServletResponse response) {

            response.setStatus(200);
            response.addHeader(RESPONSE_HEADER, request.getHeader(CONNECTION_HEADER));
            baseRequest.setHandled(true);
        }
    }

}
