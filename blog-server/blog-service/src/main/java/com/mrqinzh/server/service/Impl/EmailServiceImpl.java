package com.mrqinzh.server.service.Impl;

import com.mrqinzh.common.exception.BizException;
import com.mrqinzh.common.model.enums.AppStatus;
import com.mrqinzh.common.model.resp.Resp;
import com.mrqinzh.common.model.vo.email.EmailVO;
import com.mrqinzh.common.util.RedisUtil;
import com.mrqinzh.server.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author mrqinzh
 * @description 发送邮件
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public Resp sendSimpleMail(EmailVO emailVO) {
        try {
            send(emailVO.getEmailTitle(), emailVO.getEmailContent(), null, false, emailVO.getTo());
            return Resp.sendMsg(AppStatus.EMAIL_SEND_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(AppStatus.INTERNET_ERROR);
        }
    }

    @Override
    public Resp sendFileMail(EmailVO emailVO) {
        try {
            send(emailVO.getEmailTitle(), emailVO.getEmailContent(), emailVO.getFilePath(), false, emailVO.getTo());
            logger.info("邮件加附件发送成功！");
            return Resp.sendMsg(AppStatus.EMAIL_SEND_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(AppStatus.INTERNET_ERROR);
        }
    }

    public void send(String title, String content, String filePath, boolean isHtml, String to) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(title); // 邮件主题
        messageHelper.setText(content, isHtml);
        if (filePath != null) {
            // 携带附件
            FileSystemResource file = new FileSystemResource(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName,file);
        }
        javaMailSender.send(message);
    }


}
