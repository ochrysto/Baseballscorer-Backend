package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "position")
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    // TODO: Question to Darleen - why was this field OneToOne?
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<LineupTeamPlayerEntity> lineupTeamPlayers;

}
