package ssv.com.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Goal {
	private int idGoal;
	private int idSchedule;
	private int idMember;
	private String time;
	private int team;
	
}
