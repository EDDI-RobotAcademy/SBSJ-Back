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
        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();

            if(!member.isRightPassword(memberLoginRequest.getPassword())) {
                return "틀림";
            }

            UUID userToken = UUID.randomUUID();

            // redis 처리 필요
            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), member.getMemberNo());

            return userToken.toString();
        }

        return "없음";
    }

    @Override
    public void delete(Long memberNo) {
        System.out.println("서비스에서 보는 delete memberNo: "+ memberNo);
