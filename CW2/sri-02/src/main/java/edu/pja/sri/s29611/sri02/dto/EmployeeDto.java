package edu.pja.sri.s29611.sri02.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    private String firstName;
    private String LastName;
    private LocalDate birthdate;
    private String job;
}
