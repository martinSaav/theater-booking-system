import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingService } from '../../_services/booking.service';
import { Booking } from '../../_models/booking';

@Component({
  selector: 'app-booking-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.scss'
})
export class BookingListComponent {
  email = '';
  customerName = '';
  phone = '';
  ticketId: number = 1;
  bookings: Booking[] = [];

  constructor(private bookingService: BookingService) {}

  ngOnInit() {
    this.loadBookings();
  }

  loadBookings() {
    this.bookingService.getAll().subscribe(res => {
      this.bookings = res;
    });
  }

  createBooking() {
    const booking: Booking = {
      customerEmail: this.email,
      customerName: this.customerName,
      customerPhone: this.phone,
      ticketId: this.ticketId
    };

    this.bookingService.create(booking).subscribe(newRes => {
      this.bookings.push(newRes);
      this.email = '';
      this.customerName = '';
      this.phone = '';
      this.ticketId = 1;
    });
  }
}
