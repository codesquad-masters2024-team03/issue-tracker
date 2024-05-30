package com.codesquad.team3.issuetracker.oauth.config;

import com.codesquad.team3.issuetracker.oauth.dto.request.OAuthProvider;
import com.codesquad.team3.issuetracker.oauth.repository.OAuthProviderRepository;
import com.codesquad.team3.issuetracker.oauth.util.OAuthAdapter;
import java.util.Map;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OAuthProperties.class)
public class OAuthConfig {

    private final OAuthProperties properties;

    public OAuthConfig(OAuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OAuthProviderRepository oAuthproviderRepository() {
        Map<String, OAuthProvider> providers = OAuthAdapter.getOAuthProviders(properties);
        return new OAuthProviderRepository(providers);
    }
}