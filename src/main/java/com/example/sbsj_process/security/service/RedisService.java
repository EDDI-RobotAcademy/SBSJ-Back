package com.example.sbsj_process.security.service;

public interface RedisService {

    public void setKeyAndValue(String token, Long memberId);
    public Long getValueByKey(String token);
    public void deleteByKey(String token);
}
