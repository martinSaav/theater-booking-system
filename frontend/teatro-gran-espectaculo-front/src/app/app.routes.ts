import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { EventListComponent } from './event/event-list/event-list.component';
import { BookingListComponent } from './booking/booking-list/booking-list.component';
import { EventCreateComponent } from './event/event-create/event-create.component';
import { EventEditComponent } from './event/event-edit/event-edit.component';
import { BookingCreateComponent } from './booking/booking-create/booking-create.component';

export const routes: Routes = [
  { path: '', redirectTo: 'events', pathMatch: 'full' },
  { path: 'events', component: EventListComponent },
  { path: 'bookings', component: BookingListComponent },
  { path: 'events', component: EventListComponent },
  { path: 'events/create', component: EventCreateComponent },
  { path: 'events/:id/edit', component: EventEditComponent },
  { path: 'bookings/create', component: BookingCreateComponent },
];

export const appRouterProviders = provideRouter(routes);
