package com.codesquad.team3.issuetracker.domain.member.entity;

import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.global.entity.SoftDeleteEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name="member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements SoftDeleteEntity {

    @Id
    private Integer id;
    private String memberId;
    private String oauthId;
    private String password;
    private String nickname;
    private String imageUrl;
    private LocalDate birthday;
    private String joinMethod;
    @CreatedDate
    private LocalDateTime joinTime;
    private String email;
    private String refreshToken;
    private boolean isDeleted;

    public Member update(UpdateMember updateMember) {
        this.password = updateMember.password();
        this.nickname = updateMember.nickname();
        this.imageUrl = updateMember.imageUrl();
        this.birthday = updateMember.birthday();
        this.email = updateMember.email();
        return this;
    }

    @Override
    public void delete() {
        this.isDeleted = true;
    }

    @Override
    public void recover() {
        this.isDeleted = false;
    }

    public void refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void removeRefreshToken() {
        this.refreshToken = null;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}