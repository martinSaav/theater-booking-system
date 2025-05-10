import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../_services/event.service';
import { Event } from '../../_models/event';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.scss'
})
export class EventListComponent implements OnInit {
  eventName = '';
  eventDate = '';
  eventDescription = '';
  events: Event[] = [];

  constructor(private eventService: EventService) {}

  ngOnInit() {
    this.eventService.getAllEvents().subscribe(events => {
      this.events = events;
    });
  }

  createEvent() {
    const newEvent: Event = {
      name: this.eventName,
      dateTime: this.eventDate,
      description: this.eventDescription
    };

    this.eventService.createEvent(newEvent).subscribe(saved => {
      this.events.push(saved);
      this.eventName = '';
      this.eventDate = '';
      this.eventDescription = '';
    });
  }
}