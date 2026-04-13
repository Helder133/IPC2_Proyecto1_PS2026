import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ClienteResponse } from '../../../models/cliente/ClienteResponse';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-cliente-card-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './cliente-card.component.html'
})
export class ClienteCardComponent {
  @Input() cliente!: ClienteResponse;
  @Output() onDelete = new EventEmitter<ClienteResponse>();
}
