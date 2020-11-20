package org.glassfish.jersey.tests.externalproperties;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.ExternalProperties;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class HttpsProxyTest extends JerseyTest {

    private HttpServer proxy;
    private URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9997).build();
    private boolean proxyHit;

    @Path("resource")
    public static class MyResource {

        @GET
        public Response getMessage() {
            return Response.ok("Hello World").build();
        }
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(MyResource.class);
    }

    @Test
    public void testHitProxy() {
        System.setProperty(ExternalProperties.HTTP_NON_PROXY_HOSTS, "");
        Response response = target("resource").request().get();

        Assert.assertEquals(407, response.getStatus());
        Assert.assertTrue(proxyHit);
    }

    @Test
    public void testNonProxyHost() {
        System.setProperty(ExternalProperties.HTTP_NON_PROXY_HOSTS, "localhost");
        Response response = target("resource").request().get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("Hello World", response.readEntity(String.class));
        Assert.assertFalse(proxyHit);
    }

    @Before
    public void startFakeProxy() {
        System.setProperty(ExternalProperties.HTTPS_PROXY_HOST, "localhost");
        System.setProperty(ExternalProperties.HTTPS_PROXY_PORT, "9997");

        proxy = GrizzlyHttpServerFactory.createHttpServer(BASE_URI);
        Runtime.getRuntime().addShutdownHook(new Thread(proxy::shutdownNow));
        proxy.getServerConfiguration().addHttpHandler(new ProxyHandler());

        try {
            proxy.start();
        } catch (IOException ioe) {

        }

    }

    @After
    public void stopFakeProxy() {
        proxy.shutdownNow();
    }

    private class ProxyHandler extends HttpHandler {

        @Override
        public void service(Request request,
                            org.glassfish.grizzly.http.server.Response response) throws Exception {
            proxyHit = true;
            response.setStatus(407);
        }

        ProxyHandler() {
            super();
            proxyHit = false;
        }
    }
}
