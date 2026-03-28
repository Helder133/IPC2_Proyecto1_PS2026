import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteResponse } from '../../models/cliente/ClienteResponse';
import { ClienteRequest } from '../../models/cliente/clienteRequest';
import { validate } from '@angular/forms/signals';

@Component({
  selector: 'app-cliente-form-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cliente-form.component.html'
})
export class ClienteFormComponent implements OnInit {
  @Input() clienteEditar = signal<ClienteResponse>(null!);

  @Output() clienteNuevo = signal<EventEmitter<ClienteRequest>>(new EventEmitter<ClienteRequest>());

  clienteForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.clienteForm = this.formBuilder.group({
      dpi_o_pasaporte: [this.clienteEditar()?.dpi_o_pasaporte || '', [Validators.required, Validators.maxLength(20), Validators.minLength(13)]],
      nombre: [this.clienteEditar()?.nombre || '', [Validators.required, Validators.maxLength(300)]],
      fecha: [this.clienteEditar()?.fecha || '', [Validators.required]],
      telefono: [this.clienteEditar()?.telefono || '', [Validators.maxLength(20), Validators.minLength(8)]],
      email: [this.clienteEditar()?.email || '', [Validators.email, Validators.maxLength(100)]],
      nacionalidad: [this.clienteEditar()?.nacionalidad || '', [Validators.required, Validators.maxLength(100)]]
    })
  }

}
