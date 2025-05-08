package com.theater.booking.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "talk")
public class Talk extends Event {

    @Column(name = "has_meet_and_greet", nullable = false)
    private Boolean hasMeetAndGreet;
}
