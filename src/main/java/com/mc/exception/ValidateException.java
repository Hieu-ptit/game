package com.mc.exception;


import java.util.List;

public class ValidateException extends RuntimeException {
    private final List<FieldViolation> fields;

    public ValidateException(List<FieldViolation> fields) {
        this(null, fields);
    }

    public ValidateException(String msg, List<FieldViolation> fields) {
        super(msg);
        this.fields = fields;
    }

    public List<FieldViolation> getFields() {
        return fields;
    }
}
