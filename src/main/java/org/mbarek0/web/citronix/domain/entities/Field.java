package org.mbarek0.web.citronix.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private double area;

    @ManyToOne
    private Farm farm;

    @JsonIgnore
    @OneToMany(mappedBy = "field")
    private List<Tree> trees;
}
