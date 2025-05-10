import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-booking-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.scss'
})
export class BookingListComponent {
  customerName = '';
  eventName = '';

  bookings = [
    { customer: 'martin', event: 'a' }
  ];

  createBooking() {
    this.bookings.push({
      customer: this.customerName,
      event: this.eventName
    });
    this.customerName = '';
    this.eventName = '';
  }
}
