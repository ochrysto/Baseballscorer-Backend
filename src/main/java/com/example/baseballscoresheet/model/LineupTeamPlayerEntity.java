package com.example.baseballscoresheet.model;

import com.example.baseballscoresheet.enums.POSITION;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "lineup_team_player")
public class LineupTeamPlayerEntity {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TeamPlayerEntity teamPlayer;

    @ManyToOne
    @JoinColumn(name = "lineup_id", nullable = false)
    private LineupEntity lineup;

    private POSITION position;

    @Column(name = "jersey_nr")
    @Size(max = 99, message = "Tricot Number must be between 0 and 99")
    private Integer jerseyNr;

    /**
     *
     */
    @OneToMany(mappedBy = "lineupPlayer")
    private Set<GameStateEntity> gameStateSet;
}
