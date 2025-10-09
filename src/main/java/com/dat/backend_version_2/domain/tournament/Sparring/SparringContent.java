package com.dat.backend_version_2.domain.tournament.Sparring;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SparringContent", schema = "tournament")
public class SparringContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSparringContent;

    private int weightClass;
}
