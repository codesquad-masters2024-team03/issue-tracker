package com.codesquad.team3.issuetracker.domain.jwt.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    private Integer Id;
    private String memberId;
    private String refreshToken;
    private Date expirationDate;

    public RefreshToken(String memberId, String refreshToken, Date expirationDate) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expirationDate = expirationDate;
    }

}
