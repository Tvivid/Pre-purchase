package com.example.preorder.Controller;

import com.example.preorder.Service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/internal/member")
public class InternalMemberController {

    private final MemberService memberService;

    @Autowired
    public InternalMemberController(MemberService memberService) {
        this.memberService = memberService;
    }



    @GetMapping
    public Long getMember(@RequestParam("token") String token){
        Long member = memberService.findUser(token);
        return member;
    }
}
