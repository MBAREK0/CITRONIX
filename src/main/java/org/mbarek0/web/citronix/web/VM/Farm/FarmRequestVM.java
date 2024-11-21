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
public class FarmRequestVM {

    @NotBlank(message = "name is required.")
    private String name;

    @NotBlank(message = "location is required.")
    private String location;

    @Positive(message = "Total area must be positive.")
    private double area;

    @NotNull(message = "Creation date is required.")
    private LocalDate creationDate;
}
