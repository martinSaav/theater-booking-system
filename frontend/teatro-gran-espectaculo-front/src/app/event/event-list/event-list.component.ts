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

  createEvent() {
    const newEvent: Event = {
      name: this.eventName,
      dateTime: this.eventDate,
      description: this.eventDescription
    };

    const creatorMap = {
      'theater-plays': () => this.eventService.createTheaterPlay(newEvent),
      'concerts': () => this.eventService.createConcert(newEvent),
      'talks': () => this.eventService.createTalk(newEvent),
    };

    this.getFromMap(creatorMap, this.eventType)().subscribe(saved => {
      this.events.push(saved);
      this.eventName = '';
      this.eventDate = '';
      this.eventDescription = '';
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

  private getFromMap<K extends string, V>(
    map: Record<K, V>,
    key: K
  ): V {
    return map[key];
  }
}