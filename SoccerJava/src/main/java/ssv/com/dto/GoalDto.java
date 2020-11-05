package ssv.com.dto;

import lombok.Getter;
import lombok.Setter;
import ssv.com.entity.Profile;

@Setter
@Getter
public class GoalDto {
	private String time;
	private Profile profile;
	private int idSchedule;
	private int team;
}
