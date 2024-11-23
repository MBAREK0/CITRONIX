package org.mbarek0.web.citronix.web.VM.Tree;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreeResponseVM {
    private UUID id;
    private LocalDate plantingDate;
    private int age;
    private double productivity;
}
