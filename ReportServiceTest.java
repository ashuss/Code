package com.capgemini.smarthire.service.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.capgemini.smarthire.Application;
import com.capgemini.smarthire.controller.GenerateReportController;
import com.capgemini.smarthire.dtos.GenerateReportDTO;
import com.capgemini.smarthire.services.ReportService;
import com.jcabi.log.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ReportServiceTest {
	
	@Autowired
	ReportService reportService;
	@Autowired
	GenerateReportController reportController;

	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	
	private static final String FROMTIME = "1527046200000";
	private static final String TOTIME = "1527049800000";
	private static final String TECHID2 = "[43069]";
	private static final String TECHID1 = "1";
	private static final long INTERVIEWTYPEID = 1L;
	private static final long DOWNLOADID1 = 1L;
	private static final long DOWNLOADID2 = 2L;
	private static final long DOWNLOADID3 = 3L;
	private static final long DOWNLOADID4 = 4L;
	private static final long FILTERID1 = 1L;
	private static final long FILTERID2 = 2L;
	private static final String EXCEPTION_MESSAGE = "Exception is";
	

	
	@Test
	public void buildExcelDocumentTest(){
		commonbuildExcelTest(FROMTIME,TOTIME,TECHID1,INTERVIEWTYPEID,DOWNLOADID1,FILTERID1, request, response);
		
	  }
	
	@Test
	  public void buildExcelDocumentTest1(){
		commonbuildExcelTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID,DOWNLOADID1,FILTERID2, request, response);
	  }
	 
	 @Test
	  public void buildExcelDocumentTest2(){
		 commonbuildExcelTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID,DOWNLOADID1,FILTERID1, request, response);
		 
	  }
	 
	 @Test
	  public void buildExcelDocumentTest3(){
		 commonbuildExcelTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID,DOWNLOADID2,FILTERID2, request, response);
		
	 }
	 
	 @Test
	  public void buildExcelDocumentTest4(){
		 commonbuildExcelTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID,DOWNLOADID2,FILTERID1, request, response);
		
	  }

	  
	  @Test
	  public void buildExcelDocumentTest5(){
		  commonbuildExcelTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID,DOWNLOADID4,FILTERID2, request, response);
	  }
	  @Test
	  public void buildExcelDocumentTest6(){
		  commonbuildExcelTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID,DOWNLOADID4,FILTERID1, request, response);
	  }
	  @Test
	  public void buildExcelDocumentTest7(){
		  commonbuildExcelTest(FROMTIME,TOTIME,TECHID1,INTERVIEWTYPEID,DOWNLOADID4,FILTERID1, request, response);
	  }
	  
	  private void commonbuildExcelTest(String fromtime2, String totime2, String techid12, long interviewtypeid2,
				long downloadid12, long filterid12, HttpServletRequest request2, HttpServletResponse response2) {
			 GenerateReportDTO generateReportDTO = new GenerateReportDTO();
			  generateReportDTO.setFromTime(fromtime2);
			  generateReportDTO.setToTime(totime2);
			  generateReportDTO.setTechId(techid12);
			  generateReportDTO.setInterviewTypeId(interviewtypeid2);
			  generateReportDTO.setDownloadId(downloadid12);
			  generateReportDTO.setFilterDaysId(filterid12);
			  
			  try {
				reportService.buildExcelDocument(generateReportDTO, request2, response2);
			} catch (Exception e) {
				Logger.info(e, EXCEPTION_MESSAGE);
			}  
	  }
}
