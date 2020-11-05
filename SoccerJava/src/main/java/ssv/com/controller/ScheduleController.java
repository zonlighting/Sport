package ssv.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.GoalDto;
import ssv.com.dto.ResponseQuery;
import ssv.com.entity.Schedule;
import ssv.com.form.ScheduleForm;
import ssv.com.service.ScheduleService;

@RestController
@RequestMapping("/api/v1/schedule/")
public class ScheduleController {
	@Autowired ScheduleService scheduleService;


	//Thêm trận đấu
	@PostMapping(value = "create")
	public ResponseQuery<?> create(@RequestBody Schedule schedule){
		return scheduleService.create(schedule);
	}
	//Hiển thị all
	@GetMapping(value="getAll")
	public ResponseQuery<?> getAll(){
		return ResponseQuery.success("Connect Success", scheduleService.getAll());
	}


	//Hiển thị tất cả các  trận đấu theo giải đấu
	@GetMapping(value = "getByTournament")
	public ResponseQuery<?> getByTournament(@RequestParam int idTournament){
		return ResponseQuery.success("Connect Success", scheduleService.getByTournament(idTournament));
	}
	//Hiển thị trận đấu theo id
	@GetMapping(value="getById")
	public ResponseQuery<?> getById(@RequestParam int idSchedule){
		return ResponseQuery.success("Connect Success", scheduleService.getById(idSchedule));
	}
	//Xóa trận đấu
	@PostMapping(value="deleteSchedule")
	public ResponseQuery<?> deleteSchedule(@RequestParam int idSchedule){
		if(scheduleService.getById(idSchedule).getStatus()==0) {
		scheduleService.Delete(idSchedule);
		return ResponseQuery.success("Delete Success",200);
		}
		return ResponseQuery.faild("Match is ongoing or ending",400);

	}
	//Sửa trận đấu
	@PostMapping(value="edit")
	public ResponseQuery<?> edit(@RequestBody Schedule schedule){
		return 	scheduleService.edit(schedule);
	}


	//autoUpdate Status
	@GetMapping(value="status")
	public void statusAuto() {
		scheduleService.statusAuto();
	}

	//Update Trận đấu
	@PostMapping(value="update")
	public ResponseQuery<?> update(@ModelAttribute ScheduleForm scheduleForm) {
		return scheduleService.update(scheduleForm);
	}
	//Update Goal
	@PostMapping(value="goal")
	public  ResponseQuery<?> goal(@RequestBody List<GoalDto> goals){
		return scheduleService.goal(goals);
	}
	//Trận đấu gần nhất
	@GetMapping(value="recentMatch")
	public ResponseQuery<?> recentMatch(){
		return scheduleService.recentMatch();
	}
	//Hiển thị các trận đáu theo trạng thai
	@GetMapping(value="getByStatus")
	public ResponseQuery<?> getByStatus(@RequestParam int status){
		return ResponseQuery.success("Connect",scheduleService.getByStatus(status));
	}

	//Hiển thị video giải kết thúc gần nhất 
	@GetMapping(value="lastVideo")
	public ResponseQuery<?> lastVideo(){
		
		return ResponseQuery.success("Connect",scheduleService.lastVideo());

	}
	//Kết quả các trận đấu gần nhất	
	@GetMapping(value="lastResults")
	public ResponseQuery<?> lastResults(){
		if(scheduleService.lastResults()==null) {
			return ResponseQuery.faild("Null", null);
		}
		return ResponseQuery.success("Connect", scheduleService.lastResults());
	}
	
	

}
