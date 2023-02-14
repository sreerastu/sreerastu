package com.app.sreerastu.services;

import com.app.sreerastu.domain.Vendor;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    final static Logger log = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String sender;

    private JavaMailSender javaMailSender;
    private VendorServiceImpl vendorService;

    public MailService(JavaMailSender javaMailSender, VendorServiceImpl vendorService) {
        this.javaMailSender = javaMailSender;
        this.vendorService = vendorService;
    }


    public String sendMail(String emailAddress) throws Exception {

       // Vendor vendor = new Vendor();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Password Reset");
        String randomPwd = sendTempPassword();
        helper.setText("Your temporary new password is " + randomPwd + ",Please change your password at application");
        helper.setFrom(sender);
        helper.setTo(emailAddress);
        vendorService.updatePassword(randomPwd);
        // vendor.setPassword(randomPwd);
        javaMailSender.send(message);
        log.info("Mail Sent Successfully......");
        return randomPwd;

    }

    public String sendTempPassword() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String pwd = RandomStringUtils.random(10, characters);
        log.info(pwd);
        return pwd;
    }

}
