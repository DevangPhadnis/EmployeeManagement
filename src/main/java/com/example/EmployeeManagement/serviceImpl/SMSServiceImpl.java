package com.example.EmployeeManagement.serviceImpl;

import com.example.EmployeeManagement.config.TwilioConfiguration;
import com.example.EmployeeManagement.service.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    private TwilioConfiguration twilioConfiguration;

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
    }

    @Override
    public void sendSMS(String to, String messageBody) {
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioConfiguration.getTwilioNumber()),
                messageBody
        ).create();
    }
}
