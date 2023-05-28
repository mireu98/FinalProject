//관리자 : 주성
package hotel.management.v1.member.entity;

import hotel.management.v1.member.dto.MemberDto;
import hotel.management.v1.member.dto.MemberDto.Read;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member {
	private String username;
	private String name;
	private String tel;
	private String password;
	private String email;
	private String personalId;
	private Integer loginFailCount;
	private Boolean disabled;
	@Builder.Default
	private Level userLevel = Level.BRONZE;
	private Role role;
	private Integer bookcnt;
	private Boolean black;
	private Boolean vip;
	
	public Read toReadDto() {
		String[] emails = this.email.split("@");
		String email1 = emails[0];
		String email2 = emails[1];
		return new MemberDto.Read(name, username, email1, email2, tel, userLevel.getKorean());
	}

}
	
	
