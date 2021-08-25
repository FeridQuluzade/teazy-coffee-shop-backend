package com.sale.teazy.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SaleType {
    BOLT,WOLT,TEAZY;

    @JsonValue
    public String toLower() {
        return this.toString().toLowerCase();
    }
}
