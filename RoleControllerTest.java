package com.capgemini.smarthire.controller.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.controller.RoleController;
import com.capgemini.smarthire.dtos.EmailDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RoleControllerTest {
    
    private static final String EMAIL = "akshat.mangal@capgemini.com";

    @Autowired
    RoleController roleController;
    
    @Test
    public void getRoleTest(){
        List<String> roleExpected = new ArrayList<>();
        roleExpected.add("Interviewer");
        
        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);
        
        List<String> roleActual = roleController.getRole(email);
        
        Assert.assertEquals(roleExpected, roleActual);
        
        
    }
    
}
