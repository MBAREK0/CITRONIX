package org.mbarek0.web.citronix.web.VM.Sale;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleResponseVM {
    private UUID id;
    private LocalDate date;
    private double unitPrice;
    private String client;
    private double revenue;
    private double soldQuantity;
}
