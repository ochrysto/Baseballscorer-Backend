package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game_umpire")
public class GameUmpireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_gameNr")
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name = "umpire_passnumber")
    private UmpireEntity umpire;
}
