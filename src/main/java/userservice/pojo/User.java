package userservice.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;

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