package com.capgemini.smarthire.service.test;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.dtos.LookupDTO;
import com.capgemini.smarthire.exception.SmarthireException;
import com.capgemini.smarthire.services.LookupService;
import com.jcabi.log.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LookupServiceTest {
    
    private static final String EXCEPTION_MESSAGE = "Exception is";

    private static final long SCREENID = 1L;
    private static final long SCREENID1 = 2L;
    private static final String INTERVIEWTYPE = "interviewtype";

    @Autowired
    LookupService lookupService;

    @SuppressWarnings("unchecked")
    @Test
    public void fetchDropdownTest() {

        LookupDTO lookupDto = new LookupDTO();
        lookupDto.setDropdownName(INTERVIEWTYPE);
        JSONObject dropdownJson = new JSONObject();
        dropdownJson.put("1", "L1");
        lookupDto.setDropdown(dropdownJson);

        List<LookupDTO> lookupDtoExpected = new ArrayList<>();
        lookupDtoExpected.add(lookupDto);
 
        List<LookupDTO> loopDtoActual = new ArrayList<>() ;
        try {
            loopDtoActual = lookupService.fetchDropdown(SCREENID);
            
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        
        Assert.assertNotEquals(lookupDtoExpected.size(), loopDtoActual.size());

    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void fetchDropdownTest1() {

        LookupDTO lookupDto = new LookupDTO();
        lookupDto.setDropdownName(INTERVIEWTYPE);
        JSONObject dropdownJson = new JSONObject();
        dropdownJson.put("2", "L2");
        lookupDto.setDropdown(dropdownJson);

        List<LookupDTO> lookupDtoExpected = new ArrayList<>();
        lookupDtoExpected.add(lookupDto);
        
        List<LookupDTO> loopDtoActual = new ArrayList<>();
        try {
            loopDtoActual = lookupService.fetchDropdown(SCREENID1);
            
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        Assert.assertEquals(lookupDtoExpected.size(),loopDtoActual.size());

    }

}
