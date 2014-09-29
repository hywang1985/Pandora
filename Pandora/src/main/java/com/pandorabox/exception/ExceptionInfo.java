package com.pandorabox.exception;

public enum ExceptionInfo {
    DEFAULT(500, "An error has occurred"),
    NO_USER(NoUserException.class, 700, "No user exist in the session");

    public static int DEFAULT_CODE = 500;

    private Class clazz;

    private int code;

    private String message;

    private ExceptionInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ExceptionInfo(Class clazz, int code, String message) {
        this.clazz = clazz;
        this.code = code;
        this.message = message;
    }

    public static ExceptionInfo valueOf(Class clazz) {
        for (ExceptionInfo value : values()) {
            if (value.clazz == clazz) {
                return value;
            } else {
                continue;
            }
        }
        return DEFAULT;
    }

    public Class getClazz() {
        return clazz;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
