package org.mbarek0.web.citronix.web.VM.Field;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldCreationVM {

    @NotNull(message = "Field area is required")
    @Positive(message = "Field area must be positive.")
    private double area;
    @NotNull(message = "farm id is required")
    private UUID farmId;
}
