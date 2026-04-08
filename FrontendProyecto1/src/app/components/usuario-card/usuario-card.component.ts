import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UsuarioResponse } from '../../models/usuario/UsuarioResponse';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';
import { CommonModule } from '@angular/common';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-usuario-card-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './usuario-card.component.html'
})
export class UsuarioCardComponent {
  @Input() usuario!: UsuarioResponse;
  @Output() onDelete = new EventEmitter<UsuarioResponse>(); 
  @Output() notEstado = new EventEmitter<number>();
  enumUsuario = EnumUsuario;
  
}
