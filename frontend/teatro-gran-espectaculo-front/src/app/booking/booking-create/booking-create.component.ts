import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Booking } from '../../_models/booking';
import { BookingService } from '../../_services/booking.service';

@Component({
  selector: 'app-booking-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './booking-create.component.html',
  styleUrl: './booking-create.component.scss'
})
export class BookingCreateComponent {
  email = '';
  customerName = '';
  phone = '';
  ticketId: number = 1;
  private router = inject(Router);

  constructor(private bookingService: BookingService) {}
  
  createBooking() {
    const booking: Booking = {
      customerEmail: this.email,
      customerName: this.customerName,
      customerPhone: this.phone,
      ticketId: this.ticketId
    };
    console.log(booking);
    this.bookingService.create(booking).subscribe(() => {
      this.router.navigate(['/bookings']);
    });
  }

  toBookingList() {
    this.router.navigate(['/bookings']);
  }
}
