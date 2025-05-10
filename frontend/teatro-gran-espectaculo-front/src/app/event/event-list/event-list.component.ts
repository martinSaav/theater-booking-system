import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.scss'
})
export class EventListComponent {
  eventName = '';
  eventDate = '';
  eventDescription = '';

  events = [
    { name: 'Evento de ejemplo', date: '2025-10-01', description: 'mmm' }
  ];

  createEvent() {
    this.events.push({
      name: this.eventName,
      date: this.eventDate,
      description: this.eventDescription
    });
    this.eventName = '';
    this.eventDate = '';
    this.eventDescription = '';
  }
}
