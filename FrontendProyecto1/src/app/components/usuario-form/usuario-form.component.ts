import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { UsuarioResponse } from '../../models/usuario/UsuarioResponse';
import { UsuarioRequest } from '../../models/usuario/UsuarioRequest';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';

@Component({
  selector: 'app-usuario-form-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './usuario-form.component.html'
})
export class UsuarioFormComponent implements OnInit{
  
  @Input() usuarioEditar = signal<UsuarioResponse>(null!);

  @Output() usuarioNuevo = signal<EventEmitter<UsuarioRequest>>(new EventEmitter<UsuarioRequest>());
  
  usuarioForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  enumUsuario = EnumUsuario;

  ngOnInit(): void {
    this.usuarioForm = this.formBuilder.group({
      nombre: [this.usuarioEditar()?.nombre || '', [Validators.required, Validators.maxLength(250)]],
      password: ['', this.usuarioEditar() ? [] : [Validators.required]],
      rol: [this.usuarioEditar()?.rol || EnumUsuario.Atencion_al_Cliente, [Validators.required]]
    })
  }

  guardar(): void {
    if (this.usuarioForm.invalid) {
      this.usuarioForm.markAllAsTouched();
      return;
    }

    const usuarioRequest = signal<UsuarioRequest>(this.usuarioForm.value);
    this.usuarioNuevo().emit(usuarioRequest());
  }

}
