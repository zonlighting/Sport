package ssv.com.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StreaksDto {
	private int winning;
	private int currentWinning;
	private int unbeaten;
	private int currentUnbeaten;
	private int losing;
	private int currentLosing;
}
