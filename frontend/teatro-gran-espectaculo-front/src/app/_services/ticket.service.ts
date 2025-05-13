import { Injectable} from '@angular/core';
import { inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Ticket } from '../_models/ticket';

@Injectable({
  providedIn: 'root'
})
export class TicketService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiBaseUrl}/events/tickets`;

  getAllTickets() {
    return this.http.get<Ticket[]>(`${this.baseUrl}`);
  }
}
