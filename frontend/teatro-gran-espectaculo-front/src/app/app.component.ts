import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  template: `
    <nav>
      <a routerLink="/events">Events</a> |
      <a routerLink="/bookings">Bookings</a>
    </nav>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  title = 'teatro-gran-espectaculo-front';
}
