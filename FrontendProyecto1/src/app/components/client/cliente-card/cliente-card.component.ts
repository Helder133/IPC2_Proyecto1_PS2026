import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ClienteResponse } from '../../../models/cliente/ClienteResponse';

@Component({
  selector: 'app-cliente-card-component',
  imports: [CommonModule],
  templateUrl: './cliente-card.component.html'
})
export class ClienteCardComponent {
  @Input() cliente!: ClienteResponse;

  @Output() onEdit = new EventEmitter<ClienteResponse>();
  @Output() onDelete = new EventEmitter<number>();
}
