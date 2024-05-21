package com.codesquad.team3.issuetracker.domain.member.dto.response;

import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMember {
    private String memberId;
    private String nickname;
    private LocalDateTime birthday;
    private LocalDateTime joinTime;
    private String email;

    public static ResponseMember toResponse(Member member) {
        return new ResponseMember(member.getMemberId(),
            member.getNickname(),
            member.getBirthday(),
            member.getJoinTime(),
            member.getEmail()
        );
    }
}
