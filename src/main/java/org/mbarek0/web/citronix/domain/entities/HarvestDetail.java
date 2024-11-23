package org.mbarek0.web.citronix.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HarvestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private double quantity;

    @ManyToOne
    private Harvest harvest;
    @ManyToOne
    private Tree tree;
}
