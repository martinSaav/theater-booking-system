package com.theater.booking.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="concert")
public class Concert extends Event {
}
