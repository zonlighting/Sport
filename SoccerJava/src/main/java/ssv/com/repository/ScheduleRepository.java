package ssv.com.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.mapper.ScheduleMapper;

@Repository
public class ScheduleRepository {
	@Autowired ScheduleMapper scheduleMapper;
}
