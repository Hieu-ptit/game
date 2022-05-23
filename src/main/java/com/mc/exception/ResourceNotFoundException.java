package com.mc.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ResourceNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private String entityName;

    private String errorKey;

    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

    public ResourceNotFoundException(String entityName) {
        this(DEFAULT_TYPE, "not_found", entityName, "not_found");
    }

    public ResourceNotFoundException(String defaultMessage, String entityName, String errorKey) {
        this(DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public ResourceNotFoundException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.NOT_FOUND, null, null, null, getAlertParameters(defaultMessage, entityName, errorKey, null));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    private static Map<String, Object> getAlertParameters(String defaultMessage, String entityName, String errorKey, String fields) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", errorKey + "." + defaultMessage);
        parameters.put("params", entityName);
        parameters.put("fields", fields);
        return parameters;
    }

    public ResourceNotFoundException(String entityName, String fields) {
        this(DEFAULT_TYPE, MessageKeyConstant.NOT_EXIST, entityName, ErrorKey.ID, fields);
    }

    public ResourceNotFoundException(String defaultMessage, String entityName, String errorKey, String fields) {
        this(DEFAULT_TYPE, defaultMessage, entityName, errorKey, fields);
    }

    public ResourceNotFoundException(URI type, String defaultMessage, String entityName, String errorKey, String fields) {
        super(type, defaultMessage, Status.NOT_FOUND, null, null, null, getAlertParameters(defaultMessage, entityName, errorKey, fields));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }
}
