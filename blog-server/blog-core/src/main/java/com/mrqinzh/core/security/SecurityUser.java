package com.mrqinzh.core.security;

import com.mrqinzh.core.access.RoleType;

import java.io.Serializable;
import java.util.List;

public interface SecurityUser extends Serializable {

    Integer getId();

    String getName();

    String getPassword();

    List<RoleType> authorities();

    // todo accountLocked...
}
