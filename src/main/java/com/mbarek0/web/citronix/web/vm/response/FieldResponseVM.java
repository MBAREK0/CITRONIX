package com.mbarek0.web.citronix.web.vm.response;

import com.mbarek0.web.citronix.domain.Farm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldResponseVM {
    private UUID farm;
    private Double area;
}
