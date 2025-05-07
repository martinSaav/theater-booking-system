package com.theater.booking.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="theater_play")
public class TheaterPlay extends Event {
}
