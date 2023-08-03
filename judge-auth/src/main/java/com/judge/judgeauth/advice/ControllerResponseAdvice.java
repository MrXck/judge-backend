package com.judge.judgeauth.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.judge.judgeauth.common.R;
import com.judge.judgeauth.exception.APIException;
import com.judge.judgeauth.utils.NotControllerResponseAdvice;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author xck
 */
@RestControllerAdvice(basePackages = {"com.judge"})
public class ControllerResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.getParameterType().isAssignableFrom(R.class) && !returnType.hasMethodAnnotation(NotControllerResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(R.success(data));
            } catch (Exception e) {
                throw new APIException(e.getMessage());
            }
        }
        return R.success(data);
    }
}
