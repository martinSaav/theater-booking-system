import { Injectable} from '@angular/core';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Event } from '../_models/event';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/events';

  getAllEvents() {
    return this.http.get<Event[]>(this.baseUrl);
  }

  createEvent(event: Event) {
    return this.http.post<Event>(this.baseUrl, event);
  }
}
