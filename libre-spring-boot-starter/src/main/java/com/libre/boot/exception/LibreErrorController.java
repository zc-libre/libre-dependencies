package com.libre.boot.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhao.cheng
 * @date 2021/4/19 14:02
 */
public class LibreErrorController extends BasicErrorController {

    private final ObjectMapper objectMapper;

    public LibreErrorController(ObjectMapper objectMapper,
                               ErrorAttributes errorAttributes,
                               ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
        this.objectMapper = objectMapper;
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        HttpStatus status = getStatus(request);
        response.setStatus(status.value());
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setObjectMapper(objectMapper);
        view.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new ModelAndView(view, body);
    }
}
