package ssv.com.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthYearDto {
	private String monthStart;
	private List<TeamScheduleDto> teamSchedules;
}
