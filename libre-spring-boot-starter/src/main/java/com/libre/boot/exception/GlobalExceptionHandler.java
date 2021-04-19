package com.libre.boot.exception;

import com.libre.core.result.R;
import com.libre.core.result.ResultCode;
import com.libre.core.toolkit.StringUtil;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.Servlet;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


/**
 * @author zhaocheng
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class })
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleError(MissingServletRequestParameterException e) {
        log.warn("缺少请求参数:{}", e.getMessage());
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return R.fail(ResultCode.PARAM_MISS, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleError(MethodArgumentTypeMismatchException e) {
        log.warn("请求参数格式错误:{}", e.getMessage());
        String message = String.format("请求参数格式错误: %s", e.getName());
        return R.fail(ResultCode.PARAM_TYPE_ERROR, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleError(MethodArgumentNotValidException e) {
        log.warn("参数验证失败:{}", e.getMessage());
        return handleError(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleError(BindException e) {
        log.warn("参数绑定失败:{}", e.getMessage());
        return handleError(e.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleError(ConstraintViolationException e) {
        log.warn("参数验证失败:{}", e.getMessage());
        return handleError(e.getConstraintViolations());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public R<Object> handleError(NoHandlerFoundException e) {
        log.error("404没找到请求:{}", e.getMessage());
        return R.fail(ResultCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Object> handleError(HttpMessageNotReadableException e) {
        log.error("消息不能读取:{}", e.getMessage());
        return R.fail(ResultCode.MSG_NOT_READABLE, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R<Object> handleError(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法:{}", e.getMessage());
        return R.fail(ResultCode.METHOD_NOT_SUPPORTED, e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public R<Object> handleError(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型:{}", e.getMessage());
        return R.fail(ResultCode.MEDIA_TYPE_NOT_SUPPORTED, e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public R<Object> handleError(HttpMediaTypeNotAcceptableException e) {
        String message = e.getMessage() + " " + StringUtil.join(e.getSupportedMediaTypes());
        log.error("不接受的媒体类型:{}", message);
        return R.fail(ResultCode.MEDIA_TYPE_NOT_SUPPORTED, message);
    }

    /**
     * 处理 BindingResult
     *
     * @param result BindingResult
     * @return R
     */
    private static R<Object> handleError(BindingResult result) {
        FieldError error = result.getFieldError();
        String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
        return R.fail(ResultCode.PARAM_BIND_ERROR, message);
    }

    /**
     * 处理 ConstraintViolation
     *
     * @param violations 校验结果
     * @return R
     */
    private static R<Object> handleError(Set<ConstraintViolation<?>> violations) {
        ConstraintViolation<?> violation = violations.iterator().next();
        String path = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = String.format("%s:%s", path, violation.getMessage());
        return R.fail(ResultCode.PARAM_VALID_ERROR, message);
    }

}
