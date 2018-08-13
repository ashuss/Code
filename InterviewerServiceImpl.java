package com.capgemini.smarthire.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.smarthire.dtos.CheckAvailabilityDTO;
import com.capgemini.smarthire.dtos.EmailDto;
import com.capgemini.smarthire.dtos.FeedbackDTO;
import com.capgemini.smarthire.dtos.FeedbackDetailsDTO;
import com.capgemini.smarthire.dtos.InterviewerCalendarSavedSlotDTO;
import com.capgemini.smarthire.dtos.InterviewerCalenderDetailsDto;

import com.capgemini.smarthire.dtos.InterviewerDropdownDTO;
import com.capgemini.smarthire.dtos.InterviewerDropdownRequestDTO;
import com.capgemini.smarthire.dtos.InterviewerSaveSlotDto;

import com.capgemini.smarthire.dtos.RecruiterDetailsDTO;
import com.capgemini.smarthire.dtos.ReportDTO;
import com.capgemini.smarthire.dtos.RescheduleRequestDto;
import com.capgemini.smarthire.dtos.SendEmailDTO;
import com.capgemini.smarthire.dtos.SlotDetailsDTO;

import com.capgemini.smarthire.dtos.SlotsDTO;
import com.capgemini.smarthire.dtos.SmarthireReportDTO;

import com.capgemini.smarthire.exception.SmarthireException;
import com.capgemini.smarthire.repositories.EmployeeMasterRepository;
import com.capgemini.smarthire.repositories.EmployeeRoleRepository;
import com.capgemini.smarthire.repositories.EmployeeTechnologyRepository;
import com.capgemini.smarthire.repositories.FeedbackStatusRepository;
import com.capgemini.smarthire.repositories.InterviewGradeTypeDetailsRepository;
import com.capgemini.smarthire.repositories.InterviewerRepository;
import com.capgemini.smarthire.repositories.ParticipationTypeRepository;
import com.capgemini.smarthire.repositories.RoleMasterRepository;
import com.capgemini.smarthire.repositories.TechnologyMasterRepository;
import com.capgemini.smarthire.reusable.transaction.entity.EmployeeMasterEntity;

import com.capgemini.smarthire.reusable.transaction.entity.FeedbackStatusEntity;

import com.capgemini.smarthire.reusable.transaction.entity.InterviewerCalendarDetailsEntity;
import com.capgemini.smarthire.reusable.transaction.entity.ParticipationTypeEntity;
import com.capgemini.smarthire.reusable.transaction.entity.RecruiterCalendarDetailsEntity;
import com.capgemini.smarthire.reusable.transaction.entity.RoleMasterEntity;
import com.capgemini.smarthire.util.Utils;

@Service
public class InterviewerServiceImpl implements InterviewerService {

    private static final String INTERVIEWERROLE = "Interviewer";
    private static final int HOUR = 5;
    private static final int MINUTES = 30;
	public static final String RECRUITERROLE = "Recruiter";


	@Autowired
	InterviewerRepository interviewerRepository;

	@Autowired
	ParticipationTypeRepository participationTypeRepository;

    @Autowired
    EntityDtoMapper mapper;

    @Autowired
    EmployeeMasterRepository employeeMasterRepository;

    @Autowired
    EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    RoleMasterRepository roleMasterRepository;

    @Autowired
    FeedbackStatusRepository feedbackStatusRepository;

	@Autowired
	EmployeeTechnologyRepository employeeTechnologyRepository;

	@Autowired
	InterviewGradeTypeDetailsRepository interviewGradeTypeRepository;

	@Autowired
	TechnologyMasterRepository technologyMasterRepository;

	private static final Date currentDate = new Date();

	@Override
	public List<InterviewerCalenderDetailsDto> getInterviewersSlots(CheckAvailabilityDTO checkAvailabilityDTO)
			throws SmarthireException {
		List<InterviewerCalenderDetailsDto> interviewerCalenderDetailsDtos = null;
		EmployeeMasterEntity employeeMasterEntity = employeeMasterRepository
				.getEmployeeByEmail(checkAvailabilityDTO.getEmailId());
		List<RoleMasterEntity> roleMasterList = roleMasterRepository.findRoleByEmpId(employeeMasterEntity.getEmpId());
		RoleMasterEntity roleMaster = roleMasterRepository.findByRoleName(RECRUITERROLE);
		for(RoleMasterEntity roler : roleMasterList){			
			if (roler.getRoleId() == roleMaster.getRoleId() ) {
		
			List<InterviewerCalendarDetailsEntity> interviewerCalendarDetailsEntities = interviewerRepository
					.findInterviewersSlots(checkAvailabilityDTO.getInterviewerTypeId(),
							checkAvailabilityDTO.getTechnologyId(), checkAvailabilityDTO.getBuId(), new Date());

			interviewerCalenderDetailsDtos = new ArrayList<>();
			
			for (InterviewerCalendarDetailsEntity interviewerCalendarDetailsEntity : interviewerCalendarDetailsEntities) {
				InterviewerCalenderDetailsDto interviewerCalenderDetailsDto = mapper
						.interviewerCalendarEntityToDto(interviewerCalendarDetailsEntity);
				interviewerCalenderDetailsDtos.add(interviewerCalenderDetailsDto);
			}
			
		} else {
			throw new SmarthireException("YOU ARE NOT AUTHORIZED TO VIEW FREE INTERVIEW SLOTS");
		}}
		return interviewerCalenderDetailsDtos;
	}

	@Override
	public List<InterviewerCalenderDetailsDto> getAllInterviewerSlots(EmailDto emailDto) throws SmarthireException {
		EmployeeMasterEntity employeeMasterEntity = employeeMasterRepository.getEmployeeByEmail(emailDto.getEmail());
		
		List<InterviewerCalenderDetailsDto> interviewerCalenderDetailsDtos = null;
		
		List<RoleMasterEntity> roleMasterList = roleMasterRepository.findRoleByEmpId(employeeMasterEntity.getEmpId());
		RoleMasterEntity roleMaster = roleMasterRepository.findByRoleName(INTERVIEWERROLE);
		
		for(RoleMasterEntity roleN : roleMasterList){
			if(roleN.getRoleId() == roleMaster.getRoleId()){
			List<InterviewerCalendarDetailsEntity> interviewerCalendarDetailsEntities = interviewerRepository
					.findSlotsByEmail(emailDto.getEmail());
			interviewerCalenderDetailsDtos = new ArrayList<>();
			
			for (InterviewerCalendarDetailsEntity interviewerCalendarDetailsEntity : interviewerCalendarDetailsEntities) {
				InterviewerCalenderDetailsDto interviewerCalenderDetailsDto = mapper
						.interviewerCalendarEntityToDto(interviewerCalendarDetailsEntity);
				interviewerCalenderDetailsDtos.add(interviewerCalenderDetailsDto);
			}
		
			
		
		} else {
			throw new SmarthireException("YOU ARE NOT AUTHORIZED TO VIEW INTERVIEW SLOTS");
		}
		}
		return interviewerCalenderDetailsDtos;
	}

    private InterviewerCalendarDetailsEntity fetchInterviewerCalendarEntity(
            InterviewerSaveSlotDto interviewerSaveSlotDto, EmployeeMasterEntity employeeMasterEntity)
            throws SmarthireException {
        InterviewerCalendarDetailsEntity interviewerCalendarDetailsEntity = null;
        if (interviewerSaveSlotDto.getCalendarId() != 0) {
            interviewerCalendarDetailsEntity = interviewerRepository.findOne(interviewerSaveSlotDto.getCalendarId());
            if (interviewerCalendarDetailsEntity != null) {
                if (interviewerCalendarDetailsEntity.isBooked()) {
                    throw new SmarthireException("SLOT ALREADY BOOKED BY RECRUITER");
                }
                interviewerCalendarDetailsEntity.setInterviewerCalendarId(interviewerSaveSlotDto.getCalendarId());
                interviewerCalendarDetailsEntity.setUpdatedBy(interviewerSaveSlotDto.getEmail());
                interviewerCalendarDetailsEntity.setUpdatedDate(new Date());
            } else {
                throw new SmarthireException("INTERVIEW SLOT DOES NOT EXIST");
            }
        } else {
            interviewerCalendarDetailsEntity = new InterviewerCalendarDetailsEntity();
            interviewerCalendarDetailsEntity.setCreatedBy(interviewerSaveSlotDto.getEmail());
            interviewerCalendarDetailsEntity.setCreatedDate(new Date());
        }
		ParticipationTypeEntity participationTypeEntity = participationTypeRepository
				.findOne(interviewerSaveSlotDto.getParticipationTypeId());
        interviewerCalendarDetailsEntity.setEmployeeMasterEntity(employeeMasterEntity);
        interviewerCalendarDetailsEntity.setBooked(false);
        interviewerCalendarDetailsEntity.setFromTime(interviewerSaveSlotDto.getFromTime());
        interviewerCalendarDetailsEntity.setToTime(interviewerSaveSlotDto.getToTime());
        interviewerCalendarDetailsEntity.setActiveFlag(true);
		interviewerCalendarDetailsEntity.setParticipationTypeEntity(participationTypeEntity);
        return interviewerRepository.save(interviewerCalendarDetailsEntity);
    }

	@Override
	public InterviewerCalendarSavedSlotDTO saveFreeSlot(InterviewerSaveSlotDto interviewerSaveSlotDto)
			throws SmarthireException {
		EmployeeMasterEntity employeeMasterEntity = employeeMasterRepository
				.getEmployeeByEmail(interviewerSaveSlotDto.getEmail());
		List<RoleMasterEntity> roleMasterList = roleMasterRepository.findRoleByEmpId(employeeMasterEntity.getEmpId());
		RoleMasterEntity roleMaster = roleMasterRepository.findByRoleName(INTERVIEWERROLE);
		InterviewerCalendarDetailsEntity interviewerCalendarDetailsEntity = null;
		
		
		for(RoleMasterEntity roleN : roleMasterList){
			
			if(roleN.getRoleId() == roleMaster.getRoleId()){ 
				 
				
			if (interviewerRepository.findCalenderBySlotAndIdAndParticipation(interviewerSaveSlotDto.getFromTime(),
					interviewerSaveSlotDto.getToTime(), employeeMasterEntity.getEmpId(),
					interviewerSaveSlotDto.getParticipationTypeId()) == null) {
				interviewerCalendarDetailsEntity = fetchInterviewerCalendarEntity(interviewerSaveSlotDto,
						employeeMasterEntity);
				
				
			} else {
				String fromdate = Utils.dateToString(interviewerSaveSlotDto.getFromTime());
				String todate = Utils.dateToString(interviewerSaveSlotDto.getToTime());
				DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				DateFormat timeFormat = new SimpleDateFormat("KK:mm a");
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    Calendar from = Calendar.getInstance();
                    from.setTime(inputFormat.parse(fromdate));
                    from.add(Calendar.HOUR_OF_DAY, HOUR);
                    from.add(Calendar.MINUTE, MINUTES);

                    Calendar to = Calendar.getInstance();
                    to.setTime(inputFormat.parse(todate));
                    to.add(Calendar.HOUR_OF_DAY, HOUR);
                    to.add(Calendar.MINUTE, MINUTES);

                    String exception = "SLOT ALREADY BOOKED FROM " + timeFormat.format(from.getTime()) + " TO "
                            + timeFormat.format(to.getTime()) + " ON " + dateFormat.format(inputFormat.parse(todate));
                    throw new SmarthireException(exception);
                } catch (ParseException e) {
                    throw new SmarthireException(e.getMessage());
                }
            }
        } else {
            throw new SmarthireException("YOU ARE NOT AUTHORIZED TO ADD INTERVIEW SLOTS");
        }
		}
		return mapper.interviewerCalendarDetailsEntityToDTO(interviewerCalendarDetailsEntity);
    }

	@Override
	public List<InterviewerDropdownDTO> fetchInterviewerDropdown(
			InterviewerDropdownRequestDTO interviewerDropdownRequestDTO) throws SmarthireException {
		
		List<EmployeeMasterEntity> employeeMasterList = interviewerRepository.fetchInterviewerDropdown(
				interviewerDropdownRequestDTO.getFromTime(), interviewerDropdownRequestDTO.getToTime(),
				interviewerDropdownRequestDTO.getTechnologyId(), interviewerDropdownRequestDTO.getInterviewTypeId(),
				interviewerDropdownRequestDTO.getBuId());

		if (employeeMasterList != null) {
			List<InterviewerDropdownDTO> interviewerDropdownList = new ArrayList<>();

			for (EmployeeMasterEntity interviewer : employeeMasterList) {

				InterviewerCalendarDetailsEntity interviewerCalendar = interviewerRepository.findParticipationType(
						interviewer.getEmpId(), interviewerDropdownRequestDTO.getFromTime(),
						interviewerDropdownRequestDTO.getToTime());
				InterviewerDropdownDTO interviewerDto = mapper.interviewerEntityToDTO(interviewer);
				
				if(interviewerCalendar.getParticipationTypeEntity() != null)
				interviewerDto
						.setParticipationType(interviewerCalendar.getParticipationTypeEntity().getParticipationName());

				interviewerDropdownList.add(interviewerDto);
			}
			return interviewerDropdownList;
		} else {
			throw new SmarthireException("NO INTERVIEWER AVAILABLE");
		}

    }

	@Override
	public Boolean deleteInterviewSlot(long calenderId) throws SmarthireException {
		InterviewerCalendarDetailsEntity interviewerCalender = interviewerRepository.findOne(calenderId);
		if (interviewerCalender != null) {
			EmployeeMasterEntity employeeMasterEntity = interviewerCalender.getEmployeeMasterEntity();
			RoleMasterEntity roleMaster = roleMasterRepository.findByRoleName(INTERVIEWERROLE);
			List<RoleMasterEntity> roleMasterList = roleMasterRepository
					.findRoleByEmpId(employeeMasterEntity.getEmpId());
			if (roleMasterList.contains(roleMaster)) {
				interviewerRepository.delete(interviewerCalender);
				return true;
			} else {
				throw new SmarthireException("YOU ARE NOT AUTHORIZED TO DELETE INTERVIEW SLOTS");
			}

        } else {
            throw new SmarthireException("NO INTERVIEW SLOT FOUND");
        }
    }

	@Override
	public Boolean saveFeedback(FeedbackDTO feedbackDto) throws SmarthireException {

		InterviewerCalendarDetailsEntity interviewerCalender = interviewerRepository
				.findOne(feedbackDto.getCalendarId());
		
		
		if (interviewerCalender != null) {
			EmployeeMasterEntity employeeMasterEntity = interviewerCalender.getEmployeeMasterEntity();
			RoleMasterEntity roleMaster = roleMasterRepository.findByRoleName(INTERVIEWERROLE);
			List<RoleMasterEntity> roleMasterList = roleMasterRepository
					.findRoleByEmpId(employeeMasterEntity.getEmpId());

			if (roleMasterList.contains(roleMaster)) {
				interviewerCalender.setFeedbackComment(feedbackDto.getFeedbackComments());
				ParticipationTypeEntity participationTypeEntity = participationTypeRepository
						.findOne(feedbackDto.getParticipationId());
				interviewerCalender.setParticipationTypeEntity(participationTypeEntity);

				FeedbackStatusEntity feedbackStatusEntity = feedbackStatusRepository
						.findOne(feedbackDto.getFeedbackStatusId());
				if (feedbackStatusEntity != null) {
					interviewerCalender.setFeedbackStatusEntity1(feedbackStatusEntity);
					InterviewerCalendarDetailsEntity interviewCalendarNew = interviewerRepository
							.save(interviewerCalender);
					return (interviewCalendarNew != null) ? true : false;
				} else {
					throw new SmarthireException("NO FEEDBACK STATUS FOUND");
				}
			} else {
				throw new SmarthireException("YOU ARE NOT AUTHORIZED TO GIVE FEEDBACK COMMENTS");
			}
		} else {
			throw new SmarthireException("NO INTERVIEW SLOT FOUND");
		}
	}

	@Override
	public List<ReportDTO> generateReport(SmarthireReportDTO reportDto) throws SmarthireException {
		List<InterviewerCalendarDetailsEntity> interviewerCalendarDetailsEntitiesFinal = new ArrayList<>();
		List<InterviewerCalendarDetailsEntity> interviewerCalendarDetailsEntity = null;
		
		System.out.println("interviewer Email id"+reportDto.getInterviewerEmailId());
		if (reportDto.getInterviewerEmailId() != null) {
		
			interviewerCalendarDetailsEntity = interviewerRepository.fetchInterviewerReport(
					employeeMasterRepository.getEmployeeByEmail(reportDto.getInterviewerEmailId()).getEmpId(),
					reportDto.getFromTime(), reportDto.getToTime());
		}

		else {
		
			List<Long> idList = reportDto.getTechId();
			if (idList != null && !idList.isEmpty()) {
				
				for (Long techId : idList) {
					
					if (techId != null && reportDto.getInterviewTypeId() != 0) {
						
						interviewerCalendarDetailsEntity = interviewerRepository.fetchReportByTechIdAndTypeId(
								reportDto.getFromTime(), reportDto.getToTime(), techId, reportDto.getInterviewTypeId());
					
					} else if (techId != null) {
						
						interviewerCalendarDetailsEntity = interviewerRepository
								.fetchReportByTechId(reportDto.getFromTime(), reportDto.getToTime(), techId);
						
					}
					interviewerCalendarDetailsEntitiesFinal.addAll(interviewerCalendarDetailsEntity);
				}
			} else if (reportDto.getInterviewTypeId() != 0) {
				
				interviewerCalendarDetailsEntity = interviewerRepository.fetchReportByTypeId(reportDto.getFromTime(),
						reportDto.getToTime(), reportDto.getInterviewTypeId());
				interviewerCalendarDetailsEntitiesFinal = interviewerCalendarDetailsEntity;

			} else {
				
				interviewerCalendarDetailsEntity = interviewerRepository.getAvailibility(reportDto.getFromTime(),
						reportDto.getToTime());
				interviewerCalendarDetailsEntitiesFinal = interviewerCalendarDetailsEntity;
			}

		}
		
	
		List<ReportDTO> reportDTOList = new ArrayList<>();

		ReportDTO availableReport = new ReportDTO();
		availableReport.setStatus("AVAILABLE");
		ReportDTO bookedReport = new ReportDTO();
		bookedReport.setStatus("BOOKED");
		ReportDTO interviewedReport = new ReportDTO();
		interviewedReport.setStatus("INTERVIEWED");
		ReportDTO notInterviewedReport = new ReportDTO();
		notInterviewedReport.setStatus("NOT INTERVIEWED");
		long availableCount = 0;
		long bookedCount = 0;
		long interviewedCount = 0;
		long notInterviewedCount = 0;

		List<SlotDetailsDTO> availableSlotDetailsDTOs = null;
		List<SlotDetailsDTO> bookedSlotDetailsDTOs = null;
		List<SlotDetailsDTO> interviewedSlotDetailsDTOs = null;
		List<SlotDetailsDTO> notInterviewedSlotDetailsDTOs = null;

		List<SlotsDTO> availableSlotDtoList = new ArrayList<>();
		List<SlotsDTO> bookedSlotDtoList = new ArrayList<>();
		List<SlotsDTO> interviewedSlotDtoList = new ArrayList<>();
		List<SlotsDTO> notInterviewedSlotDtoList = new ArrayList<>();

		SlotsDTO availableSlotsDTO = null;
		SlotsDTO bookedSlotsDTO = null;
		SlotsDTO interviewedSlotsDTO = null;
		SlotsDTO notInterviewedSlotsDTO = null;

		long interviewerId = -1;
		long interviewerIdOld = -1;
		long availableInterviewerId = -1;
		long bookedInterviewerId = -1;
		long interviewedInterviewerId = -1;
		long notInterviewedInterviewerId = -1;
		System.out.println("sizeeeee "+interviewerCalendarDetailsEntitiesFinal.size());
		for (InterviewerCalendarDetailsEntity interviewerCalendar : interviewerCalendarDetailsEntitiesFinal) {
			
			
			if(reportDto.getDaysFilter() == 2){//show only week days
				if(interviewerCalendar.getFromTime().getDay() == 0 || interviewerCalendar.getFromTime().getDay() == 6) continue;

			}
			else if(reportDto.getDaysFilter() == 1){//show only weekends
				if(interviewerCalendar.getFromTime().getDay() == 1 || interviewerCalendar.getFromTime().getDay() == 2
						|| interviewerCalendar.getFromTime().getDay() == 3 || interviewerCalendar.getFromTime().getDay() == 4
						|| interviewerCalendar.getFromTime().getDay() == 5) continue;
			}
	
		
			
			
			if (interviewerCalendar.getEmployeeMasterEntity().getEmpId() != interviewerId) {
				System.out.println("first id"+interviewerIdOld);
				if (interviewerIdOld != -1
						&& interviewerIdOld != interviewerCalendar.getEmployeeMasterEntity().getEmpId()) {
					if (availableSlotsDTO != null) {
						availableSlotDtoList.add(availableSlotsDTO);
					}
					if (bookedSlotsDTO != null) {
						bookedSlotDtoList.add(bookedSlotsDTO);
					}
					if (interviewedSlotsDTO != null) {
						interviewedSlotDtoList.add(interviewedSlotsDTO);
					}
					if (notInterviewedSlotsDTO != null) {
						notInterviewedSlotDtoList.add(notInterviewedSlotsDTO);
					}
					availableSlotsDTO = null;
					bookedSlotsDTO = null;
					interviewedSlotsDTO = null;
					notInterviewedSlotsDTO = null;
					availableInterviewerId = -1;
					bookedInterviewerId = -1;
					interviewedInterviewerId = -1;
					notInterviewedInterviewerId = -1;
				}
				
				interviewerId = interviewerCalendar.getEmployeeMasterEntity().getEmpId();
				interviewerIdOld = interviewerId;
				System.out.println("second id" + interviewerIdOld);
			}
			
			if (!interviewerCalendar.isBooked()) {
				
				if (interviewerCalendar.getEmployeeMasterEntity().getEmpId() != availableInterviewerId) {
					availableSlotsDTO = new SlotsDTO();
					availableSlotsDTO.setEmailId(interviewerCalendar.getEmployeeMasterEntity().getEmailId());
					availableSlotsDTO.setInterviewerId(interviewerCalendar.getEmployeeMasterEntity().getEmpId());
					availableSlotsDTO.setInterviewerName(interviewerCalendar.getEmployeeMasterEntity().getEmpName());
					availableSlotsDTO.setInterviewerType(interviewGradeTypeRepository.findInterviewType(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeId()));
					availableSlotsDTO.setGrade(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeName());
					availableSlotsDTO.setLocation(interviewerCalendar.getEmployeeMasterEntity().getLocation());
					if(interviewerCalendar.getParticipationTypeEntity() != null)
						availableSlotsDTO.setParticipationType(
								interviewerCalendar.getParticipationTypeEntity().getParticipationName());
						else 
							availableSlotsDTO.setParticipationType("NA");

					availableSlotDetailsDTOs = new ArrayList<>();
					availableInterviewerId = interviewerCalendar.getEmployeeMasterEntity().getEmpId();
				}

				SlotDetailsDTO slotDetailsDTO = new SlotDetailsDTO();
				FeedbackDetailsDTO feedbackDTO = null;
				if (interviewerCalendar.getFeedbackStatusEntity1() != null) {
					feedbackDTO = new FeedbackDetailsDTO();
					feedbackDTO
							.setFeedbackComment(interviewerCalendar.getFeedbackStatusEntity1().getFeedbackStatusName());
					feedbackDTO.setFeedbackStatus(interviewerCalendar.getFeedbackComment());

				}
				RecruiterDetailsDTO recruiterDetailsDTO = null;
				if (interviewerCalendar.getRecruiterCalendarDetailsEntity() != null) {
					recruiterDetailsDTO = new RecruiterDetailsDTO();
					recruiterDetailsDTO.setId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpId());
					recruiterDetailsDTO.setName(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpName());
					recruiterDetailsDTO.setEmailId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmailId());
					slotDetailsDTO.setCandidateName(
							interviewerCalendar.getRecruiterCalendarDetailsEntity().getCandidateName());
					slotDetailsDTO.setTechnology(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getTechnologyMasterEntity().getTechnologyName());
				}
				slotDetailsDTO.setCreationDate(interviewerCalendar.getCreatedDate());
				slotDetailsDTO.setFromDate(interviewerCalendar.getFromTime());
				slotDetailsDTO.setToDate(interviewerCalendar.getToTime());
				slotDetailsDTO.setFeedbackDetails(feedbackDTO);
				slotDetailsDTO.setRecruiterDetails(recruiterDetailsDTO);
				slotDetailsDTO.setSlotStatus("AVAILABLE");
				slotDetailsDTO.setIsBooked("N");				
				availableSlotDetailsDTOs.add(slotDetailsDTO);
				availableSlotsDTO.setInterviewerCount(availableSlotDetailsDTOs.size());
				availableSlotsDTO.setSlotsDetails(availableSlotDetailsDTOs);
				availableCount++;

			}

			else if (interviewerCalendar.isBooked()
					&& interviewerCalendar.getRecruiterCalendarDetailsEntity().getIsInterviewerAssigned()
					&& interviewerCalendar.getToTime().after(currentDate)) {
				if (interviewerCalendar.getEmployeeMasterEntity().getEmpId() != bookedInterviewerId) {
					bookedSlotsDTO = new SlotsDTO();
					bookedSlotsDTO.setEmailId(interviewerCalendar.getEmployeeMasterEntity().getEmailId());
					bookedSlotsDTO.setInterviewerId(interviewerCalendar.getEmployeeMasterEntity().getEmpId());
					bookedSlotsDTO.setInterviewerName(interviewerCalendar.getEmployeeMasterEntity().getEmpName());
					bookedSlotsDTO.setInterviewerType(interviewGradeTypeRepository.findInterviewType(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeId()));
					bookedSlotsDTO.setGrade(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeName());
					bookedSlotsDTO.setLocation(interviewerCalendar.getEmployeeMasterEntity().getLocation());
					if(interviewerCalendar.getParticipationTypeEntity() != null)
						bookedSlotsDTO.setParticipationType(
								interviewerCalendar.getParticipationTypeEntity().getParticipationName());
						else 
							bookedSlotsDTO.setParticipationType("NA");
					bookedSlotDetailsDTOs = new ArrayList<>();
					bookedInterviewerId = interviewerCalendar.getEmployeeMasterEntity().getEmpId();
				}

				SlotDetailsDTO slotDetailsDTO = new SlotDetailsDTO();
				FeedbackDetailsDTO feedbackDTO = null;
				if (interviewerCalendar.getFeedbackStatusEntity1() != null) {
					feedbackDTO = new FeedbackDetailsDTO();
					feedbackDTO
							.setFeedbackComment(interviewerCalendar.getFeedbackStatusEntity1().getFeedbackStatusName());
					feedbackDTO.setFeedbackStatus(interviewerCalendar.getFeedbackComment());

				}
				RecruiterDetailsDTO recruiterDetailsDTO = null;
				if (interviewerCalendar.getRecruiterCalendarDetailsEntity() != null) {
					recruiterDetailsDTO = new RecruiterDetailsDTO();
					recruiterDetailsDTO.setId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpId());
					recruiterDetailsDTO.setName(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpName());
					recruiterDetailsDTO.setEmailId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmailId());
					slotDetailsDTO.setCandidateName(
							interviewerCalendar.getRecruiterCalendarDetailsEntity().getCandidateName());
					slotDetailsDTO.setTechnology(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getTechnologyMasterEntity().getTechnologyName());
				}
				slotDetailsDTO.setCreationDate(interviewerCalendar.getCreatedDate());
				slotDetailsDTO.setFromDate(interviewerCalendar.getFromTime());
				slotDetailsDTO.setToDate(interviewerCalendar.getToTime());
				slotDetailsDTO.setFeedbackDetails(feedbackDTO);
				slotDetailsDTO.setRecruiterDetails(recruiterDetailsDTO);
				slotDetailsDTO.setSlotStatus("BOOKED");
				slotDetailsDTO.setIsBooked("Y");
				bookedSlotDetailsDTOs.add(slotDetailsDTO);
				bookedSlotsDTO.setInterviewerCount(bookedSlotDetailsDTOs.size());
				bookedSlotsDTO.setSlotsDetails(bookedSlotDetailsDTOs);
				bookedCount++;
			} else if (interviewerCalendar.isBooked()
					&& interviewerCalendar.getRecruiterCalendarDetailsEntity().getIsInterviewerAssigned()
					&& interviewerCalendar.getFeedbackStatusEntity1() != null) {
				if (interviewerCalendar.getEmployeeMasterEntity().getEmpId() != interviewedInterviewerId) {
					
					interviewedSlotsDTO = new SlotsDTO();
					interviewedSlotsDTO.setEmailId(interviewerCalendar.getEmployeeMasterEntity().getEmailId());
					interviewedSlotsDTO.setInterviewerId(interviewerCalendar.getEmployeeMasterEntity().getEmpId());
					interviewedSlotsDTO.setInterviewerName(interviewerCalendar.getEmployeeMasterEntity().getEmpName());
					interviewedSlotsDTO.setInterviewerType(interviewGradeTypeRepository.findInterviewType(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeId()));
					interviewedSlotsDTO.setGrade(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeName());
					interviewedSlotsDTO.setLocation(interviewerCalendar.getEmployeeMasterEntity().getLocation());
					if(interviewerCalendar.getParticipationTypeEntity() != null)
						interviewedSlotsDTO.setParticipationType(
								interviewerCalendar.getParticipationTypeEntity().getParticipationName());
						else 
							interviewedSlotsDTO.setParticipationType("NA");
					interviewedSlotDetailsDTOs = new ArrayList<>();
					interviewedInterviewerId = interviewerCalendar.getEmployeeMasterEntity().getEmpId();
				}
				SlotDetailsDTO slotDetailsDTO = new SlotDetailsDTO();
				FeedbackDetailsDTO feedbackDTO = null;
				if (interviewerCalendar.getFeedbackStatusEntity1() != null) {
					feedbackDTO = new FeedbackDetailsDTO();
					feedbackDTO
							.setFeedbackComment(interviewerCalendar.getFeedbackStatusEntity1().getFeedbackStatusName());
					feedbackDTO.setFeedbackStatus(interviewerCalendar.getFeedbackComment());

				}
				RecruiterDetailsDTO recruiterDetailsDTO = null;
				if (interviewerCalendar.getRecruiterCalendarDetailsEntity() != null) {
					recruiterDetailsDTO = new RecruiterDetailsDTO();
					recruiterDetailsDTO.setId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpId());
					recruiterDetailsDTO.setName(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpName());
					recruiterDetailsDTO.setEmailId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmailId());
					slotDetailsDTO.setCandidateName(
							interviewerCalendar.getRecruiterCalendarDetailsEntity().getCandidateName());
					slotDetailsDTO.setTechnology(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getTechnologyMasterEntity().getTechnologyName());
				}
				slotDetailsDTO.setCreationDate(interviewerCalendar.getCreatedDate());
				slotDetailsDTO.setFromDate(interviewerCalendar.getFromTime());
				slotDetailsDTO.setToDate(interviewerCalendar.getToTime());
				slotDetailsDTO.setFeedbackDetails(feedbackDTO);
				slotDetailsDTO.setRecruiterDetails(recruiterDetailsDTO);
				slotDetailsDTO.setSlotStatus("INTERVIEWED");
				slotDetailsDTO.setIsBooked("Y");
				interviewedSlotDetailsDTOs.add(slotDetailsDTO);
				if(interviewedSlotsDTO == null)
				
				
				if(interviewedSlotDetailsDTOs == null)	
				interviewedSlotsDTO.setInterviewerCount(interviewedSlotDetailsDTOs.size());
				interviewedSlotsDTO.setSlotsDetails(interviewedSlotDetailsDTOs);
				interviewedCount++;
			} else if (interviewerCalendar.isBooked()
					&& interviewerCalendar.getRecruiterCalendarDetailsEntity().getIsInterviewerAssigned()
					&& interviewerCalendar.getToTime().before(currentDate)
					&& interviewerCalendar.getFeedbackStatusEntity1() == null) {
				if (interviewerCalendar.getEmployeeMasterEntity().getEmpId() != notInterviewedInterviewerId) {
					notInterviewedSlotsDTO = new SlotsDTO();
					notInterviewedSlotsDTO.setEmailId(interviewerCalendar.getEmployeeMasterEntity().getEmailId());
					notInterviewedSlotsDTO.setInterviewerId(interviewerCalendar.getEmployeeMasterEntity().getEmpId());
					notInterviewedSlotsDTO
							.setInterviewerName(interviewerCalendar.getEmployeeMasterEntity().getEmpName());
					notInterviewedSlotsDTO.setInterviewerType(interviewGradeTypeRepository.findInterviewType(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeId()));
					notInterviewedSlotsDTO.setGrade(
							interviewerCalendar.getEmployeeMasterEntity().getGradeMasterEntity1().getGradeName());
					notInterviewedSlotsDTO.setLocation(interviewerCalendar.getEmployeeMasterEntity().getLocation());
					if(interviewerCalendar.getParticipationTypeEntity() != null)
					notInterviewedSlotsDTO.setParticipationType(
							interviewerCalendar.getParticipationTypeEntity().getParticipationName());
					else 
						notInterviewedSlotsDTO.setParticipationType("NA");
					notInterviewedSlotDetailsDTOs = new ArrayList<>();
					notInterviewedInterviewerId = interviewerCalendar.getEmployeeMasterEntity().getEmpId();
				}
				SlotDetailsDTO slotDetailsDTO = new SlotDetailsDTO();
				FeedbackDetailsDTO feedbackDTO = null;
				if (interviewerCalendar.getFeedbackStatusEntity1() != null) {
					feedbackDTO = new FeedbackDetailsDTO();
					feedbackDTO
							.setFeedbackComment(interviewerCalendar.getFeedbackStatusEntity1().getFeedbackStatusName());
					feedbackDTO.setFeedbackStatus(interviewerCalendar.getFeedbackComment());

				}
				RecruiterDetailsDTO recruiterDetailsDTO = null;
				if (interviewerCalendar.getRecruiterCalendarDetailsEntity() != null) {
					recruiterDetailsDTO = new RecruiterDetailsDTO();
					recruiterDetailsDTO.setId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpId());
					recruiterDetailsDTO.setName(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmpName());
					recruiterDetailsDTO.setEmailId(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getEmployeeMasterEntity().getEmailId());
					slotDetailsDTO.setCandidateName(
							interviewerCalendar.getRecruiterCalendarDetailsEntity().getCandidateName());
					slotDetailsDTO.setTechnology(interviewerCalendar.getRecruiterCalendarDetailsEntity()
							.getTechnologyMasterEntity().getTechnologyName());
				}
				slotDetailsDTO.setCreationDate(interviewerCalendar.getCreatedDate());
				slotDetailsDTO.setFromDate(interviewerCalendar.getFromTime());
				slotDetailsDTO.setToDate(interviewerCalendar.getToTime());
				slotDetailsDTO.setFeedbackDetails(feedbackDTO);
				slotDetailsDTO.setRecruiterDetails(recruiterDetailsDTO);
				slotDetailsDTO.setSlotStatus("NOT INTERVIEWED");
				slotDetailsDTO.setIsBooked("Y");
				notInterviewedSlotDetailsDTOs.add(slotDetailsDTO);
				notInterviewedSlotsDTO.setInterviewerCount(notInterviewedSlotDetailsDTOs.size());
				notInterviewedSlotsDTO.setSlotsDetails(notInterviewedSlotDetailsDTOs);
				notInterviewedCount++;
			}
		
		}

		if (availableSlotsDTO != null) {
			availableSlotDtoList.add(availableSlotsDTO);
		}
		if (bookedSlotsDTO != null) {
			bookedSlotDtoList.add(bookedSlotsDTO);
		}
		if (interviewedSlotsDTO != null) {
			interviewedSlotDtoList.add(interviewedSlotsDTO);
		}
		if (notInterviewedSlotsDTO != null) {
			notInterviewedSlotDtoList.add(notInterviewedSlotsDTO);
		}

		availableReport.setStatusCount(availableCount);
		availableReport.setSlotsDTO(availableSlotDtoList);
		bookedReport.setStatusCount(bookedCount);
		bookedReport.setSlotsDTO(bookedSlotDtoList);
		interviewedReport.setStatusCount(interviewedCount);
		interviewedReport.setSlotsDTO(interviewedSlotDtoList);
		notInterviewedReport.setStatusCount(notInterviewedCount);
		notInterviewedReport.setSlotsDTO(notInterviewedSlotDtoList);
		reportDTOList.add(availableReport);
		reportDTOList.add(bookedReport);
		reportDTOList.add(interviewedReport);
		reportDTOList.add(notInterviewedReport);
		
		return reportDTOList;
	}

	@Override
	public Boolean setRescheduledRequested(RescheduleRequestDto rescheduleRequestDto) throws SmarthireException {
		InterviewerCalendarDetailsEntity interviewerCalender = interviewerRepository
				.findOne(rescheduleRequestDto.getCalendarId());
		if (interviewerCalender != null) {
			EmployeeMasterEntity employeeMasterEntity = interviewerCalender.getEmployeeMasterEntity();
			RoleMasterEntity roleMaster = roleMasterRepository.findByRoleName(INTERVIEWERROLE);
			List<RoleMasterEntity> roleMasterList = roleMasterRepository
					.findRoleByEmpId(employeeMasterEntity.getEmpId());
			if (roleMasterList.contains(roleMaster)) {
				InterviewerCalendarDetailsEntity interviewerCalendarDetailsEntity = interviewerRepository
						.findOne(rescheduleRequestDto.getCalendarId());
				RecruiterCalendarDetailsEntity recruiterCalendarDetailsEntity = interviewerCalendarDetailsEntity
						.getRecruiterCalendarDetailsEntity();
				SendEmailDTO sendEmailDTO = new SendEmailDTO();
				sendEmailDTO.setDomain("corp");
				sendEmailDTO.setPassword("bUNRPBpEMGtoca/R2cDVCFxUd5vUFfT+BdhVKehT9g8=");
				sendEmailDTO.setSubject("SmartHire Reschedule Slot Requested");
				sendEmailDTO.setSubscriptionKey("eO3cjl3SeOZFmumjZoSmQoVa5pCuIq8dfp19NQnqiUo=");
				sendEmailDTO.setUserId("gisoni");
				String msg = "";
				String msg1 = "";
				String fromdate = Utils.dateToString(interviewerCalendarDetailsEntity.getFromTime());
				String todate = Utils.dateToString(interviewerCalendarDetailsEntity.getToTime());
				DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				DateFormat timeFormat = new SimpleDateFormat("KK:mm a");
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

				try {
					Calendar from = Calendar.getInstance();
					from.setTime(inputFormat.parse(fromdate));
					from.add(Calendar.HOUR_OF_DAY, 0);
					from.add(Calendar.MINUTE, 0);

					Calendar to = Calendar.getInstance();
					to.setTime(inputFormat.parse(todate));
					to.add(Calendar.HOUR_OF_DAY, 0);
					to.add(Calendar.MINUTE, 0);
					msg = "Reschedule request raised successfully to "
							+ interviewerCalendarDetailsEntity.getRecruiterCalendarDetailsEntity()
									.getEmployeeMasterEntity().getEmpName()
							+ " for the slot booked on " + dateFormat.format(inputFormat.parse(todate)) + " from "
							+ timeFormat.format(from.getTime()) + " to " + timeFormat.format(to.getTime());
					msg1 = interviewerCalendarDetailsEntity.getEmployeeMasterEntity().getEmpName()
							+ " has raised a request to reschedule a slot booked on "
							+ dateFormat.format(inputFormat.parse(todate)) + " from "
							+ timeFormat.format(from.getTime()) + " to " + timeFormat.format(to.getTime());
				} catch (ParseException e) {
					throw new SmarthireException(e.getMessage());
				}

				sendEmailDTO.setBodyContent(msg);
				sendEmailDTO.setTo(interviewerCalendarDetailsEntity.getEmployeeMasterEntity().getEmailId());
				sendEmailDTO.setCc(recruiterCalendarDetailsEntity.getEmployeeMasterEntity().getEmailId());

				sendEmailDTO.setBodyContent(msg1);
				sendEmailDTO.setTo(recruiterCalendarDetailsEntity.getEmployeeMasterEntity().getEmailId());
				sendEmailDTO.setCc(interviewerCalendarDetailsEntity.getEmployeeMasterEntity().getEmailId());

				return true;
			} else {
				throw new SmarthireException("YOU ARE NOT AUTHORIZED TO DELETE INTERVIEW SLOTS");
			}

		} else {
			throw new SmarthireException("NO INTERVIEW SLOT FOUND");
		}
	}

	
	
	
	
	@Override
	public boolean showHideReport(String email) {
		EmployeeMasterEntity employeeMasterEntity = employeeMasterRepository.getEmployeeByEmail(email);
		if(employeeMasterEntity != null){
			System.out.println("i'm in first if loop"+employeeMasterEntity.getShowReports());
			if(employeeMasterEntity.getShowReports() == null) return false;
			else return employeeMasterEntity.getShowReports();
		}else{
			return false;
		}
	}

}
