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
import com.capgemini.smarthire.controller.InterviewerController;
import com.capgemini.smarthire.dtos.CheckAvailabilityDTO;
import com.capgemini.smarthire.dtos.EmailDto;
import com.capgemini.smarthire.dtos.FeedbackDTO;
import com.capgemini.smarthire.dtos.InterviewerCalendarSavedSlotDTO;
import com.capgemini.smarthire.dtos.InterviewerCalenderDetailsDto;
import com.capgemini.smarthire.dtos.InterviewerDropdownDTO;
import com.capgemini.smarthire.dtos.InterviewerDropdownRequestDTO;
import com.capgemini.smarthire.dtos.InterviewerSaveSlotDto;
import com.capgemini.smarthire.dtos.ReportDTO;
import com.capgemini.smarthire.dtos.RescheduleRequestDto;
import com.capgemini.smarthire.dtos.ResponseDto;
import com.capgemini.smarthire.dtos.SmarthireReportDTO;
import com.capgemini.smarthire.exception.SmarthireException;
import com.jcabi.log.Logger;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class InterviewerControllerTest {

    private static final String EMAIL = "kushal.sanghavi@capgemini.com";
    private static final String RECRUITEREMAIL = "girjesh.soni@capgemini.com";
    private static final long TECHID = 43069;
    private static final long TECHID1 = 1;
    private static final long DAYSFILTER = 1;
    private static final long BUID = 1L;
    private static final long INTERVIEWTYPEID = 1L;
    private static final long INTERVIEWTYPEID1 = 0L;
    private static final Timestamp FROMTIME = new Timestamp(Long.parseLong("1530183600000"));
    private static final Timestamp TOTIME = new Timestamp(Long.parseLong("1530187200000"));
    private static final Timestamp DROPDOWNFROMTIME = new Timestamp(Long.parseLong("1521181800000"));
    private static final Timestamp DROPDOWNTOTIME = new Timestamp(Long.parseLong("1521185400000"));
    private static final Timestamp NEWFROMTIME1 = new Timestamp(Long.parseLong("1521181800000"));
    private static final Timestamp NEWTOTIME1 = new Timestamp(Long.parseLong("1521185400000"));
    private static final long DELETECALENDARID = 44234L;
    private static final long SAVECALENDARID = 44234L;
    private static final long ELSESAVECALENDARID = 0;
    private static final long FEEDBACK_CALID = 44229;
    private static final long FEEDBACK_STATUSID = 1;
    private static final String FEEDBACK_COMMENT = "good";
	private static final long PARTICIPATIONTYPEID = 1L;
	private static final String STATUS = "PASS";
	
	private static final Timestamp FROM1 = new Timestamp(Long.parseLong("1527042600000"));
    private static final Timestamp TO1 = new Timestamp(Long.parseLong("1527773400000"));
 
    @Autowired
    InterviewerController interviewerController;
    
    @Test
    @Transactional 
    public void getInterviewerSlotsTest() throws SmarthireException {
        ResponseDto responseExpected = new ResponseDto();

        InterviewerCalenderDetailsDto interviewerCalendar = new InterviewerCalenderDetailsDto();
        interviewerCalendar.setCalendarId(1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListExpected = new ArrayList<>();
        interviewerCalendarListExpected.add(interviewerCalendar);
        List<Object> response = new ArrayList<>();
        response.add(interviewerCalendarListExpected);
        responseExpected.setResponse(response);

        CheckAvailabilityDTO checkAvailabilityDTO = new CheckAvailabilityDTO();
        checkAvailabilityDTO.setEmailId(RECRUITEREMAIL);
        checkAvailabilityDTO.setBuId(BUID);
        checkAvailabilityDTO.setInterviewerTypeId(INTERVIEWTYPEID);
        checkAvailabilityDTO.setTechnologyId(TECHID);

        ResponseDto responseActual = interviewerController.getInterviewerSlots(checkAvailabilityDTO);
        Assert.assertEquals(responseExpected.getResponse().size(), responseActual.getResponse().size());

    }

    @Test
    @Transactional
    public void getAllInterviewerSlotsTest() throws SmarthireException {
        ResponseDto responseExpected = new ResponseDto();

        InterviewerCalenderDetailsDto interviewerCalendar = new InterviewerCalenderDetailsDto();
        interviewerCalendar.setCalendarId(1);
        List<InterviewerCalenderDetailsDto> interviewerCalendarListExpected = new ArrayList<>();
        interviewerCalendarListExpected.add(interviewerCalendar);
        List<Object> response = new ArrayList<>();
        response.add(interviewerCalendarListExpected);
        responseExpected.setResponse(response);

        EmailDto email = new EmailDto();
        email.setEmail(EMAIL);
        ResponseDto responseActual = interviewerController.getAllInterviewerSlots(email);

        Assert.assertEquals(responseExpected.getResponse().size(), responseActual.getResponse().size());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest() {
        ResponseDto responseExpected = new ResponseDto();
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL);
        List<Object> response = new ArrayList<>();
        response.add(interviewerCalendarSavedSlotExpected);
        responseExpected.setResponse(response);

        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(SAVECALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL);
        interviewerSaveSlotDto.setFromTime(FROMTIME);
        interviewerSaveSlotDto.setToTime(TOTIME);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);

        ResponseDto responseActual = interviewerController.saveFreeSlot(interviewerSaveSlotDto);
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotFinal = (InterviewerCalendarSavedSlotDTO) responseActual
                .getResponse().get(0);

        Assert.assertEquals(interviewerCalendarSavedSlotExpected.getEmail(),
                interviewerCalendarSavedSlotFinal.getEmail());
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void saveFreeSlotTest1() {
    	ResponseDto responseExpected = new ResponseDto();
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotExpected = new InterviewerCalendarSavedSlotDTO();
        interviewerCalendarSavedSlotExpected.setEmail(EMAIL);
        
        List<Object> response = new ArrayList<>();
        response.add(interviewerCalendarSavedSlotExpected);
        responseExpected.setResponse(response);
        String exception = null;
        String expected="";
        InterviewerSaveSlotDto interviewerSaveSlotDto = new InterviewerSaveSlotDto();
        interviewerSaveSlotDto.setCalendarId(ELSESAVECALENDARID);
        interviewerSaveSlotDto.setEmail(EMAIL);
        interviewerSaveSlotDto.setFromTime(NEWFROMTIME1);
        interviewerSaveSlotDto.setToTime(NEWTOTIME1);
        interviewerSaveSlotDto.setParticipationTypeId(PARTICIPATIONTYPEID);
        
        InterviewerCalendarSavedSlotDTO interviewerCalendarSavedSlotActual = new InterviewerCalendarSavedSlotDTO();
       
        ResponseDto responseActual = interviewerController.saveFreeSlot(interviewerSaveSlotDto);
        
        Assert.assertNotEquals(expected,exception);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void fetchInterviewerDropdownControllerTest() {
        InterviewerDropdownDTO interviewer1 = new InterviewerDropdownDTO();
        interviewer1.setInterviewerId(1);
        List<InterviewerDropdownDTO> interviewerListExpected1 = new ArrayList<>();
        interviewerListExpected1.add(interviewer1);

        InterviewerDropdownRequestDTO interviewerDropdownRequestDTO1 = new InterviewerDropdownRequestDTO();
        interviewerDropdownRequestDTO1.setTechnologyId(TECHID);
        interviewerDropdownRequestDTO1.setInterviewTypeId(INTERVIEWTYPEID);
        interviewerDropdownRequestDTO1.setFromTime(DROPDOWNFROMTIME);
        interviewerDropdownRequestDTO1.setToTime(DROPDOWNTOTIME);
        interviewerDropdownRequestDTO1.setBuId(BUID);
        ResponseDto responseFinal1 = interviewerController.fetchInterviewerDropdown(interviewerDropdownRequestDTO1);
        List<InterviewerDropdownDTO> interviewerListFinal1 = (List<InterviewerDropdownDTO>) responseFinal1.getResponse()
                .get(0);

        Assert.assertEquals(interviewerListExpected1.size(), interviewerListFinal1.size());
    }
    
 
	
	@Test
	public void showHideReportTest(){
		Boolean showHideExpected = true;
		
		EmailDto emailDto = new EmailDto();
		emailDto.setEmail(RECRUITEREMAIL);
		
		boolean showHideFinal = interviewerController.showHideReport(emailDto);
		Assert.assertNotEquals(showHideExpected, showHideFinal);
	}
	
	@Test
	public void showHideReportTest1(){
		Boolean showHideExpected = true;
		
		EmailDto emailDto = new EmailDto();
		emailDto.setEmail(null);
		
		boolean showHideFinal = interviewerController.showHideReport(emailDto);
		Assert.assertNotEquals(showHideExpected, showHideFinal);
	}
	


    @Test
    @Transactional
    @Rollback(true)
    public void deleteInterviewSlotTest() {
        Boolean deleteSuccessExpected = true;

        ResponseDto responseFinal = interviewerController.deleteInterviewSlot(DELETECALENDARID);
        Boolean deleteSuccessActual = (Boolean) responseFinal.getResponse().get(0);

        Assert.assertEquals(deleteSuccessExpected, deleteSuccessActual);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void saveFeedbackTest() {
        Boolean feedbackSuccessExpected = true;

        FeedbackDTO feedbackDto = new FeedbackDTO();
        feedbackDto.setCalendarId(FEEDBACK_CALID);
        feedbackDto.setFeedbackStatusId(FEEDBACK_STATUSID);
        feedbackDto.setFeedbackComments(FEEDBACK_COMMENT);
        feedbackDto.setParticipationId(PARTICIPATIONTYPEID);
        
        ResponseDto responseFinal = interviewerController.saveFeedback(feedbackDto);

        Boolean feedbackSuccessActual = (Boolean) responseFinal.getResponse().get(0);
        
        Assert.assertEquals(feedbackSuccessExpected, feedbackSuccessActual);
    }
  
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest() {
    	commonReportTest(TECHID1, TECHID, DAYSFILTER);  
	    
    }

	@Test
    @Transactional
    @Rollback(true)
    public void generateReportTest1() {
		commonReportTest(TECHID1, TECHID, DAYSFILTER);        
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest2() {
    	commonReportTest(TECHID1, TECHID, DAYSFILTER);	    
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest3() {
    	commonReportTest(TECHID1, TECHID, DAYSFILTER);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest4() {
    	commonReportTest(TECHID1, TECHID, DAYSFILTER);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest5() {
    	commonReportTest(TECHID1, TECHID, DAYSFILTER);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest6() {
    	commonReportTest(TECHID1, TECHID, DAYSFILTER);
    }
    @Test
    @Transactional
    @Rollback(true)
    public void generateReportTest7() {
    	commonReportTest(TECHID, TECHID1, DAYSFILTER);
    }
    
 private void commonReportTest(long tech1, long tech2, long daysFilter) {
		
    	ReportDTO reportDTO = new ReportDTO();
    	ResponseDto responseExpected = new ResponseDto();
    	reportDTO.setStatus(STATUS);
    	List<ReportDTO> smarthireReport = new ArrayList<>();
    	smarthireReport.add(reportDTO);
    	
    	List<Object> response = new ArrayList<>();
	    response.add(smarthireReport);
	    responseExpected.setResponse(response);
	    
	    List<Long> techList = new ArrayList<>();
	    techList.add(tech1);
	    techList.add(tech2);
	 
	    
	    SmarthireReportDTO smartHireReportDto = new SmarthireReportDTO();
	    
	    smartHireReportDto.setDaysFilter(daysFilter);
	    smartHireReportDto.setFromTime(FROM1);
	    smartHireReportDto.setInterviewTypeId(INTERVIEWTYPEID);
	    smartHireReportDto.setRecruiterEmailId(RECRUITEREMAIL);
	    smartHireReportDto.setTechId(techList);
	    smartHireReportDto.setToTime(TO1);
	    
	    ResponseDto responseFinal = interviewerController.generateReport(smartHireReportDto);
	    Assert.assertEquals(responseExpected.getResponse().size(), responseFinal.getResponse().size());
		
	}
 
    @Test
    @Transactional
    @Rollback(true)
    public void setRescheduledRequestedTest(){
    	Boolean isRescheduledExpected = true;
   
    	RescheduleRequestDto rescheduleRequestDto = new RescheduleRequestDto();
    	rescheduleRequestDto.setCalendarId(44227);
    	ResponseDto responseFinal = interviewerController.setRescheduledRequested(rescheduleRequestDto);
    	
    	Boolean isRescheduledActual = (Boolean) responseFinal.getResponse().get(0);
    	Assert.assertEquals(isRescheduledExpected, isRescheduledActual);
    		
    }
    
   

}
