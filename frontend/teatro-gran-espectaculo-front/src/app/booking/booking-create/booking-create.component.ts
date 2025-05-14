import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Booking } from '../../_models/booking';
import { BookingService } from '../../_services/booking.service';
import { Ticket } from '../../_models/ticket';
import { TicketService } from '../../_services/ticket.service';
import { EventService } from '../../_services/event.service';
import { Event } from '../../_models/event';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-booking-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking-create.component.html',
  styleUrl: './booking-create.component.scss'
})
export class BookingCreateComponent {
  email = '';
  customerName = '';
  phone = '';
  ticketId: number = 1;
  private router = inject(Router);
  tickets: Ticket[] = [];
  eventsMap: Map<number, Event> = new Map();

  constructor(private bookingService: BookingService, private ticketService: TicketService, private eventService: EventService) { }


  ngOnInit(): void {
    this.ticketService.getAllTickets().subscribe(tickets => {
      this.tickets = tickets;
    });

    this.eventService.getAllEvents().subscribe(events => {
      for (let ev of events) {
        if (ev.id !== undefined) { // solucion mmm
          this.eventsMap.set(ev.id, ev);
        }
      }
    });
  }


  createBooking() {
    const booking: Booking = {
      customerEmail: this.email,
      customerName: this.customerName,
      customerPhone: this.phone,
      ticketId: this.ticketId,
    };
    console.log(booking);
    this.bookingService.create(booking).subscribe(() => {
      this.router.navigate(['/bookings']);
    });
  }

  toBookingList() {
    this.router.navigate(['/bookings']);
  }

  getTicketDisplay(ticket: Ticket): string {
    const event = this.eventsMap.get(ticket.eventId);
    if (!event) return '';
    console.log(event);
    console.log(ticket);
    const fecha = new Date(event.dateTime);
    const fechaStr = fecha.toLocaleDateString('es-AR');
    const horaStr = fecha.toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' });

    return `${event.name} - ${fechaStr} ${horaStr} - ${ticket.type} ($${ticket.price})`;
  }

}
