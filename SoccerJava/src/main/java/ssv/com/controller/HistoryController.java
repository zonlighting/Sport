package ssv.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ssv.com.dto.ResponseQuery;
import ssv.com.entity.History;
import ssv.com.service.HistoryService;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {
	@Autowired
	private HistoryService historyService;
	
	
	//Những thành viên trong team nào và giải nào
	@GetMapping(value="/historyMember")
	public ResponseQuery<?> historyMember(@RequestParam int idTour,@RequestParam int idTeam){
		List<History>  historyMember=historyService.historyMember(idTour,idTeam);
		if(historyMember==null) {
			return ResponseQuery.faild("Chưa tham gia giải nào",null );
		}
		
		return ResponseQuery.success("Connect", historyMember);
	}
	@GetMapping(value="/test")
	public ResponseQuery<?> test(@RequestParam int idTeam){
		return ResponseQuery.success("Connect", historyService.test(idTeam));
	}
}
