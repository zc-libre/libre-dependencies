package com.libre.swagger.config;

import com.libre.swagger.prop.SwaggerProperties;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhao.cheng
 * @date 2021/3/3 16:03
 */

@EnableOpenApi
@ConditionalOnClass(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class SwaggerAutoConfiguration {

    @Bean
    public Docket createRestApi(Environment environment,
                                SwaggerProperties properties) {
        String appName = environment.getProperty("spring.application.name");

        Docket docket = new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .globalRequestParameters(globalHeaders(properties))
                .apiInfo(apiInfo(appName, properties)).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
        // 如果开启 apiKey 认证
        if (Boolean.TRUE.equals(properties.getAuthorization().getEnabled())) {
            SwaggerProperties.Authorization authorization = properties.getAuthorization();
            docket.securitySchemes(Collections.singletonList(apiKey(authorization)));
            docket.securityContexts(Collections.singletonList(apiKeySecurityContext(authorization)));
        }
        // 如果开启 oauth2 认证
        if (Boolean.TRUE.equals(properties.getOauth2().getEnabled())) {
            SwaggerProperties.Oauth2 oauth2 = properties.getOauth2();
            docket.securitySchemes(Collections.singletonList(oauth2(oauth2)));
            docket.securityContexts(Collections.singletonList(oauth2SecurityContext(oauth2)));
        }
        return docket;
    }

    /**
     * 配置基于 ApiKey 的鉴权对象
     * @return {ApiKey}
     */
    private ApiKey apiKey(SwaggerProperties.Authorization authorization) {
        return new ApiKey(authorization.getName(), authorization.getKeyName(), ApiKeyVehicle.HEADER.getValue());
    }

    /**
     * 配置默认的全局鉴权策略的开关，以及通过正则表达式进行匹配；默认 /** 匹配所有URL
     * 其中 securityReferences 为配置启用的鉴权策略
     *
     * @return {SecurityContext}
     */
    private SecurityContext apiKeySecurityContext(SwaggerProperties.Authorization authorization) {
        final AntPathMatcher matcher = new AntPathMatcher();
        final List<String> pathPatterns = new ArrayList<>(authorization.getPathPatterns());
        if (pathPatterns.isEmpty()) {
            pathPatterns.add("/**");
        }
        return SecurityContext.builder()
                .securityReferences(apiKeyAuth(authorization))
                .operationSelector(context -> {
                    String mappingPattern = context.requestMappingPattern();
                    return pathPatterns.stream().anyMatch(patterns -> matcher.match(patterns, mappingPattern));
                })
                .build();
    }

    /**
     * 配置默认的全局鉴权策略；其中返回的 SecurityReference 中，reference 即为ApiKey对象里面的name，保持一致才能开启全局鉴权
     *
     * @return {List<SecurityReference>}
     */
    private List<SecurityReference> apiKeyAuth(SwaggerProperties.Authorization authorization) {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(SecurityReference.builder()
                .reference(authorization.getName())
                .scopes(authorizationScopes).build());
    }

    private OAuth oauth2(SwaggerProperties.Oauth2 oauth2) {
        SwaggerProperties.GrantTypes grantTypes = oauth2.getGrantType();
        GrantType grantType = null;
        // 授权码模式
        if (SwaggerProperties.GrantTypes.AUTHORIZATION_CODE == grantTypes) {
            TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpointBuilder()
                    .url(oauth2.getAuthorizeUrl())
                    .clientIdName(oauth2.getClientIdName())
                    .clientSecretName(oauth2.getClientSecretName())
                    .build();
            TokenEndpoint tokenEndpoint = new TokenEndpointBuilder()
                    .url(oauth2.getTokenUrl())
                    .tokenName(oauth2.getTokenName())
                    .build();
            grantType = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);
        } else if (SwaggerProperties.GrantTypes.CLIENT_CREDENTIALS == grantTypes) {
            grantType = new ClientCredentialsGrant(oauth2.getTokenUrl());
        } else if (SwaggerProperties.GrantTypes.IMPLICIT == grantTypes) {
            LoginEndpoint loginEndpoint = new LoginEndpoint(oauth2.getAuthorizeUrl());
            grantType = new ImplicitGrant(loginEndpoint, oauth2.getTokenName());
        } else if (SwaggerProperties.GrantTypes.PASSWORD == grantTypes) {
            grantType = new ResourceOwnerPasswordCredentialsGrant(oauth2.getTokenUrl());
        }
        return new OAuthBuilder()
                .name(oauth2.getName())
                .grantTypes(Collections.singletonList(grantType))
                .build();
    }

    private SecurityContext oauth2SecurityContext(SwaggerProperties.Oauth2 oauth2) {
        List<AuthorizationScope> scopes = new ArrayList<>();
        List<AuthorizationScope> oauth2Scopes = oauth2.getScopes();
        for (AuthorizationScope oauth2Scope : oauth2Scopes) {
            scopes.add(new AuthorizationScope(oauth2Scope.getScope(), oauth2Scope.getDescription()));
        }
        SecurityReference securityReference = new SecurityReference(oauth2.getName(), scopes.toArray(new AuthorizationScope[0]));
        final List<String> pathPatterns = new ArrayList<>(oauth2.getPathPatterns());
        if (pathPatterns.isEmpty()) {
            pathPatterns.add("/**");
        }
        final AntPathMatcher matcher = new AntPathMatcher();
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(securityReference))
                .operationSelector(context -> {
                    String mappingPattern = context.requestMappingPattern();
                    return pathPatterns.stream().anyMatch(patterns -> matcher.match(patterns, mappingPattern));
                })
                .build();
    }

    private ApiInfo apiInfo(@Nullable String appName, SwaggerProperties properties) {
        String defaultName = (appName == null ? "" : appName) + "服务";
        String title = Optional.ofNullable(properties.getTitle())
                .orElse(defaultName);
        String description = Optional.ofNullable(properties.getDescription())
                .orElse(defaultName);
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(properties.getVersion())
                .contact(new Contact(properties.getContactUser(), properties.getContactUrl(), properties.getContactEmail()))
                .build();
    }

    private List<RequestParameter> globalHeaders(SwaggerProperties properties) {
        return properties.getHeaders().stream()
                .map(header ->
                        new RequestParameterBuilder()
                                .in(ParameterType.HEADER)
                                .name(header.getName())
                                .description(header.getDescription())
                                .required(header.isRequired())
                                .build())
                .collect(Collectors.toList());
    }
}
