/**
 * Copyright (c) 2017 by CyberSource
 * Governing licence: https://github.com/CyberSource/cybersource-flex-samples/blob/master/LICENSE.md
 */
package com.cybersource.example;

import com.cybersource.flex.sdk.FlexService;
import com.cybersource.flex.sdk.FlexServiceFactory;
import com.cybersource.flex.sdk.authentication.CGKCredentials;
import com.cybersource.flex.sdk.exception.FlexException;
import com.cybersource.flex.sdk.model.EncryptionType;
import com.cybersource.flex.sdk.model.FlexPublicKey;
import com.cybersource.flex.sdk.model.KeysRequestParameters;
import com.cybersource.flex.sdk.repackaged.JSONObject;

import javax.servlet.http.HttpSession;

public class FlexKeyProvider {

    private final FlexService flexService;

    FlexKeyProvider() {
        try {

            MerchantCredentials merchantCredentials = new MerchantCredentials();
            CGKCredentials credentials = new CGKCredentials();
            credentials.setEnvironment(CGKCredentials.Environment.CAS);
            credentials.setMid(merchantCredentials.getMerchantId());
            credentials.setKeyId(merchantCredentials.getKeyId());
            credentials.setSharedSecret(merchantCredentials.getSharedSecret());

            flexService = FlexServiceFactory.createInstance(credentials);
        } catch (FlexException fe) {
            throw new RuntimeException(fe);
        }
    }

    public String bindFlexKeyToSession(HttpSession session) throws FlexException {
        KeysRequestParameters parameters = new KeysRequestParameters(EncryptionType.RsaOaep256, "http://localhost:8080");
        FlexPublicKey flexPublicKey = flexService.createKey(parameters);
        session.setAttribute(FlexPublicKey.class.getName(), flexPublicKey);
        return new JSONObject(flexPublicKey.getJwk()).toString();
    }

    public boolean verifyTokenResponse(HttpSession session, String flexResponse) throws FlexException {
        System.out.print(flexResponse);
        FlexPublicKey flexPublicKey = (FlexPublicKey) session.getAttribute(FlexPublicKey.class.getName());
        return flexService.verify(flexPublicKey, flexResponse);
    }

}
