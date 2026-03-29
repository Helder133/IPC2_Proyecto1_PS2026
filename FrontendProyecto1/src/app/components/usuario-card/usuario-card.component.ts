import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UsuarioResponse } from '../../models/usuario/UsuarioResponse';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuario-card-component',
  imports: [CommonModule],
  templateUrl: './usuario-card.component.html'
})
export class UsuarioCardComponent {
  @Input() usuario!: UsuarioResponse;

  @Output() onEdit = new EventEmitter<UsuarioResponse>();
  @Output() onDelete = new EventEmitter<UsuarioResponse>(); 
  enumUsuario = EnumUsuario;
  
}
