package com.sale.teazy.util;

import com.sale.teazy.exception.ExtensionNotAcceptableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SaleTypeUtil {
    private final String[] acceptableSaleTypes;

    public SaleTypeUtil(@Value("${saleType.upload.acceptableSaleType}") String[] acceptableSaleTypes) {
        this.acceptableSaleTypes = acceptableSaleTypes;
    }

    private boolean isSaleTypeAcceptable(String saleType) {
        for (String s : acceptableSaleTypes) {
            if (s.equalsIgnoreCase(saleType)) {
                return true;
            }
        }
        return false;
    }

    public String getSaleTypeIfAcceptable(String saleType) {
        if (isSaleTypeAcceptable(saleType)) {
            return saleType;
        } else {
            throw new ExtensionNotAcceptableException(saleType);
        }
    }
}
