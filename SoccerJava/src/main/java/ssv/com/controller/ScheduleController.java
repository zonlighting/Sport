package ssv.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.service.ScheduleService;

@RestController
@RequestMapping("/api/v1/schedule/")
public class ScheduleController {
	@Autowired ScheduleService scheduleService;
	
}
