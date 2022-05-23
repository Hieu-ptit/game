package com.mc.util;

import com.fasterxml.uuid.Generators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.format.DateTimeFormatter;

public final class Constant {

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String PREFIX_RESPONSE_CODE = "SUPPORT-";
    public static final String GUEST_ROLE = "ROLE_GUEST";
    public static final String USER_ROLE = "ROLE_USER";

    public static final String BEARER = "Bearer";
    public static final String USER_ATTR = "user";
    public static final String SEPARATOR = "-";

    public static final String JWT_OA = "JWT_OA";
    public static final Logger logger;


    static {
        logger = LogManager.getLogger(Constant.class);
    }

    private Constant() {
        //hidden constructor
    }

    public static String generateUUID() {
        return Generators.randomBasedGenerator().generate().toString();
    }

    public static boolean compareEnum(Object... o) {

        for (int i = 0; i < o.length - 1; i++)
            if (!o[i].toString().equals(o[i + 1].toString())) return false;

        return true;
    }


}
