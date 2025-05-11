package com.theater.booking.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Attendance> attendances = new ArrayList<>();

    //@Column(name = "next_booking_discount", nullable = false)
    //private Double nextBookingDiscount = 0.0;

    @Column(name = "reward_count", nullable = false)
    private int rewardCount = 0;

    @Column(name = "rewarded_attendance_count", nullable = false)
    private int rewardedAttendanceCount = 0;

    public boolean shouldGetFreeBooking() {
        return rewardCount > 0;
    }

    public void consumeReward() {
        if (rewardCount > 0) {
            rewardCount--;
        }
    }

    public void evaluateRewardEligibility() {
        long recentAttendances = attendances.stream()
                .filter(a -> a.getEvent().getDateTime().isAfter(LocalDateTime.now().minusYears(1)))
                .count();

        int newRewards = (int) ((recentAttendances - rewardedAttendanceCount) / 5);
        if (newRewards > 0) {
            rewardCount += newRewards;
            rewardedAttendanceCount += newRewards * 5;
        }
    }
}
