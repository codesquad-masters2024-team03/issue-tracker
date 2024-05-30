package com.codesquad.team3.issuetracker.oauth.service;

import com.codesquad.team3.issuetracker.domain.member.dto.response.TokenResponse;
import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.codesquad.team3.issuetracker.domain.member.repository.MemberRepository;
import com.codesquad.team3.issuetracker.jwt.util.JwtUtil;
import com.codesquad.team3.issuetracker.oauth.attribute.OAuthAttributes;
import com.codesquad.team3.issuetracker.oauth.dto.request.OAuthProvider;
import com.codesquad.team3.issuetracker.oauth.dto.profile.SnsProfile;
import com.codesquad.team3.issuetracker.oauth.dto.response.OAuthTokenResponse;
import com.codesquad.team3.issuetracker.oauth.repository.OAuthProviderRepository;
import com.codesquad.team3.issuetracker.oauth.util.IdGenerator;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final OAuthProviderRepository oAuthProviderRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public TokenResponse login(String providerName, String code) {
        // OAuthProviderRepository 에서 provider 이름에 해당하는 OAuthProvider 가져오기
        OAuthProvider provider = oAuthProviderRepository.findByProviderName(providerName);

        // access token & sns profile 가져오기
        OAuthTokenResponse tokenResponse = getToken(code, provider);
        SnsProfile snsProfile = getProfile(providerName, tokenResponse, provider);

        // member DB 저장 및 토큰 발급
        Member oAuthMember = saveOrUpdate(snsProfile);
        TokenResponse newTokens = jwtUtil.createTokens(oAuthMember.getMemberId());
        oAuthMember.refreshToken(newTokens.refreshToken());
        memberRepository.update(oAuthMember);

        return newTokens;
    }

    private OAuthTokenResponse getToken(String code, OAuthProvider provider) {
        return WebClient.create()
            .post()
            .uri(provider.getTokenUrl())
            .headers(header -> {
                header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
            })
            .bodyValue(tokenRequest(code, provider))
            .retrieve()
            .bodyToMono(OAuthTokenResponse.class)
            .block();

    }

    private SnsProfile getProfile(String providerName, OAuthTokenResponse tokenResponse, OAuthProvider provider) {
        Map<String, Object> profileAttributes = getProfileAttributes(provider, tokenResponse);
        return OAuthAttributes.extract(providerName, profileAttributes);
    }

    private Member saveOrUpdate(SnsProfile snsProfile) {
        Optional<Member> optional = memberRepository.findByOauthIdAndJoinMethod(snsProfile.getOauthId(), snsProfile.getJoinMethod());
        if (optional.isPresent()) {
            Member targetMember = optional.get();
            Member updatedMember = snsProfile.toMember(targetMember.getId(), targetMember.getMemberId());
            return memberRepository.update(updatedMember);
        }
        String newMemberId = getMemberId();
        Member newMember = snsProfile.toMember(newMemberId);
        return memberRepository.insert(newMember);
    }

    private MultiValueMap<String, String> tokenRequest(String code, OAuthProvider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUrl());
        return formData;
    }

    private Map<String, Object> getProfileAttributes(OAuthProvider provider, OAuthTokenResponse tokenResponse) {
        return WebClient.create()
            .get()
            .uri(provider.getUserInfoUrl())
            .headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
            .block();
    }

    private String getMemberId() {
        String memberId;
        do {
            memberId = IdGenerator.getString(16);
        } while (memberRepository.existsByMemberId(memberId)); // 중복 확인
        return memberId;
    }
}
