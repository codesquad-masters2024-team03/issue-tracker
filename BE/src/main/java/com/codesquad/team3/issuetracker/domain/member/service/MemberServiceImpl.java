package com.codesquad.team3.issuetracker.domain.member.service;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.ResponseMember;
import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.codesquad.team3.issuetracker.domain.member.repository.MemberRepository;
import com.codesquad.team3.issuetracker.support.enums.SoftDeleteSearchFlags;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public ResponseMember save(CreateMember createRequest) {
        Member savedMember = memberRepository.insert(new Member(createRequest));
        return ResponseMember.toResponse(savedMember);
    }

    @Override
    public ResponseMember update(Integer targetId, UpdateMember updateRequest) {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED).get();
        Member updatedMember = memberRepository.update(targetMember.update(updateRequest));
        return ResponseMember.toResponse(updatedMember);
    }

    @Override
    public ResponseMember findById(Integer targetId) {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED).get();
        return ResponseMember.toResponse(targetMember);
    }

    @Override
    public List<ResponseMember> findAll() {
        List<Member> allMembers = (List<Member>) memberRepository.findAll(SoftDeleteSearchFlags.NOT_DELETED);
        return allMembers.stream().map(ResponseMember::toResponse).collect(Collectors.toList());
    }

    @Override
    public ResponseMember softDeleteById(Integer targetId) {
        Member targetMember = memberRepository.findByIdWithDeleteCondition(targetId, SoftDeleteSearchFlags.NOT_DELETED).get();
        Member deletedMember = memberRepository.softDelete(targetMember);
        return ResponseMember.toResponse(deletedMember);
    }
}