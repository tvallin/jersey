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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpAgentTest extends JerseyTest {

    private static final String AGENT = "Custom-agent";
    private static final String AGENT_SERVER_URI = "http://localhost:9997/";
    private static final String AGENT_REQUEST_HEADER = "request-agent";
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
    public void setAgentProperty() {
        System.setProperty(ExternalProperties.HTTP_AGENT, AGENT);
    }

    @Test
    public void testHttpAgent() {
        System.getProperties().clear();
        System.setProperty(ExternalProperties.HTTP_AGENT, AGENT);

        startAgentHandler();

        Response response = getClient().target(AGENT_SERVER_URI).request().get();
        Object result = response.getHeaders().getFirst(AGENT_REQUEST_HEADER);
        Assert.assertEquals(200, response.getStatus());

        String[] agentHeaders = result.toString().split(" ");
        if (agentHeaders.length == 0) {
            Assert.fail();
        } else {
            Assert.assertEquals(AGENT, agentHeaders[0]);
        }

    }

    private void startAgentHandler()  {
        Server server = new Server(PORT);
        server.setHandler(new AgentHandler());
        try {
            server.start();
        } catch (Exception e) {
            Logger.getLogger(HttpAgentTest.class.getName())
                    .log(Level.SEVERE, e.getMessage());
        }
    }

    class AgentHandler extends AbstractHandler {
        @Override
        public void handle(String target,
                           Request baseRequest,
                           HttpServletRequest request,
                           HttpServletResponse response) {

            response.setStatus(200);
            response.addHeader(AGENT_REQUEST_HEADER, request.getHeader(HttpHeaders.USER_AGENT));
            baseRequest.setHandled(true);
        }
    }
}
