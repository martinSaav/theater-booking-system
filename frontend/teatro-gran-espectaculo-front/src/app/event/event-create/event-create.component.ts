import { Component, inject } from '@angular/core';
import { EventService } from '../../_services/event.service';
import { Event } from '../../_models/event';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-create',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './event-create.component.html',
  styleUrl: './event-create.component.scss'
})
export class EventCreateComponent {
  eventName = '';
  eventDate = '';
  eventDescription = '';
  eventType = 'theater-plays';
  private router = inject(Router);

  constructor(private eventService: EventService) { }

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

    this.getFromMap(creatorMap, this.eventType)().subscribe(() => {
      this.router.navigate(['/events']);
    });

  }

  toEventList() {
    this.router.navigate(['/events']);
  }

  private getFromMap<K extends string, V>(
    map: Record<K, V>,
    key: K
  ): V {
    return map[key];
  }

}
