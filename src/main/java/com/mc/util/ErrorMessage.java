package com.mc.util;

public interface ErrorMessage {

    String MESSAGE_ERROR = "Invalid field format";

    String REQUIRE = "require";

    String CAN_NOT_ENCODE_JSON_OBJECT = "Can't encode json object";

    String UNAUTHORIZED = "Permission denied !";

    String EMAIL_INVALID = "Email is invalid!";

    String TYPE = "type";

    String CAN_NOT_ENCODE_JSON_OBJECT_OF_TYPE = "Can't encode json object of type: ";

    String CAN_NOT_DELETE_SESSION = "cannot delete driver session with id = %s";

    String INVALID = "Invalid";

    String ROLE_NOT_FOUND = "Role not found!";
    String EMAIL_IS_ALREADY = "Email is already in use!, userManagement, email exists";

    String USER_ALREADY = "A new user cannot already have an ID, userManagement, id exists";

    String PASS_WORD_INVALID = "Incorrect password!";

    String EMAIL_ALREADY = "Email already!";

    String INVALID_PASSWORD_TYPE = "Incorrect password";

    String USER_NOT_FOUND = "User not found!";

    String ROOM_NOT_FOUND = "Room not found";
}
