package com.sale.teazy.exception;

public class ExtensionNotAcceptableException extends RuntimeException {
    public ExtensionNotAcceptableException(String extension) {
        super("." + extension + " ");
    }
}
