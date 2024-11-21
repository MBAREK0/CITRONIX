package org.mbarek0.web.citronix.web.VM.Sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleCreationVm {

    @NotNull(message = "date is required")
    private LocalDate date;

    @NotNull(message = "unitPrice is required")
    @Positive(message = "unitPrice should be positive")
    private double unitPrice;

    @NotBlank(message = "client name is required")
    private String client;
}
