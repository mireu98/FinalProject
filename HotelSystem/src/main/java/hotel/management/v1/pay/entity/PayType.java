package hotel.management.v1.pay.entity;

import lombok.Getter;

/*
 카카오
 토스
 현장결제
 
  */
@Getter
public enum PayType {
	KAKAO ("카카오"),TOSS("토스"),YET("현장결제");
	private String paykr;
	PayType(String string) {
		this.paykr = string;
	}
	
}
