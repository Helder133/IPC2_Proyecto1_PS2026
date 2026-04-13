import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';

@Component({
  selector: 'confirmation-modal-reservacion-completada-component',
  imports: [],
  templateUrl: './confirmation-modal-reservacion-completada.component.html'
})
export class ConfirmationModalReservacionCompletadaComponent {
  @Input() reservacion!: ReservacionResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarCompletacion(): void {
    this.onConfirm.emit();
  }
}
