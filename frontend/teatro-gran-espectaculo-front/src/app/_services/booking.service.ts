import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { inject } from '@angular/core';
import { Booking } from '../_models/booking';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiBaseUrl}/bookings`;
  getAll() {
    return this.http.get<Booking[]>(this.baseUrl);
  }

  create(booking: Booking) {
    return this.http.post<Booking>(this.baseUrl, booking);
  }
}
