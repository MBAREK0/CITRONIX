package org.mbarek0.web.citronix.web.VM.Harvest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestCreationVM {
    @NotNull(message = "Date is required.")
    private LocalDate date;
}
