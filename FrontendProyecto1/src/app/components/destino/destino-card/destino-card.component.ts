import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-destino-card-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './destino-card.component.html'
})
export class DestinoCardComponent {
  @Input() destino!: DestinoResponse;
  @Output() onDelete = new EventEmitter<DestinoResponse>();
}
