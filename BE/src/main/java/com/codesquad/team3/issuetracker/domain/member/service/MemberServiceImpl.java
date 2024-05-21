package com.codesquad.team3.issuetracker.domain.member.service;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.ResponseMember;
import com.codesquad.team3.issuetracker.domain.member.entity.Member;
import com.codesquad.team3.issuetracker.domain.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public ResponseMember save(CreateMember createRequest) {
        Member savedMember = memberRepository.insert(new Member(createRequest));
        return savedMember.toResponse();
    }

    @Override
    public ResponseMember update(Integer targetId, UpdateMember updateRequest) {
        Member targetMember = memberRepository.findById(targetId).get();
        Member updatedMember = memberRepository.update(targetMember.update(updateRequest));
        return updatedMember.toResponse();
    }

    @Override
    public ResponseMember findById(Integer targetId) {
        Member targetMember = memberRepository.findById(targetId).get();
        return targetMember.toResponse();
    }

    @Override
    public List<ResponseMember> findAll() {
        Iterable<Member> members = memberRepository.findAll();
        List<ResponseMember> responseMembers = new ArrayList<>();
        members.forEach(member -> responseMembers.add(member.toResponse()));
        return responseMembers;
    }

    @Override
    public ResponseMember softDeleteById(Integer targetId) {
        Member targetMember = memberRepository.findById(targetId).get();
        Member deletedMember = memberRepository.softDelete(targetMember);
        return deletedMember.toResponse();
    }
}