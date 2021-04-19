package com.libre.boot.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libre.boot.jackson.MappingApiJackson2HttpMessageConverter;
import com.libre.boot.prop.LibreJacksonProperties;
import com.libre.boot.upload.UploadFileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author zhao.cheng
 * @date 2021/3/3 10:22
 */
@EnableWebMvc
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({LibreJacksonProperties.class, UploadFileProperties.class})
@RequiredArgsConstructor
public class LibreWebMvcAutoConfiguration implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final LibreJacksonProperties jacksonProperties;
    private final UploadFileProperties uploadFileProperties;

    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(x -> x instanceof StringHttpMessageConverter || x instanceof AbstractJackson2HttpMessageConverter);
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        converters.add(new ResourceRegionHttpMessageConverter());
        converters.add(new MappingApiJackson2HttpMessageConverter(objectMapper, jacksonProperties));
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
        registrar.registerFormatters(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = uploadFileProperties.getSavePath();
        registry.addResourceHandler(uploadFileProperties.getUploadPathPattern())
                .addResourceLocations("file:" + path + "/");
    }
}
