package com.sale.teazy.exception;

public class FileCantUploadException extends RuntimeException{
    public FileCantUploadException(String fileName) {
        super(fileName + ", this file cant upload!");
    }
}
