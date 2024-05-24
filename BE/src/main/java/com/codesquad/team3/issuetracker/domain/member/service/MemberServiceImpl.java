package com.codesquad.team3.issuetracker.domain.member.service;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.LoginMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.LoginResponse;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberInfoResponse;
import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.codesquad.team3.issuetracker.domain.member.repository.MemberRepository;
import com.codesquad.team3.issuetracker.domain.jwt.util.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

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
    public LoginResponse login(LoginMember loginRequest) throws AuthenticationException {
        Member member = memberRepository.findByMemberId(loginRequest.getMemberId())
            .orElseThrow(() -> new AuthenticationException("회원정보가 없습니다."));

        if (!member.checkPassword(loginRequest.getPassword())) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }

        return createTokens(member.getMemberId());
    }

    // 토큰 생성
    private LoginResponse createTokens(String memberId) {
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        String refreshToken = jwtTokenProvider.createRefreshToken(memberId);
        return new LoginResponse(accessToken, refreshToken);
    }
}