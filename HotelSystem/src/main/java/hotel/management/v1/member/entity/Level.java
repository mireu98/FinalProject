package hotel.management.v1.member.entity;

import lombok.Getter;

@Getter
public enum Level {
	BRONZE("BRONZE"),SILVER("SILVER"),GOLD("GOLD");
	private String korean;
	
	Level(String string) {
		this.korean=string;
	}
	
	public class updateLevel {
		private Integer bookCnt; 
		private Level userLevel;
		
		public void setBookCnt(Integer bookCnt) {
			this.bookCnt = bookCnt;
			if (bookCnt >= 10 && userLevel == Level.BRONZE) {
				userLevel = Level.SILVER;
			}
		}
	}
}

