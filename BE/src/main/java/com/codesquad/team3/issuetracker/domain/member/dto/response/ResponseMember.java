package com.codesquad.team3.issuetracker.domain.member.dto.response;

import com.codesquad.team3.issuetracker.domain.member.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseMember {
    private Integer id;
    private String memberId;
    private String nickname;
    private LocalDate birthday;
    private LocalDateTime joinTime;
    private String email;

    public static ResponseMember toResponse(Member member) {
        return new ResponseMember(member.getId(), member.getMemberId(),
            member.getNickname(),
            member.getBirthday(),
            member.getJoinTime(),
            member.getEmail()
        );
    }
}
