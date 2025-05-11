import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../_services/event.service';
import { Event } from '../../_models/event';
import { Router } from '@angular/router';


@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.scss'
})
export class EventListComponent implements OnInit {
  private router = inject(Router);
  eventName = '';
  eventDate = '';
  eventDescription = '';
  eventType = 'theater-plays';
  events: Event[] = [];

  constructor(private eventService: EventService) { }

  ngOnInit() {
    this.eventService.getAllEvents().subscribe(events => {
      this.events = events;
    });
  }

  toCreateEvent() {
    this.router.navigate(['/events/create']);
  }

  toEventEdit(event: Event) {
    this.router.navigate(['/events', event.id, 'edit']);
  }

  deleteEvent(event: Event) {
    this.eventService.deleteEvent(event).subscribe(() => {
      this.events = this.events.filter(e => e.id !== event.id);
    });
  }
}