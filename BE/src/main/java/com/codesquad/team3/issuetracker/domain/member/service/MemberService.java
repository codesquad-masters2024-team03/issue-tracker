package com.codesquad.team3.issuetracker.domain.member.service;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.LoginMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.LoginResponse;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberInfoResponse;
import java.util.List;
import javax.naming.AuthenticationException;

public interface MemberService {

    MemberInfoResponse create(CreateMember createRequest);

    MemberInfoResponse update(Integer targetId, UpdateMember updateRequest);

    MemberInfoResponse findById(Integer targetId);

    MemberInfoResponse findByMemberId(String memberId);

    List<MemberInfoResponse> findAll();

    MemberInfoResponse softDeleteById(Integer targetId);

    LoginResponse login(LoginMember loginRequest) throws AuthenticationException;

}