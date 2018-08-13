package com.capgemini.smarthire.service.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.controller.RoleController;
import com.capgemini.smarthire.dtos.EmailDto;
import com.capgemini.smarthire.services.RoleService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RoleServiceTest {

    private static final String EMAIL = "akshat.mangal@capgemini.com";
    private static final String ROLE = "Interviewer";

    @Autowired
    RoleService roleService;
    @Autowired
    RoleController roleController;

    @Test
    public void getRoleTest() {
        List<String> rolesExpected = new ArrayList<>();
        rolesExpected.add(ROLE);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);
        List<String> rolesActual = roleService.getRole(email);

        Assert.assertEquals(rolesExpected, rolesActual);

    }
    
    


    
}
