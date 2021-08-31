package com.sale.teazy.exception;

public class DateNotParsingException extends RuntimeException{
    public DateNotParsingException(String startDate,String endDate) {
        super("Start date :"+startDate+" or End Date :"+endDate+" not parsing !");
    }
}
