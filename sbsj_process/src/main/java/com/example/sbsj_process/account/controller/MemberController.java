    final private MemberService memberService;
    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("signUp(): " + form);

        return memberService.signUp(form.toMemberRegisterRequest());
    }

    @PostMapping("/sign-up/check-id/{id}")
    public Boolean idValidation(@PathVariable("id") String id) {
        log.info("idValidation(): " + id);

        return memberService.idValidation(id);
    }

    @PostMapping("/sign-up/check-email/{email}")
    public Boolean emailValidation(@PathVariable("email") String email) {
        log.info("emailValidation(): " + email);

        return memberService.emailValidation(email);
    }

    @PostMapping("/sign-up/check-phoneNumber/{phoneNumber}")
    public Boolean phoneNumberValidation(@PathVariable("phoneNumber") String phoneNumber) {
        log.info("phoneNumberValidation(): " + phoneNumber);

        return memberService.phoneNumberValidation(phoneNumber);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody MemberLoginForm form, HttpServletRequest request) {
        log.info("signIn(): " + form);
        System.out.println("컨트롤러 signIn 에서 보는: "+ request);

        return memberService.signIn(form.toMemberLoginRequest(), request);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String token) {
        token = token.substring(0, token.length() - 1);
        log.info("logout(): " + token);

        redisService.deleteByKey(token);
    }
