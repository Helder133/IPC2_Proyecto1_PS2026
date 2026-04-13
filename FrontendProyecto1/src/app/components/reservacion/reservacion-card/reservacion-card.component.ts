import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservacion-card-component',
  imports: [CommonModule],
  templateUrl: './reservacion-card.component.html'
})
export class ReservacionCardComponent {
  @Input() soloLectura: boolean = false;
  @Input() reservacion!: ReservacionResponse;
  @Output() onEdit = new EventEmitter<ReservacionResponse>();
  @Output() onDelete = new EventEmitter<ReservacionResponse>();
  @Output() onCancel = new EventEmitter<ReservacionResponse>();
  @Output() onComplete = new EventEmitter<ReservacionResponse>();
  @Output() onPagos = new EventEmitter<number>();
  @Output() onPasajeros = new EventEmitter<ReservacionResponse>();

  getBadgeColor(estado: string): string {
    switch (estado) {
      case 'Confirmada': return 'bg-success';
      case 'Pendiente': return 'bg-warning text-dark';
      case 'Cancelada': return 'bg-danger';
      case 'Completada': return 'bg-info text-dark';
      default: return 'bg-secondary';
    }
  }

  getBorderColor(estado: string): string {
    switch (estado) {
      case 'Confirmada': return 'border-success';
      case 'Cancelada': return 'border-danger';
      case 'Completada': return 'border-info';
      default: return 'border-warning';
    }
  }

}
