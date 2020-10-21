/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.jersey.tests.e2e.common;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.glassfish.jersey.ExternalProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

public class TestExternalProperties extends JerseyTest {

    private static final String PROXY_HOST = "localhost";
    private static final String PROXY_PORT = "9997";
    private static boolean proxyHit = false;

    private static final String AGENT = "Custom-agent";
    private static final String AGENT_SERVER_URI = "http://localhost:9789/";
    private static final String AGENT_REQUEST_HEADER = "request-agent";

    @Path("resource")
    public static class ProxyTestResource {

        @GET
        public String getOK() {
            return "OK";
        }

    }

    @Override
    protected Application configure() {
        return new ResourceConfig(ProxyTestResource.class);
    }

    @Before
    public void setAgentProperty() {
        System.setProperty(ExternalProperties.HTTP_AGENT, AGENT);
    }

    @Test
    public void testProxy() {
        startHandler(new ProxyHandler(false), 9997);

        System.setProperty(ExternalProperties.HTTP_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.HTTP_PROXY_PORT, PROXY_PORT);
        System.setProperty(ExternalProperties.HTTP_NON_PROXY_HOSTS, "");

        Response response = target("resource").request().get();

        Assert.assertEquals(407, response.getStatus());
    }

    @Test
    public void testNonProxy() {
        startHandler(new ProxyHandler(false), 9997);

        System.setProperty(ExternalProperties.HTTP_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.HTTP_PROXY_PORT, PROXY_PORT);
        System.setProperty(ExternalProperties.HTTP_NON_PROXY_HOSTS, "localhost");

        Response response = target("resource").request().get();

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("OK", response.readEntity(String.class));
        Assert.assertFalse(proxyHit);
    }

    @Test
    public void testHttpAgent() {
        System.getProperties().clear();
        System.setProperty(ExternalProperties.HTTP_AGENT, AGENT);

        startHandler(new AgentHandler(), 9789);

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

    private void startHandler(AbstractHandler abstractHandler, final int port)  {
        Server server = new Server(port);
        server.setHandler(abstractHandler);
        try {
            server.start();
        } catch (Exception e) {
            Logger.getLogger(TestExternalProperties.class.getName())
                    .log(Level.SEVERE, e.getMessage());
        }
    }

    class ProxyHandler extends AbstractHandler {
        @Override
        public void handle(String target,
                           Request baseRequest,
                           HttpServletRequest request,
                           HttpServletResponse response) {
            proxyHit = true;
            response.setStatus(407);
            baseRequest.setHandled(true);
        }

        ProxyHandler(boolean pProxyHit) {
            super();
            proxyHit = pProxyHit;
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

