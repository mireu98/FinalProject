package hotel.management.v1.member.entity;

import lombok.Getter;

@Getter
public enum Role {
	USER("일반회원"),ADMIN("관리자");
	private String korean;

	Role(String string) {
		this.korean=string;
	}
	
	
}
