import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ServicioPaqueteResponse } from '../../../models/servicioPaquete/ServicioPaqueteResponse';

@Component({
  selector: 'confirmation-modal-servicio-component',
  imports: [],
  templateUrl: './confirmation-modal-servicio.component.html'
})
export class ConfirmationModalServicioComponent {
  @Input() servicio!: ServicioPaqueteResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarEliminacion(): void {
    this.onConfirm.emit();
  }
}
