package com.libre.swagger.prop;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.service.AuthorizationScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhao.cheng
 * @date 2021/3/3 16:07
 */
@ConfigurationProperties("libre.swagger")
public class SwaggerProperties {

    /**
     * 是否开启 swagger，默认：true
     */
    private boolean enabled = true;
    /**
     * 标题，默认：XXX服务
     */
    private String title;
    /**
     * 详情，默认：XXX服务
     */
    private String description;
    /**
     * 版本号，默认：V1.0
     */
    private String version = "1.0.0";
    /**
     * 组织名
     */
    private String contactUser;
    /**
     * 组织url
     */
    private String contactUrl;
    /**
     * 组织邮箱
     */
    private String contactEmail;
    /**
     * 全局统一请求头
     */
    private final List<Header> headers = new ArrayList<>();
    /**
     * api key 认证
     **/
    private final Authorization authorization = new Authorization();
    /**
     * oauth2 认证
     */
    private final Oauth2 oauth2 = new Oauth2();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public Oauth2 getOauth2() {
        return oauth2;
    }

    /**
     * securitySchemes 支持方式之一 ApiKey
     */
    public static class Authorization {
        /**
         * 开启Authorization，默认：false
         */
        private Boolean enabled = false;
        /**
         * 鉴权策略ID，对应 SecurityReferences ID，默认：Authorization
         */
        private String name = "Authorization";
        /**
         * 鉴权传递的Header参数，默认：TOKEN
         */
        private String keyName = "TOKEN";
        /**
         * 需要开启鉴权URL的正则，默认：/**
         */
        private List<String> pathPatterns = new ArrayList<>();

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public List<String> getPathPatterns() {
            return pathPatterns;
        }

        public void setPathPatterns(List<String> pathPatterns) {
            this.pathPatterns = pathPatterns;
        }
    }

    /**
     * oauth2 认证
     */
    public static class Oauth2 {
        /**
         * 开启Oauth2，默认：false
         */
        private Boolean enabled = false;
        /**
         * oath2 名称，默认：oauth2
         */
        private String name = "oauth2";
        /**
         * clientId name
         */
        private String clientIdName;
        /**
         * clientSecret name
         */
        private String clientSecretName;
        /**
         * authorize url
         */
        private String authorizeUrl;
        /**
         * token url
         */
        private String tokenUrl;
        /**
         * token name，默认：access_token
         */
        private String tokenName = "access_token";
        /**
         * 授权类型
         */
        private GrantTypes grantType = GrantTypes.AUTHORIZATION_CODE;
        /**
         * oauth2 scope 列表
         */
        private List<AuthorizationScope> scopes = new ArrayList<>();
        /**
         * 需要开启鉴权URL的正则，默认：/**
         */
        private List<String> pathPatterns = new ArrayList<>();

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClientIdName() {
            return clientIdName;
        }

        public void setClientIdName(String clientIdName) {
            this.clientIdName = clientIdName;
        }

        public String getClientSecretName() {
            return clientSecretName;
        }

        public void setClientSecretName(String clientSecretName) {
            this.clientSecretName = clientSecretName;
        }

        public String getAuthorizeUrl() {
            return authorizeUrl;
        }

        public void setAuthorizeUrl(String authorizeUrl) {
            this.authorizeUrl = authorizeUrl;
        }

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getTokenName() {
            return tokenName;
        }

        public void setTokenName(String tokenName) {
            this.tokenName = tokenName;
        }

        public GrantTypes getGrantType() {
            return grantType;
        }

        public void setGrantType(GrantTypes grantType) {
            this.grantType = grantType;
        }

        public List<AuthorizationScope> getScopes() {
            return scopes;
        }

        public void setScopes(List<AuthorizationScope> scopes) {
            this.scopes = scopes;
        }

        public List<String> getPathPatterns() {
            return pathPatterns;
        }

        public void setPathPatterns(List<String> pathPatterns) {
            this.pathPatterns = pathPatterns;
        }
    }

    /**
     * 全局通用请求头
     */
    public static class Header {
        /**
         * 请求头名
         */
        private String name;
        /**
         * 请求头描述
         */
        private String description;
        /**
         * 是否必须，默认：false
         */
        private boolean required = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }
    }

    /**
     * oauth2 认证类型
     */
    public enum GrantTypes {
        /**
         * authorization_code
         */
        AUTHORIZATION_CODE,
        /**
         * client_credentials
         */
        CLIENT_CREDENTIALS,
        /**
         * implicit
         */
        IMPLICIT,
        /**
         * Password
         */
        PASSWORD;
    }

}
