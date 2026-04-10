import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ServicioPaqueteResponse } from '../../../models/servicioPaquete/ServicioPaqueteResponse';

@Component({
  selector: 'app-servicio-card-component',
  imports: [CommonModule],
  templateUrl: './servicio-card.component.html'
})
export class ServicioCardComponent {
  @Input({required: true}) servicio!: ServicioPaqueteResponse;
  
  @Output() onDelete = new EventEmitter<ServicioPaqueteResponse>();
  @Output() onEdit = new EventEmitter<ServicioPaqueteResponse>();
}
