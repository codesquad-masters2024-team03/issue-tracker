package com.codesquad.team3.issuetracker.oauth.dto.profile;

import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SnsProfile {
    private final String oauthId;
    private final String name;
    private final String imageUrl;
    private final String joinMethod;
    private final String email;

    public Member toMember(String memberId) {
        return Member.builder()
            .memberId(memberId)
            .oauthId(oauthId)
            .nickname(name)
            .imageUrl(imageUrl)
            .joinMethod(joinMethod)
            .joinTime(LocalDateTime.now())
            .email(email)
            .build();
    }

    public Member toMember(Integer id, String memberId) {
        return Member.builder()
            .id(id)
            .memberId(memberId)
            .oauthId(oauthId)
            .nickname(name)
            .imageUrl(imageUrl)
            .joinMethod(joinMethod)
            .joinTime(LocalDateTime.now())
            .email(email)
            .build();
    }
}
