package edu.mv.db.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "rocket")
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rocket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String sorte;
}
