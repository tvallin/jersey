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

package org.glassfish.jersey;

import org.glassfish.jersey.internal.util.PropertiesClass;

@PropertiesClass
public final class ExternalProperties {

    /**
     * Property used to specify the hostname, or address, of the proxy server.
     */
    public static final String HTTP_PROXY_HOST = "http.proxyHost";

    /**
     * Property used to specify the port number of the proxy server.
     */
    public static final String HTTP_PROXY_PORT = "http.proxyPort";

    /**
     * Property used to indicates the hosts that should be accessed
     * without going through the proxy.
     */
    public static final String HTTP_NON_PROXY_HOSTS = "http.nonProxyHosts";

    /**
     * Property used to define the string sent in the User-Agent
     * request header in http requests.
     */
    public static final String HTTP_AGENT = "http.agent";

    /**
     * Property used to indicates if persistent connections should be supported.
     */
    public static final String HTTP_KEEPALIVE = "http.keepalive";

    /**
     * If HTTP keepalive is enabled this value determines
     * the maximum number of idle connections that will be simultaneously
     * kept alive, per destination.
     */
    public static final String HTTP_MAX_CONNECTIONS = "http.maxConnections";

    /**
     * This integer value determines the maximum number, for a given request,
     * of HTTP redirects that will be automatically followed by the protocol handler.
     */
    public static final String HTTP_MAX_REDIRECTS = "http.maxRedirects";

    /**
     * Property used to enforce authentication with an origin.
     */
    public static final String HTTP_AUTH_DIGEST_VALIDATE_SERVER = "http.auth.digest.validateServer";

    /**
     * Property used to enforce authentication with a proxy server.
     */
    public static final String HTTP_AUTH_DIGEST_VALIDATE_PROXY = "http.auth.digest.validateProxy";

    /**
     * This property determines how many times a cnonce value is re-used.
     */
    public static final String HTTP_AUTH_DIGEST_CNONCE_REPEAT = "http.auth.digest.cnonceRepeat";

    /**
     * NTLM is another authentication scheme. It uses the java.net.Authenticator class
     * to acquire usernames and passwords when they are needed.
     */
    public static final String HTTP_AUTH_NTLM_DOMAIN = "http.auth.ntlm.domain";

    /**
     * Property used to specify the hostname, or address, of the proxy server
     * used by https protocol.
     */
    public static final String HTTPS_PROXY_HOST = "https.ProxyHost";

    /**
     * Property used to specify the port number of the proxy server
     * used by https protocol.
     */
    public static final String HTTPS_PROXY_PORT = "https.ProxyPort";

    /**
     * Property used to specify the hostname, or address, of the proxy server
     * used by the FTP protocol handler.
     */
    public static final String FTP_PROXY_HOST = "ftp.proxyHost";

    /**
     * Property used to specify the port number of the proxy server
     * used by the FTP protocol handler.
     */
    public static final String FTP_PROXY_PORT = "ftp.proxyPort";

    /**
     * Property used by the FTP protocol handler to indicates the hosts
     * that should be accessed without going through the proxy.
     */
    public static final String FTP_NON_PROXY_HOSTS = "ftp.nonProxyHosts";

    /**
     * Property used to specify the hostname, or address, of the proxy server.
     */
    public static final String SOCKS_PROXY_HOST = "socksProxyHost";

    /**
     * Property used to specify the port number of the proxy server.
     */
    public static final String SOCKS_PROXY_PORT = "socksProxyPort";

    /**
     * Property used to specify the version of the SOCKS protocol
     * supported by the server.
     */
    public static final String SOCKS_PROXY_VERSION = "socksProxyVersion";

    /**
     * Username to use if the SOCKSv5 server asks for authentication
     * and no java.net.Authenticator instance was found.
     */
    public static final String JAVA_NET_SOCKS_USERNAME = "java.net.socks.username";

    /**
     * Password to use if the SOCKSv5 server asks for authentication
     * and no java.net.Authenticator instance was found.
     */
    public static final String JAVA_NET_SOCKS_PASSWORD = "java.net.socks.password";

    /**
     * On recent Windows systems and on Gnome 2.x systems it is possible to tell the java.net stack,
     * setting this property to true, to use the system proxy settings.
     */
    public static final String JAVA_NET_USE_SYSTEM_PROXIES = "java.net.useSystemProxies";

    /**
     * Value is an integer corresponding to the number of seconds
     * successful name lookups will be kept in the cache.
     */
    public static final String NETWORK_ADDRESS_CACHE_TTL = "networkaddress.cache.ttl";

    /**
     * Value is an integer corresponding to the number of seconds
     * an unsuccessful name lookup will be kept in the cache.
     */
    public static final String NETWORK_ADDRESS_CACHE_NEGATIVE_TTL = "networkaddress.cache.negative.ttl";

    /**
     * The name of the property that contains the name of the class capable
     * of creating new {@code JAXBContext} objects.
     */
    public static final String JAXB_CONTEXT_FACTORY = "jakarta.xml.bind.JAXBContextFactory";

    /**
     * The name of the property used to specify the output encoding in
     * the marshalled XML data.
     */
    public static final String JAXB_ENCODING = "jaxb.encoding";

    /**
     * The name of the property used to specify whether or not the marshalled
     * XML data is formatted with linefeeds and indentation.
     */
    public static final String JAXB_FORMATTED_OUTPUT = "jaxb.formatted.output";

    /**
     * The name of the property used to specify the xsi:schemaLocation
     * attribute value to place in the marshalled XML output.
     */
    public static final String JAXB_SCHEMA_LOCATION = "jaxb.schemaLocation";

    /**
     * The name of the property used to specify the
     * xsi:noNamespaceSchemaLocation attribute value to place in the marshalled
     * XML output.
     */
    public static final String JAXB_NO_NAMESPACE_SCHEMA_LOCATION = "jaxb.noNamespaceSchemaLocation";

    /**
     * The name of the property used to specify whether or not the marshaller
     * will generate document level events (ie calling startDocument or endDocument).
     */
    public static final String JAXB_FRAGMENT = "jaxb.fragment";

    /**
     * Property used to specify whether or not the serialized
     * JSON data is formatted with line feeds and indentation.
     */
    public static final String JSONB_FORMATTING = "jsonb.formatting";

    /**
     * The Jsonb serialization {@code toJson()} methods will default to this property
     * for encoding of output JSON data. Default value is 'UTF-8'.
     *
     * The Jsonb deserialization {@code fromJson()} methods will default to this
     * property encoding of input JSON data if the encoding cannot be detected
     * automatically.
     */
    public static final String JSONB_ENCODING = "jsonb.encoding";

    /**
     * Property used to specify custom naming strategy.
     */
    public static final String JSONB_PROPERTY_NAMING_STRATEGY = "jsonb.property-naming-strategy";

    /**
     * Property used to specify custom order strategy.
     */
    public static final String JSONB_PROPERTY_ORDER_STRATEGY = "jsonb.property-order-strategy";

    /**
     * Property used to specify null values serialization behavior.
     */
    public static final String JSONB_NULL_VALUES = "jsonb.null-values";

    /**
     * Property used to specify strict I-JSON serialization compliance.
     */
    public static final String JSONB_STRICT_IJSON = "jsonb.strict-ijson";

    /**
     * Property used to specify custom visibility strategy.
     */
    public static final String JSONB_PROPERTY_VISIBILITY_STRATEGY = "jsonb.property-visibility-strategy";

    /**
     * Property used to specify custom mapping adapters for generic types.
     */
    public static final String JSONB_ADAPTERS = "jsonb.adapters";

    /**
     * Property used to specify custom serializers.
     */
    public static final String JSONB_SERIALIZERS = "jsonb.serializers";

    /**
     * Property used to specify custom deserializers.
     */
    public static final String JSONB_DESERIALIZERS = "jsonb.derializers";

    /**
     * Property used to specify custom binary data strategy.
     */
    public static final String JSONB_BINARY_DATA_STRATEGY = "jsonb.binary-data-strategy";

    /**
     * Property used to specify custom date format globally.
     */
    public static final String JSONB_DATE_FORMAT = "jsonb.date-format";

    /**
     * Property used to specify locale globally.
     */
    public static final String JSONB_LOCALE = "jsonb.locale";

    /**
     * Prevent instantiation.
     */
    private ExternalProperties() {
    }

}