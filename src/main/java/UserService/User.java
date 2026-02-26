package UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class User {

    private int id;
    private String username;
    private String password;
    private HashSet<Integer> roles;
}