package com.example.sbsj_process.account.service;

import com.example.sbsj_process.account.entity.Authentication;
import com.example.sbsj_process.account.entity.BasicAuthentication;
import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.entity.MemberProfile;
import com.example.sbsj_process.account.repository.AuthenticationRepository;
import com.example.sbsj_process.account.repository.MemberProfileRepository;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.account.request.MemberCheckPasswordRequest;
import com.example.sbsj_process.account.request.MemberLoginRequest;
import com.example.sbsj_process.account.request.MemberRegisterRequest;
import com.example.sbsj_process.account.request.MyPageUpdateRequest;
import com.example.sbsj_process.account.response.MemberInfoResponse;
import com.example.sbsj_process.account.response.MemberLoginResponse;
import com.example.sbsj_process.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;
    final private MemberProfileRepository memberProfileRepository;
    final private AuthenticationRepository authenticationRepository;
    final private RedisService redisService;

    @Override
    public Boolean signUp(MemberRegisterRequest memberRegisterRequest) {
        final Member member = memberRegisterRequest.toMember();
        final MemberProfile memberProfile = memberRegisterRequest.toMemberProfile(member);

        try{
            memberRepository.save(member);
            memberProfileRepository.save(memberProfile);
        } catch(Exception e) {
            memberRepository.deleteByMemberNo(member.getMemberNo());
            return false;
        }

        final BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,
                memberRegisterRequest.getPassword()
        );

        authenticationRepository.save(authentication);
        return true;
    }

    @Override
    public Boolean idValidation(String id) {
        Optional<Member> maybeMember = memberRepository.findById(id);

        if (maybeMember.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean emailValidation(String email) {
        email = email.substring(1, email.length() - 1);

        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByEmail(email);
        if (maybeMemberProfile.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean phoneNumberValidation(String phoneNumber) {
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByPhoneNumber(phoneNumber);

        if (maybeMemberProfile.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest) {
        Optional<Member> maybeMember = memberRepository.findById(memberLoginRequest.getId());

        MemberLoginResponse memberLoginResponse = new MemberLoginResponse();

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();
            memberLoginResponse.setMemberNo(member.getMemberNo());

            if(!member.isRightPassword(memberLoginRequest.getPassword())) {
                memberLoginResponse.setToken("incorrect");
                return memberLoginResponse;
            }

            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getMemberNo());

            memberLoginResponse.setToken(userToken.toString());
            return memberLoginResponse;
        }

        memberLoginResponse.setToken("no");
        return memberLoginResponse;
    }

    @Override
    public void delete(Long memberNo) {
        System.out.println("서비스에서 보는 delete memberNo: "+ memberNo);
        Optional<Member> maybeMember = memberRepository.findByMemberNo(memberNo);

        if(maybeMember.isEmpty()) {
//            System.out.println("서비스에서 보는 delete: "+ maybeMember.get());
            System.out.println("서비스에서 없어 그냥 없어.");
            return;
        }

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();
            memberProfileRepository.deleteByMember(member);
            memberRepository.deleteByMemberNo(memberNo);
        }

    }

    @Override
    @Transactional
    public Boolean passwordValidation(MemberCheckPasswordRequest memberRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberNo(memberRequest.getMemberNo());

        if(maybeMember.isEmpty()) {
            System.out.println("memberNo 에 해당하는 계정이 없습니다.");
            return null;
        }

        Member member = maybeMember.get();
        if(member.isRightPassword(memberRequest.getPassword())) {
            return true;
        }

        return false;
    }

    @Override
    public MemberInfoResponse getMemberInfo(Long memberNo) {
        Optional<Member> maybeMember = memberRepository.findByMemberNo(memberNo);
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByMember_MemberNo(memberNo);

        if(maybeMember.isEmpty()) {
            return null;
        }

        MemberInfoResponse memberInfoResponse = new MemberInfoResponse(maybeMember.get(), maybeMemberProfile.get());
        return memberInfoResponse;
    }

    @Override
    public Boolean updateMemberInfo(Long memberNo, MyPageUpdateRequest myPageUpdateRequest) {
        Optional<Member> maybeMember = memberRepository.findByMemberNo(memberNo);
        Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByMember_MemberNo(memberNo);
        Optional<Authentication> maybeAuthentication = authenticationRepository.findByMember_MemberNo(memberNo);

        if(maybeMember.isEmpty()) {
            return false;
        }

        Member member = maybeMember.get();
        memberRepository.save(member);

        MemberProfile memberProfile = myPageUpdateRequest.toMemberProfile(member);
        memberProfile.setProfileId(maybeMemberProfile.get().getProfileId());
        memberProfileRepository.save(memberProfile);

        if(!myPageUpdateRequest.getNewPassword().equals("")) {
            final BasicAuthentication authentication = new BasicAuthentication(
                    member,
                    Authentication.BASIC_AUTH,
                    myPageUpdateRequest.getNewPassword()
            );

            authentication.setId(maybeAuthentication.get().getId());
            authenticationRepository.save(authentication);
        }

        return true;
    }


}