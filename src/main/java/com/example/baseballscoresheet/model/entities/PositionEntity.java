package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "position")
public class PositionEntity {

    @Id
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "position")
    private LineupTeamPlayerEntity lineupTeamPlayer;
}
