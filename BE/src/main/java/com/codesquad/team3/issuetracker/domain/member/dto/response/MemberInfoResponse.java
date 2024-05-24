package com.codesquad.team3.issuetracker.domain.member.dto.response;

import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
    private String memberId;
    private String nickname;
    private LocalDateTime birthday;
    private LocalDateTime joinTime;
    private String email;

    public static MemberInfoResponse toResponse(Member member) {
        return new MemberInfoResponse(member.getMemberId(),
            member.getNickname(),
            member.getBirthday(),
            member.getJoinTime(),
            member.getEmail()
        );
    }
}
