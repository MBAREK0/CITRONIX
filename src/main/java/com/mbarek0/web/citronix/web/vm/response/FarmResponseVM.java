package com.mbarek0.web.citronix.web.vm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FarmResponseVM {

    private UUID id;

    private String name;

    private String location;

    private Double area;

    private LocalDateTime creationDate;
}
