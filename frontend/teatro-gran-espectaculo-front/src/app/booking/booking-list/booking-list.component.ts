import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookingService } from '../../_services/booking.service';
import { Booking } from '../../_models/booking';
import { Router } from '@angular/router';

@Component({
  selector: 'app-booking-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.scss'
})
export class BookingListComponent {
  bookings: Booking[] = [];
  router: Router = inject(Router);

  constructor(private bookingService: BookingService) {}

  ngOnInit() {
    this.loadBookings();
  }

  loadBookings() {
    this.bookingService.getAll().subscribe(res => {
      this.bookings = res;
    });
  }

  toCreateBooking() {
    this.router.navigate(['/bookings/create']);
  }

  deleteBooking(booking: Booking) {
    this.bookingService.delete(booking.id!).subscribe(() => {
      this.bookings = this.bookings.filter(b => b.id !== booking.id);
    });
  }
}
