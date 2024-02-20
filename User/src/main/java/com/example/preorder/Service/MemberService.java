package com.example.preorder.Service;

import com.example.preorder.Dto.JwtToken;
import com.example.preorder.Dto.MemberDto;
import com.example.preorder.Entity.Member;
import com.example.preorder.JWT.JwtTokenProvider;
import com.example.preorder.Repository.MemberLoginRepository;
import com.example.preorder.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MailService mailService;

    private final MemberLoginRepository memberLoginRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(Member member) {
        // 사용자 정보를 저장하고 인증 이메일을 전송
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        validateDuplicateMember(member); //중복 회원 검증
        String tempToken= UUID.randomUUID().toString();
        member.setVerificationToken(tempToken);
        member.getRoles().add("USER");
        memberRepository.save(member);


        String subject = "회원가입 인증";
        String from = "tjsaud3250@naver.com";
        String text = "회원가입을 완료하려면 아래 링크를 클릭하세요: http://localhost:8081/v1/verify?email="+ member.getEmail()+"&token=" + member.getVerificationToken();
        mailService.sendEmail(member.getEmail(), from, subject, text);
    }

    @Transactional
    public void verifyUser(String email, String token){
        Member member=memberLoginRepository.findByEmail(email)
                .orElseThrow(NullPointerException::new);

          member.setVerificationState(true);
          memberLoginRepository.save(member);

    }


//    @Transactional //변경
//    public Long join(Member member) {
//        validateDuplicateMember(member); //중복 회원 검증
//        memberRepository.save(member);
//        return member.getId();
//    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers = memberLoginRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public JwtToken signIn(String email, String password) {

        Member member=memberLoginRepository.findByEmail(email)
                .orElseThrow(NullPointerException::new);
        if(!member.isVerificationState()){
            throw new IllegalStateException("이메일 인증을 진행해주세요");
        }
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }


    @Transactional
    public void updateMember(String token, MemberDto memberUpdateDTO) {
        // 회원 정보 조회
        String email = jwtTokenProvider.getAuthentication(token).getName();

        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 회원 정보 업데이트

        if (memberUpdateDTO.getName() != null) {
            member.setUsername(memberUpdateDTO.getName());
        }
        if (memberUpdateDTO.getIntroduce() != null) {
            member.setIntroduce(memberUpdateDTO.getIntroduce());
        }
        if (memberUpdateDTO.getProfileImage() != null) {
            member.setProfileImage(memberUpdateDTO.getProfileImage());
        }


        // 프로필 이미지나 기타 필드 업데이트 로직 추가 가능
         memberRepository.save(member); // JPA의 변경 감지 기능으로 인해 save 호출 생략 가능
    }

    @Transactional
    public void changePassword(String token, String currentPassword, String newPassword) {

        // 1. JWT 토큰에서 사용자 정보 추출
        String email = jwtTokenProvider.getAuthentication(token).getName();

        // 2. 사용자 정보 조회
        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 3. 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {

            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 4. 새 비밀번호로 업데이트
        member.setPassword(passwordEncoder.encode(newPassword));
        memberLoginRepository.save(member);


    }

    @Transactional
    public Long findUser(String token){
        String email = jwtTokenProvider.getAuthentication(token).getName();

        // 2. 사용자 정보 조회
        Member member = memberLoginRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Long memberId = member.getId();

        return memberId;


    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}