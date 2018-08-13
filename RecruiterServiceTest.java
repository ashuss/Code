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
import com.capgemini.smarthire.dtos.EmailDto;
import com.capgemini.smarthire.dtos.RecruiterCalendarDetailsDto;
import com.capgemini.smarthire.dtos.SaveRecruiterSlotDto;
import com.capgemini.smarthire.exception.SmarthireException;
import com.capgemini.smarthire.services.RecruiterService;
import com.jcabi.log.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RecruiterServiceTest {

    private static final String EMAIL = "girjesh.soni@capgemini.com";
    private static final long RECRUITERID = 2L;
    private static final String EXCEPTION_MESSAGE = "Exception is";
    private static final String CANDIDATENAME = "Akshat";
    private static final String COMMENTS = "Mandatory interview";
    private static final String DELETECOMMENTS = "Interview mandatory";
    private static final long INTERVIEWERID = 69552L;
    private static final long NOINTERVIEWERID = 0;
    private static final long INTERVIEWTYPEID = 1L;
    private static final long NEWINTERVIEWERID = 69552;
    private static final long RECRUITERCALENDARID = 0;
    private static final long DELETERECRUITERCALENDARID = 44256;
    private static final long TECHID = 1L;
    private static final Timestamp FROMTIME = new Timestamp(Long.parseLong("1522249200000"));
    private static final Timestamp TOTIME = new Timestamp(Long.parseLong("1522252800000"));
    
    private static final Timestamp FROMTIMENEW = new Timestamp(Long.parseLong("1527150600000"));
    private static final Timestamp TOTIMENEW = new Timestamp(Long.parseLong("1527154200000"));
    

    private static final Timestamp DELETEFROMTIME = new Timestamp(Long.parseLong("1527301800000"));
    private static final Timestamp DELETETOTIME = new Timestamp(Long.parseLong("1527305400000"));
    private static final Timestamp RESCHEDULEFROM = new Timestamp(Long.parseLong("1527150600000"));
    private static final Timestamp RESCHEDULETO = new Timestamp(Long.parseLong("1527154200000"));
    private static final long RESCHEDULECALENDARID = 44256;
    private static final long RECCALID = 44232;
    private static final Timestamp FROM2 = new Timestamp(Long.parseLong("1527046200000"));
    private static final Timestamp TO2 = new Timestamp(Long.parseLong("1527049800000"));
    private static final long BUID = 1L;

    @Autowired
    RecruiterService recruiterService;

    @Test
    public void getAllRecruiterSlotsTest() {
        RecruiterCalendarDetailsDto recruiterCalendarDetailsDto = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsDto.setRecruiterId(RECRUITERID);
        List<RecruiterCalendarDetailsDto> recruiterCalendarListExpected = new ArrayList<>();
        recruiterCalendarListExpected.add(recruiterCalendarDetailsDto);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);
        List<RecruiterCalendarDetailsDto> recruiterCalendarListActual = new ArrayList<>();
        try {
            recruiterCalendarListActual = recruiterService.getAllRecruiterSlots(email);
        } catch (SmarthireException e) {
               Logger.info(e, EXCEPTION_MESSAGE);
        }

        Assert.assertNotEquals(recruiterCalendarListExpected.size(), recruiterCalendarListActual.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void saveInterviewSlotTest(){
        RecruiterCalendarDetailsDto recruiterCalendarDetailsExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsExpected.setCandidateName(CANDIDATENAME);
        
        SaveRecruiterSlotDto saveRecruiterSlotDto = new SaveRecruiterSlotDto();
        saveRecruiterSlotDto.setCandidateName(CANDIDATENAME);
        saveRecruiterSlotDto.setComments(DELETECOMMENTS);
        saveRecruiterSlotDto.setEmailId(EMAIL);
        saveRecruiterSlotDto.setFromTime(DELETEFROMTIME);
        saveRecruiterSlotDto.setInterviewerId(INTERVIEWERID);
        saveRecruiterSlotDto.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterSlotDto.setRecruiterCalendarId(DELETERECRUITERCALENDARID);
        saveRecruiterSlotDto.setTechnologyId(TECHID);
        saveRecruiterSlotDto.setToTime(DELETETOTIME);
        saveRecruiterSlotDto.setBuId(BUID);
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = new RecruiterCalendarDetailsDto();
        try {
            recruiterCalendarDetailsActual = recruiterService.saveInterviewSlot(saveRecruiterSlotDto);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        Assert.assertEquals(recruiterCalendarDetailsExpected.getCandidateName(), recruiterCalendarDetailsActual.getCandidateName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void saveInterviewSlotTest1(){
        RecruiterCalendarDetailsDto recruiterCalendarDetailsExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsExpected.setCandidateName(CANDIDATENAME);
        
        SaveRecruiterSlotDto saveRecruiterSlotDto1 = new SaveRecruiterSlotDto();
        saveRecruiterSlotDto1.setCandidateName(CANDIDATENAME);
        saveRecruiterSlotDto1.setComments(COMMENTS);
        saveRecruiterSlotDto1.setEmailId(EMAIL);
        saveRecruiterSlotDto1.setFromTime(FROMTIMENEW);
        saveRecruiterSlotDto1.setInterviewerId(INTERVIEWERID);
        saveRecruiterSlotDto1.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterSlotDto1.setRecruiterCalendarId(RECRUITERCALENDARID);
        saveRecruiterSlotDto1.setTechnologyId(TECHID);
        saveRecruiterSlotDto1.setToTime(TOTIMENEW);
        saveRecruiterSlotDto1.setBuId(BUID);
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = new RecruiterCalendarDetailsDto();
        try {
            recruiterCalendarDetailsActual = recruiterService.saveInterviewSlot(saveRecruiterSlotDto1);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        Assert.assertEquals(recruiterCalendarDetailsExpected.getCandidateName(), recruiterCalendarDetailsActual.getCandidateName());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void deleteSlotTest(){
        Boolean deleteSuccessExpected = true;
        
        SaveRecruiterSlotDto saveRecruiterSlotDto2 = new SaveRecruiterSlotDto();
        saveRecruiterSlotDto2.setCandidateName(CANDIDATENAME);
        saveRecruiterSlotDto2.setComments(DELETECOMMENTS);
        saveRecruiterSlotDto2.setEmailId(EMAIL);
        saveRecruiterSlotDto2.setFromTime(FROMTIME);
        saveRecruiterSlotDto2.setInterviewerId(INTERVIEWERID);
        saveRecruiterSlotDto2.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterSlotDto2.setRecruiterCalendarId(DELETERECRUITERCALENDARID);
        saveRecruiterSlotDto2.setTechnologyId(TECHID);
        saveRecruiterSlotDto2.setToTime(TOTIME);
        Boolean deleteSuccessFinal = false;
        try {
            deleteSuccessFinal = recruiterService.deleteSlot(saveRecruiterSlotDto2);
        } catch (SmarthireException e) {
            Logger.info(e, EXCEPTION_MESSAGE);
        }
        
        Assert.assertEquals(deleteSuccessExpected, deleteSuccessFinal);
    }
   
    
    @Test
    @Transactional
    @Rollback(true)
    public void rescheduleSlotTest(){
        RecruiterCalendarDetailsDto recruiterCalendarDetailsExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsExpected.setCandidateName(CANDIDATENAME);
        
        SaveRecruiterSlotDto saveRecruiterSlotDto3 = new SaveRecruiterSlotDto();
        saveRecruiterSlotDto3.setCandidateName(CANDIDATENAME);
        saveRecruiterSlotDto3.setComments(DELETECOMMENTS);
        saveRecruiterSlotDto3.setEmailId(EMAIL);
        saveRecruiterSlotDto3.setFromTime(RESCHEDULEFROM);
        saveRecruiterSlotDto3.setInterviewerId(INTERVIEWERID);
        saveRecruiterSlotDto3.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterSlotDto3.setRecruiterCalendarId(RESCHEDULECALENDARID);
        saveRecruiterSlotDto3.setTechnologyId(TECHID);
        saveRecruiterSlotDto3.setToTime(RESCHEDULETO);
        
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = new RecruiterCalendarDetailsDto();
        
        try {
            recruiterCalendarDetailsActual = recruiterService.rescheduleSlot(saveRecruiterSlotDto3);
        } catch (SmarthireException e) {
           Logger.info(e, EXCEPTION_MESSAGE);
        }
        
        Assert.assertEquals(recruiterCalendarDetailsExpected.getCandidateName(), recruiterCalendarDetailsActual.getCandidateName());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void rescheduleSlotTest1(){
        RecruiterCalendarDetailsDto recruiterCalendarDetailsExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsExpected.setCandidateName(CANDIDATENAME);
        
        SaveRecruiterSlotDto saveRecruiterSlotDto4 = new SaveRecruiterSlotDto();
        saveRecruiterSlotDto4.setCandidateName(CANDIDATENAME);
        saveRecruiterSlotDto4.setComments(DELETECOMMENTS);
        saveRecruiterSlotDto4.setEmailId(EMAIL);
        saveRecruiterSlotDto4.setFromTime(RESCHEDULEFROM);
        saveRecruiterSlotDto4.setOldFromTime(FROM2);
        saveRecruiterSlotDto4.setOldToTime(TO2);
        saveRecruiterSlotDto4.setInterviewerId(NEWINTERVIEWERID);
        saveRecruiterSlotDto4.setInterviewTypeId(NEWINTERVIEWERID);
        saveRecruiterSlotDto4.setRecruiterCalendarId(RECCALID);
        saveRecruiterSlotDto4.setTechnologyId(TECHID);
        saveRecruiterSlotDto4.setToTime(RESCHEDULETO);
        
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = new RecruiterCalendarDetailsDto();
        
        try {
            recruiterCalendarDetailsActual = recruiterService.rescheduleSlot(saveRecruiterSlotDto4);
        } catch (SmarthireException e) {
           Logger.info(e, EXCEPTION_MESSAGE);
        }
        
        Assert.assertEquals(recruiterCalendarDetailsExpected.getCandidateName(), recruiterCalendarDetailsActual.getCandidateName());
    }

   
    @Test
    @Transactional
    @Rollback(true)
    public void rescheduleSlotTest2(){
        RecruiterCalendarDetailsDto recruiterCalendarDetailsExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsExpected.setCandidateName(CANDIDATENAME);
        
        SaveRecruiterSlotDto saveRecruiterSlotDto5 = new SaveRecruiterSlotDto();
        saveRecruiterSlotDto5.setCandidateName(CANDIDATENAME);
        saveRecruiterSlotDto5.setComments(DELETECOMMENTS);
        saveRecruiterSlotDto5.setEmailId(EMAIL);
        saveRecruiterSlotDto5.setFromTime(RESCHEDULEFROM);
        saveRecruiterSlotDto5.setOldFromTime(FROM2);
        saveRecruiterSlotDto5.setOldToTime(TO2);
        saveRecruiterSlotDto5.setInterviewerId(NOINTERVIEWERID);
        saveRecruiterSlotDto5.setInterviewTypeId(NEWINTERVIEWERID);
        saveRecruiterSlotDto5.setRecruiterCalendarId(RECCALID);
        saveRecruiterSlotDto5.setTechnologyId(TECHID);
        saveRecruiterSlotDto5.setToTime(RESCHEDULETO);
        
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = new RecruiterCalendarDetailsDto();
        
        try {
            recruiterCalendarDetailsActual = recruiterService.rescheduleSlot(saveRecruiterSlotDto5);
        } catch (SmarthireException e) {
           Logger.info(e, EXCEPTION_MESSAGE);
        }
        
        Assert.assertEquals(recruiterCalendarDetailsExpected.getCandidateName(), recruiterCalendarDetailsActual.getCandidateName());
    }
    

}
