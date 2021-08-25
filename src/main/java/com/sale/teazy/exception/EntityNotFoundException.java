package com.sale.teazy.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class entity, Long id) {
        super(entity.getSimpleName() + " with ID: " + id + "  ");
    }
}
