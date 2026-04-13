import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';

@Component({
  selector: 'confirmation-modal-reservacion-cancelacion-component',
  imports: [],
  templateUrl: './confirmation-modal-reservacion-cancelacion.component.html'
})
export class ConfirmationModalReservacionCancelacionComponent {
  @Input() reservacion!: ReservacionResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarCancelacion(): void {
    this.onConfirm.emit();
  }
}
