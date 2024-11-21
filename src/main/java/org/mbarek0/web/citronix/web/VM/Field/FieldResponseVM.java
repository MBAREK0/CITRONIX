package org.mbarek0.web.citronix.web.VM.Field;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldResponseVM {
    private UUID id ;
    private double area;
    private String farmName;
    private String farmLocation;
}
