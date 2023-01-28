package com.mrqinzh.core.auth;

import com.mrqinzh.common.model.resp.Resp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RedirectStrategy {

    void redirect(HttpServletRequest request, HttpServletResponse response, Resp resp);

}
