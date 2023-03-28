



    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody MemberRegisterForm form) {
        log.info("sign-up: " + form);

        return memberService.signUp(form.toMemberRegisterRequest());
    }
}
