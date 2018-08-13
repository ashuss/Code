package com.capgemini.smarthire.controller.test;

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
import com.capgemini.smarthire.controller.RecruiterController;
import com.capgemini.smarthire.dtos.EmailDto;
import com.capgemini.smarthire.dtos.RecruiterCalendarDetailsDto;
import com.capgemini.smarthire.dtos.ResponseDto;
import com.capgemini.smarthire.dtos.SaveRecruiterSlotDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RecruiterControllerTest {

    private static final String EMAIL = "girjesh.soni@capgemini.com";
    private static final long RECRUITERID = 2L;
    private static final String CANDIDATENAME = "Akshat";
    private static final String DELETECOMMENTS = "Interview mandatory";

    private static final long INTERVIEWERID = 69552L;
    private static final long NOINTERVIEWERID = 0;
    private static final long INTERVIEWTYPEID = 1L;
    private static final long DELETERECRUITERCALENDARID = 44256;
    private static final long TECHID = 1L;
    private static final Timestamp FROMTIME = new Timestamp(Long.parseLong("1522249200000"));
    private static final Timestamp TOTIME = new Timestamp(Long.parseLong("1522252800000"));

    private static final Timestamp DELETEFROMTIME = new Timestamp(Long.parseLong("1527301800000"));
    private static final Timestamp DELETETOTIME = new Timestamp(Long.parseLong("1527305400000"));

    private static final Timestamp RESCHEDULEFROM = new Timestamp(Long.parseLong("1527301800000"));
    private static final Timestamp RESCHEDULETO = new Timestamp(Long.parseLong("1527305400000"));
    private static final long RESCHEDULECALENDARID = 44256;
    private static final long BUID = 1L;

    @Autowired
    RecruiterController recruiterController;

    @Test
    @Transactional
    public void getAllRecruiterSlotsTest() {
        RecruiterCalendarDetailsDto recruiterCalendarDetailsDto = new RecruiterCalendarDetailsDto();
        recruiterCalendarDetailsDto.setRecruiterId(RECRUITERID);
        List<RecruiterCalendarDetailsDto> recruiterCalendarListExpected = new ArrayList<>();
        recruiterCalendarListExpected.add(recruiterCalendarDetailsDto);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);

        ResponseDto responseFinal = recruiterController.getAllRecruiterSlots(email);

        List<RecruiterCalendarDetailsDto> recruiterCalendarListActual = (List<RecruiterCalendarDetailsDto>) responseFinal
                .getResponse().get(0);

        Assert.assertNotEquals(recruiterCalendarListExpected.size(), recruiterCalendarListActual.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void saveInterviewSlot() {
        RecruiterCalendarDetailsDto recruiterCalendarExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarExpected.setCandidateName(CANDIDATENAME);

        SaveRecruiterSlotDto saveRecruiterDto = new SaveRecruiterSlotDto();
        saveRecruiterDto.setCandidateName(CANDIDATENAME);
        saveRecruiterDto.setComments(DELETECOMMENTS);
        saveRecruiterDto.setEmailId(EMAIL);
        saveRecruiterDto.setFromTime(DELETEFROMTIME);
        saveRecruiterDto.setInterviewerId(NOINTERVIEWERID);
        saveRecruiterDto.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterDto.setRecruiterCalendarId(DELETERECRUITERCALENDARID);
        saveRecruiterDto.setTechnologyId(TECHID);
        saveRecruiterDto.setToTime(DELETETOTIME);
        saveRecruiterDto.setBuId(BUID);
        saveRecruiterDto.setInterviewerId(INTERVIEWERID);
        ResponseDto responseFinal = recruiterController.saveInterviewSlot(saveRecruiterDto);
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = (RecruiterCalendarDetailsDto) responseFinal
                .getResponse().get(0);
        Assert.assertEquals(recruiterCalendarExpected.getCandidateName(),
                recruiterCalendarDetailsActual.getCandidateName());
    }

    @Test
    @Rollback(true)
    @Transactional
    public void deleteSlotControllerTest() {
        Boolean deleteSuccessExp = true;

        SaveRecruiterSlotDto saveRecruiterDto = new SaveRecruiterSlotDto();
        saveRecruiterDto.setCandidateName(CANDIDATENAME);
        saveRecruiterDto.setComments(DELETECOMMENTS);
        saveRecruiterDto.setEmailId(EMAIL);
        saveRecruiterDto.setFromTime(FROMTIME);
        saveRecruiterDto.setInterviewerId(INTERVIEWERID);
        saveRecruiterDto.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterDto.setRecruiterCalendarId(DELETERECRUITERCALENDARID);
        saveRecruiterDto.setTechnologyId(TECHID);
        saveRecruiterDto.setToTime(TOTIME);

        ResponseDto responseFinal = recruiterController.deleteSlot(saveRecruiterDto);
        Boolean deleteSuccessFinal = (Boolean) responseFinal.getResponse().get(0);

        Assert.assertEquals(deleteSuccessExp, deleteSuccessFinal);
    }
    
    @Test
    @Rollback(true)
    @Transactional
    public void rescheduleSlotControllerTest(){
        RecruiterCalendarDetailsDto recruiterCalendarExpected = new RecruiterCalendarDetailsDto();
        recruiterCalendarExpected.setCandidateName(CANDIDATENAME);
        
        SaveRecruiterSlotDto saveRecruiterDto = new SaveRecruiterSlotDto();
        saveRecruiterDto.setCandidateName(CANDIDATENAME);
        saveRecruiterDto.setComments(DELETECOMMENTS);
        saveRecruiterDto.setEmailId(EMAIL);
        saveRecruiterDto.setFromTime(RESCHEDULEFROM);
        saveRecruiterDto.setInterviewerId(INTERVIEWERID);
        saveRecruiterDto.setInterviewTypeId(INTERVIEWTYPEID);
        saveRecruiterDto.setRecruiterCalendarId(RESCHEDULECALENDARID);
        saveRecruiterDto.setTechnologyId(TECHID);
        saveRecruiterDto.setToTime(RESCHEDULETO);
        saveRecruiterDto.setBuId(BUID);
        
        
        ResponseDto responseFinal = recruiterController.rescheduleSlot(saveRecruiterDto);
        RecruiterCalendarDetailsDto recruiterCalendarDetailsActual = (RecruiterCalendarDetailsDto) responseFinal.getResponse().get(0); 
        
        Assert.assertEquals(recruiterCalendarExpected.getCandidateName(), recruiterCalendarDetailsActual.getCandidateName());
    }

}
