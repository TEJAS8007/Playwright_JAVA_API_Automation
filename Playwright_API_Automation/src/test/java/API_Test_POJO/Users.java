package API_Test_POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class Users {

	private String id;
	private String name;
	private String email;
	private String gender;
	private String status;
	
	
}
