package org.mbarek0.web.citronix.web.VM.Farm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FarmVM {
    private String name;
    private String location;
    private double area;
    private LocalDate creationDate;
}
