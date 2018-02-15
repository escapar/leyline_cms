package com.k41d.cms.interfaces.rest;


import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/ScheduledController")
public class ScheduledController {


    @Scheduled(cron = "0 0 2 * * ?") // cron = 0 0 2 * * ?  fixedRate = 3000
    @RequestMapping(value = "/springScheduled", method = RequestMethod.POST)
    public void springScheduled() {


    }



}
