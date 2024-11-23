package org.mbarek0.web.citronix.web.VM.Tree;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeCreationVm {
    @NotNull(message = "Planting date is required.")
    private LocalDate plantingDate;

    @NotNull(message = "Field ID is required.")
    private UUID fieldId;
}
