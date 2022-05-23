package com.mc.exception;

import java.net.URI;

public final class ErrorConstants {

    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

    public static final String ERROR_APPROVE_KEY = "error.approve";
    public static final String ERROR_PROJECT_APPROVE_MESSAGE = "Only Admin Can Approve Project WAITING_ACCEPT";

    public static final String ERROR_REJECT_KEY = "error.reject";
    public static final String ERROR_PROJECT_REJECT_MESSAGE = "Only Admin Can Reject Project WAITING_ACCEPT";

    private ErrorConstants() {}
}
