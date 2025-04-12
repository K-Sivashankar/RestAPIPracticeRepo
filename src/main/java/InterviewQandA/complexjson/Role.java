package InterviewQandA.complexjson;

import lombok.Data;
import java.util.List;

@Data
public class Role {
    private String name;
    private List<String> permissions;
}

