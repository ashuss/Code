package com.capgemini.smarthire.controller.test;

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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ReportControllerTest {

	@Autowired
	GenerateReportController reportController;
	
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	
	private static final String FROMTIME = "1527046200000";
	private static final String TOTIME = "1527049800000";
	private static final String TECHID1 = "[43069,1]";
	private static final String TECHID2 = "1";
	private static final long INTERVIEWTYPEID1 = 1L;
	private static final long INTERVIEWTYPEID2 = 2L;
	private static final long INTERVIEWTYPEID3 = 0L;
	private static final long DOWNLOADID1 = 1L;
	private static final long DOWNLOADID2 = 2L;
	private static final long DOWNLOADID3 = 3L;
	private static final long DOWNLOADID4 = 4L;
	private static final long FILTERID1 = 1L;
	private static final long FILTERID2 = 2L;
			
	  @Test
	  public void buildExcelDocumentTest(){
		  commonDocumentTest(FROMTIME,TOTIME,TECHID2,INTERVIEWTYPEID1,DOWNLOADID1,FILTERID1, request, response);
	  }
	  
	  @Test
	  public void buildExcelDocumentTest1(){
		commonDocumentTest(FROMTIME,TOTIME,TECHID1,INTERVIEWTYPEID2,DOWNLOADID2,FILTERID2, request, response);
	  }
	  
	  @Test
	  public void buildExcelDocumentTest2(){
		  commonDocumentTest(FROMTIME,TOTIME,TECHID1,INTERVIEWTYPEID1,DOWNLOADID3,FILTERID1, request, response);
	  }
	  
	  @Test
	  public void buildExcelDocumentTest3(){
		  commonDocumentTest(FROMTIME,TOTIME,TECHID1,INTERVIEWTYPEID1,DOWNLOADID4,FILTERID2, request, response);
	  }
	  @Test
	  public void buildExcelDocumentTest4(){
		  commonDocumentTest(FROMTIME,TOTIME,TECHID1,INTERVIEWTYPEID3,DOWNLOADID4,FILTERID2, request, response);
	  }
	  
	  private void commonDocumentTest(String fromtime2, String totime2, String techid22, long interviewtypeid12,
				long downloadid12, long filterid12, HttpServletRequest request2, HttpServletResponse response2) {
			  
			  GenerateReportDTO generateReportDTO = new GenerateReportDTO();
			  generateReportDTO.setFromTime(fromtime2);
			  generateReportDTO.setToTime(totime2);
			  generateReportDTO.setTechId(techid22);
			  generateReportDTO.setInterviewTypeId(interviewtypeid12);
			  generateReportDTO.setDownloadId(downloadid12);
			  generateReportDTO.setFilterDaysId(filterid12);
			  
			  reportController.buildExcelDocument(generateReportDTO, request2, response2);  
			
		}
}
