package com.app.sreerastu.services;

import com.app.sreerastu.domain.Admin;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.repositories.AdminRepository;
import com.app.sreerastu.repositories.UserRepository;
import com.app.sreerastu.repositories.VendorRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    final static Logger log = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String sender;

    private VendorRepository vendorRepository;

    private UserRepository userRepository;

    private AdminRepository adminRepository;

    private JavaMailSender javaMailSender;
    private VendorServiceImpl vendorService;

    @Autowired
    public MailService(VendorRepository vendorRepository, UserRepository userRepository, AdminRepository adminRepository, JavaMailSender javaMailSender, VendorServiceImpl vendorService) {
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
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

        Vendor vendorByEmailAddress = vendorService.getVendorByEmailAddress(emailAddress);
        User userByEmailAddress = userRepository.findByEmailAddress(emailAddress);
        Admin adminByEmailAddress = adminRepository.findByEmailAddress(emailAddress);

        if (vendorByEmailAddress != null) {
            vendorByEmailAddress.setPassword(randomPwd);
            vendorRepository.save(vendorByEmailAddress);
        } else if (userByEmailAddress != null) {
            userByEmailAddress.setPassword(randomPwd);
            userRepository.save(userByEmailAddress);
        } else if(adminByEmailAddress!=null) {
            adminByEmailAddress.setPassword(randomPwd);
            adminRepository.save(adminByEmailAddress);
        }else {
            throw new AuthenticationException("Invalid EmailAddress");
        }

        javaMailSender.send(message);
        log.info("Mail Sent Successfully......");
        return "Mail Sent Successfully......!";
    }

    public String sendTempPassword() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String pwd = RandomStringUtils.random(10, characters);
        log.info(pwd);
        return pwd;
    }
}
