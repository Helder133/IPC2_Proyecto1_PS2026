import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-footer-component',
  imports: [],
  templateUrl: './footer.component.html'
})
export class FooterComponent {
  year = signal<Date>(new Date());
}
