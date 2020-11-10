package ssv.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SquadDto {
	private String name;
	private String pos;
	private String age;
	private String height;
	private String weight;
	private String nation;
	private int played;
	private int ga;
	private int goal;
	private int save;
	private int assists;
	private int fc;
	private int yc;
	private int rc;
}
