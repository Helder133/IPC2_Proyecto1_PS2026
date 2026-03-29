import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UsuarioResponse } from '../../../models/usuario/UsuarioResponse';

@Component({
  selector: 'confirmation-modal-usuario-component',
  imports: [],
  templateUrl: './confirmation-modal-usuario.component.html'
})
export class ConfirmationModalUsuarioComponent {
  @Input() usuario!: UsuarioResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarEliminacion(): void {
    this.onConfirm.emit();
  }
}
