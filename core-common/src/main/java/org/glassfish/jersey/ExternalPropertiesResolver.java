package org.glassfish.jersey;

import javax.ws.rs.core.Configuration;
import java.util.Map;

public class ExternalPropertiesResolver {

    /**
     * Default value of {@link ExternalProperties#HTTP_PROXY_PORT}.
     */
    private static final int HTTP_PROXY_PORT_DEFAULT_VALUE = 8080;

    /**
     * Prevent instantiation
     */
    private ExternalPropertiesResolver() {
    }

    /**
     * Find proxy uri into client runtime configuration. If {@link ExternalProperties#HTTP_PROXY_PORT}
     * is not set, default value is
     *
     * @param config    Client runtime configuration.
     * @return          ProxyUri if exist, null otherwise.
     */
    public static Object resolveProxy(Configuration config, String clientPropertyProxyUri) {
        if (config == null || clientPropertyProxyUri == null)  {
            return null;
        }

        Object proxyUri = config.getProperty(clientPropertyProxyUri);
        if (proxyUri != null) {
            return proxyUri;
        }

        return getProxyUriFromExternalProperties(config.getProperties());
    }

    /**
     * Find proxy uri into client runtime configuration. If {@link ExternalProperties#HTTP_PROXY_PORT}
     * is not set, default value is
     *
     * @param properties    Client runtime configuration.
     * @return              ProxyUri if exist, null otherwise.
     */
    public static String resolveProxy(Map<String, Object> properties, String clientPropertyProxyUri) {
        if (properties == null || clientPropertyProxyUri == null)  {
            return null;
        }

        Object proxyUri = properties.get(clientPropertyProxyUri);
        if (proxyUri instanceof String) {
            return (String) proxyUri;
        }

        return getProxyUriFromExternalProperties(properties);
    }

    private static String getProxyUriFromExternalProperties(Map<String, Object> properties) {
        String proxyHost = ExternalProperties.getValue(
                properties, ExternalProperties.HTTP_PROXY_HOST, String.class);
        int proxyPort = ExternalProperties.getValue(
                properties,
                ExternalProperties.HTTP_PROXY_PORT,
                HTTP_PROXY_PORT_DEFAULT_VALUE,
                Integer.class);

        if (proxyHost != null) {
            return "http://" + proxyHost + ":" + proxyPort;
        }
        return null;
    }
}
