package com.capgemini.smarthire.service.test;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.services.Messages;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MessagesTest {

    
    
    @Test
    public void getStringTest() {
        String actual = Messages.getString("dummy");
        String expected = "!dummy!";
       
        Assert.assertEquals(expected, actual);
    }

}
