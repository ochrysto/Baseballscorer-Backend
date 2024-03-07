package com.example.baseballscoresheet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Die Klasse {@link AssociationEntity} bildet ein Association-Objekt mit seinen dazugehörigen Attributen ab.
 * Eine Association ist ein Verband.
 * Ein Verband ist ein Zusammenschluss mehrerer Sportvereine verschiedener oder gleicher Sportarten.
 */
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "association")
public class AssociationEntity {
    /**
     * Attribut, das zur eindeutigen Identifikation eines Association-Objekts dient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name des Verbandes.
     */
    @NotBlank(message = "the name of the association is mandatory")
    private String name;
}
