package com.pandorabox.exception;

public class PandoraException extends RuntimeException {

    private static final long serialVersionUID = 4554338903170024799L;

    public PandoraException(String message, Throwable cause) {
        super(message, cause);
    }

    public PandoraException(String message) {
        super(message);
    }

    public PandoraException(Throwable cause) {
        super(cause);
    }

}
