package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Die Klasse {@link TeamPlayerEntity} bildet ein TeamPlayer-Objekt mit seinen dazugehörigen Attributen ab.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_player")
public class TeamPlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @ManyToOne
    @JoinColumn(name = "player_passnumber")
    private PlayerEntity player;
}
