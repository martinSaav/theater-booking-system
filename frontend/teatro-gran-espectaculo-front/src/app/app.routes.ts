import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
import { EventListComponent } from './event/event-list/event-list.component';
import { BookingListComponent } from './booking/booking-list/booking-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'events', pathMatch: 'full' },
  { path: 'events', component: EventListComponent },
  { path: 'bookings', component: BookingListComponent }
];

export const appRouterProviders = provideRouter(routes);
