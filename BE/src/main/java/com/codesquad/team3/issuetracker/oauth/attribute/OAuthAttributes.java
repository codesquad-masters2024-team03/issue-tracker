package com.codesquad.team3.issuetracker.oauth.attribute;

import com.codesquad.team3.issuetracker.oauth.dto.profile.SnsProfile;
import java.util.Arrays;
import java.util.Map;

public enum OAuthAttributes {
    GITHUB {
        @Override
        public SnsProfile of(Map<String, Object> attributes) {
            return SnsProfile.builder()
                .oauthId(String.valueOf(attributes.get("login")))
                .name((String) attributes.get("name"))
                .imageUrl((String) attributes.get("avatar_url"))
                .joinMethod(name())
                .email((String) attributes.get("email"))
                .build();
        }
    },
    ;

    public static SnsProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
            .filter(provider -> providerName.toUpperCase().equals(provider.name()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new)
            .of(attributes);
    }

    public abstract SnsProfile of(Map<String, Object> attributes);
}