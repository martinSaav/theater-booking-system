import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Booking } from '../_models/booking';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/bookings';

  getAll() {
    return this.http.get<Booking[]>(this.baseUrl);
  }

  create(booking: Booking) {
    return this.http.post<Booking>(this.baseUrl, booking);
  }
}
