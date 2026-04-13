import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'confirmation-modal-reservacion-cliente-component',
  imports: [],
  templateUrl: './confirmation-modal-reservacion-cliente.component.html'
})
export class ConfirmationModalReservacionClienteComponent {
  @Input() clienteId!: number;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarCancelacion(): void {
    this.onConfirm.emit();
  }
}
