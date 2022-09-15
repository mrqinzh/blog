package com.mrqinzh.server.service;


import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.email.EmailVO;

public interface EmailService {

    Resp sendSimpleMail(EmailVO emailVO);

    Resp sendFileMail(EmailVO emailVO);
}
