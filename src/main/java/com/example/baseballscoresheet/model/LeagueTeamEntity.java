package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

//TODO bisschen mehr Infos, was eine League ist?
/**
 * Die Klasse {@link LeagueTeamEntity} bildet die n:m-Beziehung zwischen League und Team mit ihren dazugehörigen Attributen ab.
 * Es wird abgebildet, welche Teams zu welcher League gehören.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "league_team")
public class LeagueTeamEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation einer League-Team-Beziehung dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO was ist hier die Association?
    private AssociationEntity association;

    /**
     * {@link TeamEntity} bildet das Team der Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    /**
     * {@link LeagueEntity} bildet die League der Beziehung ab.
     */
    @ManyToOne
    @JoinColumn(name = "league_id")
    private LeagueEntity league;
}
