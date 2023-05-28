package hotel.management.v1.member.dto;

import hotel.management.v1.member.entity.Level;
import hotel.management.v1.member.entity.Member;
import hotel.management.v1.member.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class MemberDto {
	
	@Data
	public static class Join {
		private String username;
		private String name;
		private String tel;
		private String password;
		private String email;
		private String personalId;
		
		public Member toEntity(String encodedPassword) {
			return Member.builder().username(username).name(name).tel(tel)
					.password(encodedPassword).email(email).personalId(personalId)
					.userLevel(Level.BRONZE).role(Role.USER).build();
		}
	}
	
	@Getter
	@AllArgsConstructor
	@ToString
	public static class Read {
	    private String name;
	    private String username;
	    private String email1;
	    private String email2;
	    private String tel;
	    private String userLevel;
	}
}
