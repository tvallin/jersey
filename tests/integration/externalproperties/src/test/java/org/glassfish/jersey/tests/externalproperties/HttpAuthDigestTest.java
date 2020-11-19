package org.glassfish.jersey.tests.externalproperties;


import org.glassfish.jersey.ExternalProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpAuthDigestTest extends JerseyTest {

    private String AUTHENTICATION_INFO = "Authentication-Info";
    private String PROXY_AUTHORIZATION = "Proxy-Authorization";
    private String PROXY_AUTHENTICATION = "Proxy-Authentication";

    @Path("resource")
    public static class MyResource {

        @Context
        SecurityContext securityContext;

        @GET
        public String digest() {
            return securityContext.getAuthenticationScheme() + ":" + securityContext.getUserPrincipal().getName();
        }
    }

    public static class DigestFilter implements ContainerRequestFilter {

        @Override
        public void filter(ContainerRequestContext requestContext) throws IOException {
            final String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            if (authorization != null && authorization.trim().toUpperCase().startsWith("DIGEST")) {
                final Matcher match = Pattern.compile("username=\"([^\"]+)\"").matcher(authorization);
                if (!match.find()) {
                    return;
                }
                final String username = match.group(1);

                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return new Principal() {
                            @Override
                            public String getName() {
                                return username;
                            }
                        };
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return false;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "DIGEST";
                    }
                });
                return;
            }
            requestContext.abortWith(Response.status(401).header(HttpHeaders.WWW_AUTHENTICATE,
                    "Digest realm=\"my-realm\", domain=\"\", nonce=\"n9iv3MeSNkEfM3uJt2gnBUaWUbKAljxp\", algorithm=MD5, "
                            + "qop=\"auth\", stale=false")
                    .build());
        }
    }

    @Override
    protected Application configure() {
        ResourceConfig config = new ResourceConfig(MyResource.class);
        config.register(new DigestFilter());
        return config;
    }

    @Test
    public void testValidateServer() {
        System.setProperty(ExternalProperties.HTTP_AUTH_DIGEST_VALIDATE_SERVER, "true");
        System.setProperty(ExternalProperties.HTTP_AUTH_DIGEST_VALIDATE_PROXY, "true");

        Response response = target("resource")
                .register(HttpAuthenticationFeature.digest("Bob", "bob'sPassword"))
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("DIGEST:Bob", response.readEntity(String.class));

        //Logger.getLogger(HttpAuthDigestTest.class.getName())
        //        .log(Level.INFO, String.valueOf(response.getHeaders().getFirst(AUTHENTICATION_INFO)));
    }
}
