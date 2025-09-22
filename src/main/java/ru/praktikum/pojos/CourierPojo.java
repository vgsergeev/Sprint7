package ru.praktikum.pojos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)

public class CourierPojo {

    private String login;
    private String password;
    private String firstName;
    private Integer id;

}