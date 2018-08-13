package com.capgemini.smarthire.service.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.dtos.CheckAvailabilityDTO;
import com.capgemini.smarthire.dtos.EmailDto;
import com.capgemini.smarthire.dtos.InterviewerCalendarSavedSlotDTO;
import com.capgemini.smarthire.dtos.InterviewerCalenderDetailsDto;
import com.capgemini.smarthire.dtos.InterviewerDropdownDTO;
import com.capgemini.smarthire.dtos.InterviewerDropdownRequestDTO;
import com.capgemini.smarthire.dtos.InterviewerSaveSlotDto;
import com.capgemini.smarthire.exception.SmarthireException;
import com.capgemini.smarthire.services.InterviewerService;

import com.jcabi.log.Logger;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class InterviewerServiceTest {

    private static final String EMAIL = "akshat.mangal@capgemini.com";
    private static final String EMAIL1 = "kushal.sanghavi@capgemini.com";
	private static final String RECRUITEREMAIL = "girjesh.soni@capgemini.com";
    private static final long TECHID = 1L;
    private static final long BUID = 1L;
    private static final long INTERVIEWTYPEID = 1L;
    private static final long INTERVIEWTYPEID1 = 2L;
    private static final Timestamp FROMTIME = new Timestamp(Long.parseLong("1532779200000"));
    private static final Timestamp TOTIME = new Timestamp(Long.parseLong("1532782800000"));
    private static final Timestamp DROPDOWNFROMTIME = new Timestamp(Long.parseLong("1521181800000"));
    private static final Timestamp DROPDOWNTOTIME = new Timestamp(Long.parseLong("1521185400000"));
    private static final long DELETECALENDARID = 44234L;
    private static final long SAVECALENDARID = 0;
    private static final long DATECALENDARID = 44234L;
    private static final long SAVENEWCALENDARID = 44234L;
    private static final String EXCEPTION_MESSAGE = "Exception is";
    private static final Timestamp NEWFROMTIME = new Timestamp(Long.parseLong("1522231200000"));
    private static final Timestamp NEWTOTIME = new Timestamp(Long.parseLong("1522234800000"));
    private static final Timestamp DATEFROMTIME = new Timestamp(Long.parseLong("1527150600000"));
    private static final Timestamp DATETOTIME = new Timestamp(Long.parseLong("1527154200000"));
	private static final long PARTICIPATIONTYPEID = 1L;
    private static final long NEWPARTICIPATIONTYPEID = 2L;

    private static final Timestamp NEWFROMTIME1 = new Timestamp(Long.parseLong("1521181800000"));
    private static final Timestamp NEWTOTIME1 = new Timestamp(Long.parseLong("1521185400000"));
    
    private static final Timestamp ELSENEWFROMTIME1 = new Timestamp(Long.parseLong("1527046200000"));
    private static final Timestamp ELSENEWTOTIME1 = new Timestamp(Long.parseLong("1527049800000"));
    
    
    @Autowired
    InterviewerService interviewerService;
   
    @Test
    public void getInterviewerSlotsTest() {

        InterviewerCalenderDetailsDto interviewerCalendar = new InterviewerCalenderDetailsDto();
        interviewerCalendar.setCalendarId(1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListExpected = new ArrayList<>();
        interviewerCalendarListExpected.add(interviewerCalendar);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);
        
        CheckAvailabilityDTO checkAvailabilityDTO = new CheckAvailabilityDTO();
        checkAvailabilityDTO.setEmailId(RECRUITEREMAIL);
        System.out.println("fddg"+RECRUITEREMAIL);
        checkAvailabilityDTO.setBuId(BUID);
        System.out.println("esd"+BUID);
        checkAvailabilityDTO.setInterviewerTypeId(INTERVIEWTYPEID);
        System.out.println("zxcv"+INTERVIEWTYPEID);
        
        checkAvailabilityDTO.setTechnologyId(TECHID);
        
        System.out.println("gfh"+TECHID);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListActual = new ArrayList<>();
        try {
            interviewerCalendarListActual = interviewerService.getInterviewersSlots(checkAvailabilityDTO);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertEquals(interviewerCalendarListExpected.size(), interviewerCalendarListActual.size());
    }
    
    @Test
    public void getInterviewerSlotsTest1() {

        InterviewerCalenderDetailsDto interviewerCalendar = new InterviewerCalenderDetailsDto();
        interviewerCalendar.setCalendarId(1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListExpected = new ArrayList<>();
        interviewerCalendarListExpected.add(interviewerCalendar);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);
        
        CheckAvailabilityDTO checkAvailabilityDTO = new CheckAvailabilityDTO();
        checkAvailabilityDTO.setEmailId(EMAIL1);
        
        checkAvailabilityDTO.setBuId(BUID);
      
        checkAvailabilityDTO.setInterviewerTypeId(INTERVIEWTYPEID);
       
        
        checkAvailabilityDTO.setTechnologyId(TECHID);
        
        
        List<InterviewerCalenderDetailsDto> interviewerCalendarListActual = new ArrayList<>();
        try {
            interviewerCalendarListActual = interviewerService.getInterviewersSlots(checkAvailabilityDTO);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertNotEquals(interviewerCalendarListExpected.size(), interviewerCalendarListActual.size());
    }
    
   
    
   
    
    @Test
    public void getAllInterviewerSlotsTest() {

        InterviewerCalenderDetailsDto interviewerCalendar = new InterviewerCalenderDetailsDto();
        interviewerCalendar.setCalendarId(1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListExpected = new ArrayList<>();
        interviewerCalendarListExpected.add(interviewerCalendar);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListActual = new ArrayList<>();
        try {
            interviewerCalendarListActual = interviewerService.getAllInterviewerSlots(email);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertNotEquals(interviewerCalendarListExpected.size(), interviewerCalendarListActual.size());
    }

    @Test
    public void getAllInterviewerSlotsTest1() {

        InterviewerCalenderDetailsDto interviewerCalendar = new InterviewerCalenderDetailsDto();
        interviewerCalendar.setCalendarId(1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListExpected = new ArrayList<>();
        interviewerCalendarListExpected.add(interviewerCalendar);

        EmailDto email = new EmailDto();
        email.setEmail(RECRUITEREMAIL);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListActual = new ArrayList<>();
        try {
            interviewerCalendarListActual = interviewerService.getAllInterviewerSlots(email);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertNotEquals(interviewerCalendarListExpected.size(), interviewerCalendarListActual.size());
    }

    @Test
    public void fetchInterviewerDropdownTest() {

        InterviewerDropdownDTO interviewer = new InterviewerDropdownDTO();
        interviewer.setInterviewerId(1);
        List<InterviewerDropdownDTO> interviewerListExpected = new ArrayList<>();
        interviewerListExpected.add(interviewer);

        InterviewerDropdownRequestDTO interviewerDropdownRequestDTO = new InterviewerDropdownRequestDTO();
        interviewerDropdownRequestDTO.setTechnologyId(TECHID);
        interviewerDropdownRequestDTO.setInterviewTypeId(INTERVIEWTYPEID);
        interviewerDropdownRequestDTO.setFromTime(DROPDOWNFROMTIME);
        interviewerDropdownRequestDTO.setToTime(DROPDOWNTOTIME);
        interviewerDropdownRequestDTO.setBuId(BUID);
        List<InterviewerDropdownDTO> interviewerListActual = new ArrayList<>();
        try {
            interviewerListActual = interviewerService.fetchInterviewerDropdown(interviewerDropdownRequestDTO);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertEquals(interviewerListExpected.size(), interviewerListActual.size());
    }
   

    @Test
    @Transactional
    @Rollback(true)
    public void deleteInterviewSlotTest() {
        Boolean deleteSuccessExpected = true;
        Boolean deleteSuccessActual = false;
        try {
            deleteSuccessActual = interviewerService.deleteInterviewSlot(DELETECALENDARID);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertEquals(deleteSuccessExpected, deleteSuccessActual);

    }
   

    
    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest() {
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL);

        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(SAVECALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL);
        interviewerSaveSlotDto.setFromTime(FROMTIME);
        interviewerSaveSlotDto.setToTime(TOTIME);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
        try {
            interviewerCalendarSavedSlotActual = interviewerService.saveFreeSlot(interviewerSaveSlotDto);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        
        Assert.assertEquals(interviewerCalendarSavedSlotExpected.getEmail(),
              interviewerCalendarSavedSlotActual.getEmail());

    }
    
     
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest1() {
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL1);

        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(DATECALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL1);
        interviewerSaveSlotDto.setFromTime(DATEFROMTIME);
        interviewerSaveSlotDto.setToTime(DATETOTIME);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
        try {
            interviewerService.saveFreeSlot(interviewerSaveSlotDto);
        } catch (SmarthireException e) {
            Assert.assertNotNull(e);
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        
    }
    

   @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest2() {
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL);

        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(SAVECALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL);
        interviewerSaveSlotDto.setFromTime(NEWFROMTIME);
        interviewerSaveSlotDto.setToTime(NEWTOTIME);
        interviewerSaveSlotDto.setParticipationTypeId(NEWPARTICIPATIONTYPEID);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
        try {
            interviewerCalendarSavedSlotActual = interviewerService.saveFreeSlot(interviewerSaveSlotDto);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertEquals(interviewerCalendarSavedSlotExpected.getEmail(),
                interviewerCalendarSavedSlotActual.getEmail());

    }
  
   
    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest3() {
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL);

        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(SAVENEWCALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL);
        interviewerSaveSlotDto.setFromTime(NEWFROMTIME);
        interviewerSaveSlotDto.setToTime(NEWTOTIME);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
        try {
            interviewerCalendarSavedSlotActual = interviewerService.saveFreeSlot(interviewerSaveSlotDto);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertEquals(interviewerCalendarSavedSlotExpected.getEmail(),
                interviewerCalendarSavedSlotActual.getEmail());

    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest4() {
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL1);
String exception = null;
String expected="";
        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(SAVECALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL1);
        interviewerSaveSlotDto.setFromTime(NEWFROMTIME1);
        interviewerSaveSlotDto.setToTime(NEWTOTIME1);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
        try {
            interviewerCalendarSavedSlotActual = interviewerService.saveFreeSlot(interviewerSaveSlotDto);
        } catch (SmarthireException e) {
        	exception = e.getMessage();
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertNotEquals(expected,exception);

    }
   
    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest5() {
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(RECRUITEREMAIL);
        String exception = null;
        String expected="YOU ARE NOT AUTHORIZED TO ADD INTERVIEW SLOTS";
        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(SAVENEWCALENDARID);
        interviewerSaveSlotDto.setEmail(RECRUITEREMAIL);
        interviewerSaveSlotDto.setFromTime(NEWFROMTIME);
        interviewerSaveSlotDto.setToTime(NEWTOTIME);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
        try {
            interviewerCalendarSavedSlotActual = interviewerService.saveFreeSlot(interviewerSaveSlotDto);
        } catch (SmarthireException e) {
        	exception = e.getMessage();
            Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertEquals(expected,exception);

    }
    
}
