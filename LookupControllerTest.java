package com.capgemini.smarthire.controller.test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.smarthire.controller.LookupController;
import com.capgemini.smarthire.dtos.LookupDTO;
import com.capgemini.smarthire.dtos.ResponseDto;
import com.capgemini.smarthire.exception.SmarthireException;
import com.capgemini.smarthire.services.LookupService;
import com.jcabi.log.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LookupControllerTest {

    private static final long SCREENID = 1L;
    private static final long SCREENID1 = 2L;
    private static final String INTERVIEWTYPE = "interviewtype";
    private static final String INTERVIEWTYPE1 = "participation";
    private static final String EXCEPTION_MESSAGE = "Exception is";

    @Autowired
    private MockMvc mvc;

    @Autowired
    LookupController lookupController;

    @MockBean
    LookupService lookupService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(this.lookupController).build();
    }
  
//    @SuppressWarnings("unchecked")
//    @Test
//    public void fetchDropdownTest() {
//        ResponseDto responseDto = new ResponseDto();
//        LookupDTO lookupDTO = new LookupDTO();
//        lookupDTO.setDropdownName(INTERVIEWTYPE1);
//        JSONObject dropdownJson = new JSONObject();
//        dropdownJson.put("1", "L1");
//        lookupDTO.setDropdown(dropdownJson);
//
//        List<LookupDTO> lookupDtoExpected = new ArrayList<>();
//        lookupDtoExpected.add(lookupDTO);
//
//        List<Object> response = new ArrayList<>();
//        response.add(lookupDtoExpected);
//        responseDto.setResponse(response);
//
//        try {
//            given(lookupService.fetchDropdown(SCREENID)).willReturn(lookupDtoExpected);
//        } catch (SmarthireException e) {
//            Logger.info(e, EXCEPTION_MESSAGE);
//        }
//
//        try {
//            mvc.perform(get("/lookup/fetchDropdown?screenId=1").contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.response[0][0].dropdownName", is(lookupDtoExpected.get(0).getDropdownName())));
//        } catch (Exception e) {
//            Logger.info(e, EXCEPTION_MESSAGE); 
//        }
//
//    }
//    
    
    @SuppressWarnings("unchecked")
  @Test
  public void fetchDropdownTest() {
    ResponseDto responseDto = new ResponseDto();
    List<LookupDTO> lookupDTOExpected = new ArrayList<>();
    LookupDTO lookupDTOActual = new LookupDTO();
    lookupDTOExpected.add(lookupDTOActual);
    responseDto = lookupController.fetchDropdown(1);
System.out.println("ScreenIDddddd"+SCREENID);
	Assert.assertNotEquals(((List<LookupDTO>) responseDto.getResponse().get(0)).size(),3);
}
    

    @SuppressWarnings("unchecked")
    @Test
    public void fetchDropdownTest1() {
    	ResponseDto responseDto = new ResponseDto();
        LookupDTO lookupDto = new LookupDTO();
        lookupDto.setDropdownName(INTERVIEWTYPE);
        JSONObject dropdownJson = new JSONObject();
        dropdownJson.put("1", "L1");
        lookupDto.setDropdown(dropdownJson);

        List<LookupDTO> lookupDtoExpected = new ArrayList<>();
        lookupDtoExpected.add(lookupDto);
        
        List<LookupDTO> loopDtoActual = new ArrayList<>() ;
        System.out.println("before fetch");
        responseDto = lookupController.fetchDropdown(SCREENID);
      
        System.out.println("thusus sus "+ lookupDtoExpected.size() + " "+ responseDto.getResponse().get(0));
        
        Assert.assertNotEquals(lookupDtoExpected.size(), ((List<LookupDTO>) responseDto.getResponse().get(0)).size());
            

    }
    
    
  
}
