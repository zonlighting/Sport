package ssv.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankDto implements Comparable<RankDto>{
	private String teamName;
	private int win;
	private int lose;
	private int tie;
	private int point;
	@Override
	public int compareTo(RankDto o) {
		if(this.point > o.point) {
			return 1;
		}
		if(this.point < o.point) {
			return -1;
		}
		return 0;
	}
}


