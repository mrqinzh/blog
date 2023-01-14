package com.mrqinzh.core.auth;

import com.mrqinzh.common.model.resp.Resp;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public interface RedirectStrategy {

    void redirect(HttpServletRequest request, HttpServletResponse response, Resp resp, String url);

}
