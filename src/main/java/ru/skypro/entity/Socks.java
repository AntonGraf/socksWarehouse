package ru.skypro.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "socks")
@Entity
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @Column(name = "color")
    String color;

    @Column(name = "cotton_part")
    int cottonPart;

    @OneToOne(mappedBy = "socks")
    @JsonIgnore
    Warehouse warehouse;

    @OneToMany(mappedBy = "socks")
    @JsonIgnore
    List<Action> actions;
}
