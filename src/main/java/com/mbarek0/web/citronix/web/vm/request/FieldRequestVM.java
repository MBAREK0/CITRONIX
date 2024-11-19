package com.mbarek0.web.citronix.web.vm.request;

import com.mbarek0.web.citronix.domain.Farm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldRequestVM {
    @NotNull(message = "Field must be associated with a farm")
    @NotBlank(message = "Farm name is required")
    private Farm farm;

    @NotNull(message = "Area is required")
    private Double area;
}
