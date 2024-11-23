package org.mbarek0.web.citronix.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeDetailsDTO {
    private LocalDate plantingDate;
    private int age;
    private double productivity;
    private String farmName;
    private String farmLocation;
    private UUID fieldId;
}
