package com.example.baseballscoresheet.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "lineup_team_player")
public class LineupTeamPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TeamPlayerEntity teamPlayer;

    @ManyToOne
    @JoinColumn(name = "lineup_id", nullable = false)
    private LineupEntity lineup;

    // TODO: Question to Darleen - why was this field OneToOne?
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private PositionEntity position;


    @Column(name = "jersey_nr")
    private Integer jerseyNr;

    @OneToMany(mappedBy = "lineupPlayer")
    private Set<LineupPlayerStateEntity> lineupPlayerStateSet;

}
