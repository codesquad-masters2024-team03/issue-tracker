package com.codesquad.team3.issuetracker.domain.member.service;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.LoginMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.TokenResponse;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberInfoResponse;
import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.codesquad.team3.issuetracker.domain.member.repository.MemberRepository;
import com.codesquad.team3.issuetracker.jwt.util.JwtUtil;
import com.codesquad.team3.issuetracker.support.enums.SoftDeleteSearchFlags;
import java.util.List;
import java.util.stream.Collectors;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Override
    public MemberInfoResponse create(CreateMember createRequest) {
        Member savedMember = memberRepository.insert(new Member(createRequest));
        return MemberInfoResponse.toResponse(savedMember);
    }

    @Override
    public MemberInfoResponse update(Integer targetId, UpdateMember updateRequest) {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED).get();
        Member updatedMember = memberRepository.update(targetMember.update(updateRequest));
        return MemberInfoResponse.toResponse(updatedMember);
    }

    @Override
    public MemberInfoResponse findById(Integer targetId) {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED).get();
        return MemberInfoResponse.toResponse(targetMember);
    }

    @Override
    public MemberInfoResponse findByMemberId(String memberId) {
        Member targetMember = memberRepository.findByMemberId(memberId).get();
        return MemberInfoResponse.toResponse(targetMember);
    }

    @Override
    public List<MemberInfoResponse> findAll() {
        List<Member> allMembers = (List<Member>) memberRepository.findAll(SoftDeleteSearchFlags.NOT_DELETED);
        return allMembers.stream().map(MemberInfoResponse::toResponse).collect(Collectors.toList());
    }

    @Override
    public MemberInfoResponse softDeleteById(Integer targetId) {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED).get();
        Member deletedMember = memberRepository.softDelete(targetMember);
        return MemberInfoResponse.toResponse(deletedMember);
    }

    @Override
    public TokenResponse login(LoginMember loginRequest) throws AuthenticationException {
        Member targetMember = memberRepository.findByMemberId(loginRequest.getMemberId())
            .orElseThrow(() -> new AuthenticationException("회원정보가 없습니다."));

        if (!targetMember.checkPassword(loginRequest.getPassword())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        };

        TokenResponse newTokens = createTokens(targetMember.getMemberId());
        targetMember.refreshToken(newTokens.refreshToken());
        memberRepository.update(targetMember);

        return newTokens;
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) throws AuthenticationException {
        Member targetMember = memberRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new AuthenticationException("회원정보가 없습니다."));

        if (!jwtUtil.validateRefreshToken(refreshToken, targetMember.getMemberId())) {
            throw new AuthenticationException("토큰이 유효하지 않습니다.");
        }

        TokenResponse newTokens = createTokens(targetMember.getMemberId());
        targetMember.refreshToken(newTokens.refreshToken());
        memberRepository.update(targetMember);

        return newTokens;
    }

    private TokenResponse createTokens(String memberId) {
        String accessToken = jwtUtil.createAccessToken(memberId);
        String refreshToken = jwtUtil.createRefreshToken(memberId);
        return new TokenResponse(accessToken, refreshToken);
    }
}