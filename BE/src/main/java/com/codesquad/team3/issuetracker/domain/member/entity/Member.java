package com.codesquad.team3.issuetracker.domain.member.entity;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.global.entity.SoftDeleteEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member implements SoftDeleteEntity {

    @Id
    private Integer id;
    private String memberId;
    private String password;
    private String nickname;
    private LocalDateTime birthday;

    @CreatedDate
    @Column("join_time")
    private LocalDateTime joinTime;
    private String email;
    private boolean isDeleted;

    public Member(CreateMember createMember) {
        this.memberId = createMember.getMemberId();
        this.password = createMember.getPassword();
        this.nickname = createMember.getNickname();
        this.birthday = createMember.getBirthday();
        this.joinTime = LocalDateTime.now();
        this.email = createMember.getEmail();
    }

    public Member update(UpdateMember updateMember) {
        return new Member(id,
            memberId,
            updateMember.getPassword(),
            updateMember.getNickname(),
            updateMember.getBirthday(),
            joinTime,
            updateMember.getEmail(),
            isDeleted
        );
    }

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public void recover() {
        this.isDeleted = false;
    }

    @Override
    public void delete() {
        isDeleted = true;

    }

    @Override
    public void recover() {
        isDeleted= false;

    }
}