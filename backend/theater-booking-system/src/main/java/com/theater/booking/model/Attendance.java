package com.theater.booking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "attendance")
public class Attendance extends Base {

    @ManyToOne
    @JoinColumn(name = "customer_email")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
