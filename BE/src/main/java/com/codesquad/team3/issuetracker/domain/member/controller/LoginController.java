package com.codesquad.team3.issuetracker.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/oauth2/code")
public class LoginController {

    @GetMapping("/github")
    public void githubLogin(@RequestParam String code) {

    }
}
