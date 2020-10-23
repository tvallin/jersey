package org.glassfish.jersey.tests.externalproperties;

import org.glassfish.jersey.ExternalProperties;

public class TestResource {

    private static final String PROXY_HOST = "localhost";
    private static final String PROXY_PORT = "9997";
    private static final String PROXY_ANSWER = "Went through proxy";
    private static boolean proxyHit = false;
    public static final String DUMMY = "dummy value";
    public static final String ZERO = "0";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String USERNAME = "username";
    public static final String PASSWORD  = "password";


    private void setSystemProperties() {
        System.setProperty(ExternalProperties.HTTP_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.HTTP_PROXY_PORT, PROXY_PORT);
        System.setProperty(ExternalProperties.HTTP_NON_PROXY_HOSTS, "");

        System.setProperty(ExternalProperties.SOCKS_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.SOCKS_PROXY_PORT, PROXY_PORT);
        System.setProperty(ExternalProperties.SOCKS_PROXY_VERSION, DUMMY);

        System.setProperty(ExternalProperties.HTTP_AGENT, DUMMY);
        System.setProperty(ExternalProperties.HTTP_KEEPALIVE, DUMMY);
        System.setProperty(ExternalProperties.HTTP_MAX_CONNECTIONS, ZERO);
        System.setProperty(ExternalProperties.HTTP_MAX_REDIRECTS, ZERO);
        System.setProperty(ExternalProperties.HTTP_AUTH_DIGEST_VALIDATE_SERVER, TRUE);
        System.setProperty(ExternalProperties.HTTP_AUTH_DIGEST_CNONCE_REPEAT, TRUE);
        System.setProperty(ExternalProperties.HTTP_AUTH_NTLM_DOMAIN, DUMMY);
        System.setProperty(ExternalProperties.HTTP_AUTH_NTLM_DOMAIN, DUMMY);
        System.setProperty(ExternalProperties.HTTP_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.HTTP_PROXY_PORT, PROXY_PORT);
        System.setProperty(ExternalProperties.HTTP_NON_PROXY_HOSTS, DUMMY);

        System.setProperty(ExternalProperties.HTTPS_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.HTTPS_PROXY_PORT, PROXY_PORT);

        System.setProperty(ExternalProperties.FTP_PROXY_HOST, PROXY_HOST);
        System.setProperty(ExternalProperties.FTP_PROXY_PORT, PROXY_PORT);
        System.setProperty(ExternalProperties.FTP_NON_PROXY_HOSTS, DUMMY);

        System.setProperty(ExternalProperties.JAVA_NET_SOCKS_USERNAME, USERNAME);
        System.setProperty(ExternalProperties.JAVA_NET_SOCKS_PASSWORD, PASSWORD);
        System.setProperty(ExternalProperties.JAVA_NET_USE_SYSTEM_PROXIES, TRUE);

        System.setProperty(ExternalProperties.NETWORK_ADDRESS_CACHE_TTL, ZERO);
        System.setProperty(ExternalProperties.NETWORK_ADDRESS_CACHE_NEGATIVE_TTL, ZERO);

    }
}