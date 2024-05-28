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
    public MemberInfoResponse create(CreateMember createRequest) throws IllegalArgumentException {
        if (memberRepository.existsByMemberId(createRequest.memberId())) {
            throw new IllegalArgumentException("동일한 아이디가 이미 존재합니다.");
        }
        if (memberRepository.existsByNickname(createRequest.nickname())) {
            throw new IllegalArgumentException("동일한 닉네임이 이미 존재합니다.");
        }
        if (memberRepository.existsByEmail(createRequest.email())) {
            throw new IllegalArgumentException("동일한 이메일이 이미 존재합니다.");
        }

        Member savedMember = memberRepository.insert(createRequest.toMember());
        return MemberInfoResponse.toResponse(savedMember);
    }

    @Override
    public MemberInfoResponse update(Integer targetId, UpdateMember updateRequest) throws IllegalArgumentException {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        if (memberRepository.existsByNickname(updateRequest.nickname())) {
            throw new IllegalArgumentException("동일한 닉네임이 이미 존재합니다.");
        }
        if (memberRepository.existsByEmail(updateRequest.email())) {
            throw new IllegalArgumentException("동일한 이메일이 이미 존재합니다.");
        }

        Member updatedMember = memberRepository.update(targetMember.update(updateRequest));
        return MemberInfoResponse.toResponse(updatedMember);
    }

    @Override
    public MemberInfoResponse findById(Integer targetId) throws IllegalArgumentException {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        return MemberInfoResponse.toResponse(targetMember);
    }

    @Override
    public MemberInfoResponse findByMemberId(String memberId) throws IllegalArgumentException {
        Member targetMember = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        return MemberInfoResponse.toResponse(targetMember);
    }

    @Override
    public List<MemberInfoResponse> findAll() {
        List<Member> allMembers = (List<Member>) memberRepository.findAll(SoftDeleteSearchFlags.NOT_DELETED);
        return allMembers.stream().map(MemberInfoResponse::toResponse).collect(Collectors.toList());
    }

    @Override
    public MemberInfoResponse softDeleteById(Integer targetId) throws IllegalArgumentException{
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        Member deletedMember = memberRepository.softDelete(targetMember);
        return MemberInfoResponse.toResponse(deletedMember);
    }

    @Override
    public TokenResponse login(LoginMember loginRequest) throws AuthenticationException {
        Member targetMember = memberRepository.findByMemberId(loginRequest.memberId())
            .orElseThrow(() -> new AuthenticationException("회원정보가 없습니다."));
        if (!targetMember.checkPassword(loginRequest.password())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }

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