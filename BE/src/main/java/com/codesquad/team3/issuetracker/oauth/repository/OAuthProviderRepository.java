package com.codesquad.team3.issuetracker.oauth.repository;

import com.codesquad.team3.issuetracker.oauth.dto.request.OAuthProvider;
import java.util.HashMap;
import java.util.Map;

public class OAuthProviderRepository {
    private final Map<String, OAuthProvider> providers;

    public OAuthProviderRepository(Map<String, OAuthProvider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public OAuthProvider findByProviderName(String name) {
        return providers.get(name);
    }
}
