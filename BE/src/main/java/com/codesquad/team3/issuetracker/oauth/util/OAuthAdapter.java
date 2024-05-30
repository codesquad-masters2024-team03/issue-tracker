package com.codesquad.team3.issuetracker.oauth.util;

import com.codesquad.team3.issuetracker.oauth.dto.request.OAuthProvider;
import com.codesquad.team3.issuetracker.oauth.config.OAuthProperties;
import java.util.HashMap;
import java.util.Map;

public class OAuthAdapter {

    private OAuthAdapter() {}

    // OAuthProperties -> OAuthProvider 변환
    public static Map<String, OAuthProvider> getOAuthProviders(OAuthProperties properties) {
        Map<String, OAuthProvider> oAuthProviders = new HashMap<>();

        properties.getUser().forEach((key, value) -> oAuthProviders.put(key,
            new OAuthProvider(value, properties.getProvider().get(key))));
        return oAuthProviders;
    }
}
