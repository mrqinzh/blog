package com.mrqinzh.blog.service;

import com.mrqinzh.blog.model.dto.req.EmailDTO;
import com.mrqinzh.blog.model.dto.resp.Resp;

/**
 *  company 中科大业
 *  FileName EmailService
 *  Package com.mrqinzh.blog.service
 *  Description 
 *  author BIM7
 *  create 2021-08-26 11:15
 *  version V1.0
 */
public interface EmailService {


    Resp sendSimpleMail(EmailDTO emailDTO);

    Resp sendFileMail(EmailDTO emailDTO);
}
