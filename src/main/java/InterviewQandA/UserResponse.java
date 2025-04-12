package InterviewQandA;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String name;
    private String email;
    private String status;
    private String role;

    // Generate getters & setters (or use Lombok's @Data)

}

