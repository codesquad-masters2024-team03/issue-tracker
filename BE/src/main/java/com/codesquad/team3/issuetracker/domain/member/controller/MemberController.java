package com.codesquad.team3.issuetracker.domain.member.controller;

import com.codesquad.team3.issuetracker.domain.member.dto.request.CreateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.LoginMember;
import com.codesquad.team3.issuetracker.domain.member.dto.request.UpdateMember;
import com.codesquad.team3.issuetracker.domain.member.dto.response.TokenResponse;
import com.codesquad.team3.issuetracker.domain.member.dto.response.MemberInfoResponse;
import com.codesquad.team3.issuetracker.domain.member.service.MemberService;
import java.util.List;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberInfoResponse> signUp(@Validated @RequestBody CreateMember createRequest) {
        MemberInfoResponse createdMember = memberService.create(createRequest);
        return ResponseEntity.ok(createdMember);
    }

    @GetMapping
    public ResponseEntity<List<MemberInfoResponse>> getAll() {
        List<MemberInfoResponse> allMembers = memberService.findAll();
        return ResponseEntity.ok(allMembers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberInfoResponse> getById(@PathVariable String id) {
        MemberInfoResponse targetMember = memberService.findById(Integer.parseInt(id));
        return ResponseEntity.ok(targetMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberInfoResponse> updateById(@PathVariable String id,
        @RequestBody @Validated UpdateMember updateRequest) {
        MemberInfoResponse updatedMember = memberService.update(Integer.parseInt(id),
            updateRequest);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MemberInfoResponse> deleteById(@PathVariable String id) {
        MemberInfoResponse deletedMember = memberService.softDeleteById(Integer.parseInt(id));
        return ResponseEntity.ok(deletedMember);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginMember loginRequest) {
        TokenResponse tokenResponse;
        try {
            tokenResponse = memberService.login(loginRequest);
            return ResponseEntity.ok(tokenResponse);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody String refreshToken) {
        TokenResponse tokenResponse;
        try {
            tokenResponse = memberService.refreshToken(refreshToken);
            return ResponseEntity.ok(tokenResponse);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}