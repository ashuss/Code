package com.capgemini.smarthire.controller.test;


import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.controller.ExcelReader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ExcelReaderTest {
    

    @Autowired
    ExcelReader excelreader;

    
    @Test
    @Transactional
    @Rollback(true)
    public void excelReaderTest() {
        Boolean readExp= true;
        
        Boolean readAct = excelreader.readExcel();
        
        Assert.assertEquals(readAct, readExp);
    }

   

}
