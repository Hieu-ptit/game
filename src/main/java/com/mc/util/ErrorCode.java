package com.mc.util;

import com.mc.exception.BusinessErrorCode;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ErrorCode {
    public static final BusinessErrorCode INTERNAL_SERVER_ERROR =
        new BusinessErrorCode(5000, "Internal server error", 503);

    public static final BusinessErrorCode INTERNAL_BUFFER_ERROR =
        new BusinessErrorCode(5002, "Buffer connect fail", 503);

    public static final BusinessErrorCode INVALID_FIELD_FORMAT =
        new BusinessErrorCode(4013, "Invalid field format", 400);

    public static final BusinessErrorCode MISSING_PARAMETER =
        new BusinessErrorCode(4024, "Missing parameter", 400);

    public static final BusinessErrorCode COULD_NOT_GENERATE_CODE =
        new BusinessErrorCode(5004, "Could not Generate code for object", 503);

    public static final BusinessErrorCode INVALID_FIELD_NAME =
        new BusinessErrorCode(4027, "Field name is invalid", 400);

    public static final BusinessErrorCode FILE_TOO_LARGE =
        new BusinessErrorCode(4201, "File to large", 400);

    public static final BusinessErrorCode EMAIL_ALREADY =
        new BusinessErrorCode("Email already!", 400);

    public static final BusinessErrorCode EMAIL_IS_INVALID =
        new BusinessErrorCode("Email is invalid!", 400);

    public static final BusinessErrorCode INVALID_PASSWORD_TYPE =
        new BusinessErrorCode(4162, "Incorrect password", 400);

    public static final BusinessErrorCode USER_ALREADY =
        new BusinessErrorCode(4163, "A new user cannot already have an ID, userManagement, id exists", 400);

    public static final BusinessErrorCode PASSWORD_IN_VALID =
        new BusinessErrorCode("Incorrect password!", 400);

    public static final BusinessErrorCode ONLY_UPLOAD_10_FILES =
        new BusinessErrorCode(4202, "Only upload 10 file", 400);

    public static final BusinessErrorCode FILE_NOT_EXIST =
        new BusinessErrorCode(4203, "File not exist", 400);

    public static final BusinessErrorCode PASSWORD_INVALID =
        new BusinessErrorCode("Password is invalid!", 400);

    public static final BusinessErrorCode PASSWORD_NEW_WRONG =
        new BusinessErrorCode("Password new wrong!", 400);

    public static final BusinessErrorCode USER_NOT_FOUND =
        new BusinessErrorCode("User not found", 403);

    static {
        Set<Integer> codes = new HashSet<>();
        var duplications = Arrays.stream(ErrorCode.class.getDeclaredFields())
            .filter(f -> Modifier.isStatic(f.getModifiers()) && f.getType().equals(BusinessErrorCode.class))
            .map(f -> {
                try {
                    return ((BusinessErrorCode) f.get(null)).getCode();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })
            .filter(code -> !codes.add(code))
            .collect(Collectors.toList());
        if (!duplications.isEmpty()) {
            throw new RuntimeException("Duplicate error code: " + duplications);
        }
    }
}
