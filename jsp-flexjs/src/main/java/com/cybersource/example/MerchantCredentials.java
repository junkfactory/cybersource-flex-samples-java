/**
 * Copyright (c) 2017 by CyberSource
 * Governing licence: https://github.com/CyberSource/cybersource-flex-samples/blob/master/LICENSE.md
 */
package com.cybersource.example;

public class MerchantCredentials {

    public String getMerchantId() {
        return System.getenv("merchantId");
    }

    public String getKeyId() {
        return System.getenv("keyId");
    }

    public char[] getSharedSecret() {
        return System.getenv("sharedSecret").toCharArray();
    }

}
