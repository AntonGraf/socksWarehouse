package ru.skypro.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "actionlog")
@Entity
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @JoinColumn(name = "socks_id")
    @ManyToOne
    Socks socks;

    @JoinColumn(name = "operation_id")
    @ManyToOne
    Operation operation;

    @JoinColumn(name = "state_id")
    @ManyToOne
    State state;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "created_at")
    LocalDateTime creatAt;

}
