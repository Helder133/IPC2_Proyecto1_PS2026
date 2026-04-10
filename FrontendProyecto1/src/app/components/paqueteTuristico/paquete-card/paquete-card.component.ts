import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';

@Component({
  selector: 'app-paquete-card-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './paquete-card.component.html'
})
export class PaqueteCardComponent {
  @Input() paquete!: PaqueteTuristicoResponse;
  @Output() onDelete = new EventEmitter<PaqueteTuristicoResponse>();
  @Output() onToggleEstado = new EventEmitter<number>();
  @Output() onVerServicios = new EventEmitter<number>();
}
