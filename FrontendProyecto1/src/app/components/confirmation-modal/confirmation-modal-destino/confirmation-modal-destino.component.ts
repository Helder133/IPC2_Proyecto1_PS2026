import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';

@Component({
  selector: 'confirmation-modal-destino-component',
  imports: [],
  templateUrl: './confirmation-modal-destino.component.html'
})
export class ConfirmationModalDestinoComponent {
  @Input() destino!: DestinoResponse;
  @Output() onConfirm = new EventEmitter<void>();
  confirmarEliminacion(): void {
    this.onConfirm.emit();
  }
}
