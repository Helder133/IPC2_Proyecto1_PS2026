import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';

@Component({
  selector: 'confirmation-modal-paquete-component',
  imports: [],
  templateUrl: './confirmation-modal-paquete.component.html'
})
export class ConfirmationModalPaqueteComponent {
  @Input() paquete!: PaqueteTuristicoResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarEliminacion(): void {
    this.onConfirm.emit();
  }
}
