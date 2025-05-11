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

  getAllTheaterPlays() {
    return this.http.get<Event[]>(`${this.baseUrl}/theater-plays`);
  }

  getAllConcerts() {
    return this.http.get<Event[]>(`${this.baseUrl}/concerts`);
  }

  getAllTalks() {
    return this.http.get<Event[]>(`${this.baseUrl}/talks`);
  }

  createTheaterPlay(event: Event) {
    return this.http.post<Event>(`${this.baseUrl}/theater-plays`, event);
  }

  createConcert(event: Event) {
    return this.http.post<Event>(`${this.baseUrl}/concerts`, event);
  }

  createTalk(event: Event) {
    return this.http.post<Event>(`${this.baseUrl}/talks`, event);
  }

  deleteEvent(event: Event) {
    return this.http.delete(`${this.baseUrl}/${event.id}`);
  }
}
