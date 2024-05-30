package com.codesquad.team3.issuetracker.domain.member.service;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.LoginMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.TokenResponse;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberInfoResponse;
import java.util.List;
import javax.naming.AuthenticationException;

public interface MemberService {

    MemberInfoResponse create(CreateMember createRequest) throws IllegalArgumentException;

    MemberInfoResponse update(Integer targetId, UpdateMember updateRequest) throws IllegalArgumentException;

    MemberInfoResponse findById(Integer targetId) throws IllegalArgumentException;

    MemberInfoResponse findByMemberId(String memberId) throws IllegalArgumentException;

    List<MemberInfoResponse> findAll();

    MemberInfoResponse softDeleteById(Integer targetId) throws IllegalArgumentException;

    TokenResponse login(LoginMember loginRequest) throws AuthenticationException;

    void logout(String refreshToken) throws AuthenticationException;

    TokenResponse refreshToken(String refreshToken) throws AuthenticationException;

}