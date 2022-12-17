package com.mrqinzh.core.security;

public interface SecurityService {

    SecurityUser loadSecurityUserFromDb(String username);

}
