import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';

@Component({
  selector: 'confirmation-modal-proveedor-component',
  imports: [],
  templateUrl: './confirmation-modal-proveedor.component.html'
})
export class ConfirmationModalProveedorComponent {
  @Input() proveedor!: ProveedorResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarEliminacion(): void {
    this.onConfirm.emit();
  }
}
