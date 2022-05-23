package com.mc.controller;

import com.exception.BusinessErrorCode;
import com.exception.BusinessException;
import com.exception.FieldViolation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.model.dto.Response;
import com.util.ErrorCode;
import com.util.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final Logger logger = LogManager.getLogger(ExceptionController.class);
    private final ObjectMapper objectMapper;

    @ExceptionHandler(BusinessException.class)
    public void handleBusinessException(BusinessException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(e, e.getErrorCode(), request, response);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(e, ErrorCode.INTERNAL_SERVER_ERROR, request, response);
    }

    @ExceptionHandler(BindException.class)
    public void handleException(BindException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);

        if (fieldError == null) {
            var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, ErrorMessage.MESSAGE_ERROR);
            logger.error("{}", errorResponse, e);
            writeResponse(response, HttpStatus.BAD_REQUEST.value(), errorResponse);
            return;
        }

        String message = fieldError.getDefaultMessage();
        assert message != null;

        var errorResponse = Response.ofFailed(new BusinessErrorCode(Integer.parseInt(message)));
        writeResponse(response, HttpStatus.BAD_REQUEST.value(), errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintValidationException(
        ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<FieldViolation> violations = e.getConstraintViolations().stream()
            .map(violation -> new FieldViolation(((PathImpl) violation.getPropertyPath()).getLeafNode().getParent().getName(),
                violation.getMessage()))
            .collect(Collectors.toList());
        var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, ErrorMessage.MESSAGE_ERROR,
            violations);
        logger.error("{}", errorResponse, e);
        writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public void handleInvalidFormatException(InvalidFormatException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<FieldViolation> violations = e.getPath().stream().map(
            reference -> new FieldViolation(reference.getFieldName(), reference.getDescription())).collect(
            Collectors.toList());
        var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, ErrorMessage.MESSAGE_ERROR,
            violations);
        logger.error("{}", errorResponse, e);
        writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String description;
        Class<?> requiredType = e.getRequiredType();
        if (requiredType != null) {
            description = ErrorMessage.INVALID + e.getName() + ErrorMessage.REQUIRE + requiredType.getSimpleName() + ErrorMessage.TYPE;
        } else {
            description = e.getMessage();
        }
        var fieldViolation = new FieldViolation(e.getName(), description);
        var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, Collections.singletonList(fieldViolation));
        logger.error("{}", errorResponse, e);
        writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        InvalidFormatException realCause;
        String fieldName;
        try {
            realCause = ((InvalidFormatException) e.getCause());
            fieldName = realCause.getPath().get(0).getFieldName();
        } catch (Exception exception) {
            handleException(e, request, response);
            return;
        }
        String description;
        if (realCause.getTargetType().getEnumConstants() != null) {
            description = ErrorMessage.INVALID + fieldName + ErrorMessage.REQUIRE + realCause.getTargetType().getSimpleName() + Arrays.toString(realCause.getTargetType().getEnumConstants()) + ErrorMessage.TYPE;
        } else {
            description = ErrorMessage.INVALID + fieldName + ErrorMessage.REQUIRE + realCause.getTargetType().getSimpleName() + ErrorMessage.TYPE;
        }
        var fieldViolation = new FieldViolation(fieldName, description);
        var errorResponse = Response.ofFailed(ErrorCode.INVALID_FIELD_FORMAT, Collections.singletonList(fieldViolation));
        logger.error("{}", errorResponse, e);
        writeResponse(response, ErrorCode.INVALID_FIELD_FORMAT.getHttpStatus(), errorResponse);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public void handleMissingServletRequestPartException(MissingServletRequestPartException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(e, ErrorCode.INVALID_FIELD_FORMAT, request, response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestPartException(MissingServletRequestParameterException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handle(e, ErrorCode.MISSING_PARAMETER, request, response);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleMaxSizeException(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response) throws IOException {

        handle(e, ErrorCode.FILE_TOO_LARGE, request, response);
    }

    private void handle(Exception e, BusinessErrorCode errorCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        var errorResponse = Response.ofFailed(errorCode);
        logger.error("{}", errorResponse, e);
        writeResponse(response, errorCode.getHttpStatus(), errorResponse);
    }

    private void writeResponse(HttpServletResponse servletResponse, int httpStatus, Response<?> errorResponse) throws IOException {
        servletResponse.setStatus(httpStatus);
        servletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        byte[] body = objectMapper.writeValueAsBytes(errorResponse);
        servletResponse.setContentLength(body.length);
        servletResponse.getOutputStream().write(body);
    }
}
