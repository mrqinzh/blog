package com.mrqinzh.core.security;

import java.io.Serializable;

public interface SecurityUser extends Serializable {

    String getName();

    String getPassword();

    // todo accountLocked...
}
