package ssv.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.repository.ScheduleRepository;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;
}
