package com.swiggy.app.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
    COD("COD"),
    UPI_GOOGLE_PAY("UPI_GOOGLE_PAY"),
    UPI_PHONEPE("UPI_PHONEPE"),
    UPI_PAYTM("UPI_PAYTM"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    @JsonValue
    public String getMethod() {
        return method;
    }

    @JsonCreator
    public static PaymentMethod fromValue(String method) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.method.equalsIgnoreCase(method)) {
                return paymentMethod;
            }
        }
        throw new IllegalArgumentException("Invalid payment method: " + method);
    }
}
