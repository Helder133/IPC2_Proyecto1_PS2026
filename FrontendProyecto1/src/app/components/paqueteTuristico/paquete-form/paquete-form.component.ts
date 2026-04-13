import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { PaqueteTuristicoRequest } from '../../../models/paqueteTuristico/PaqueteTuristicoRequest';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-paquete-form-component',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './paquete-form.component.html'
})
export class PaqueteFormComponent implements OnInit{
  @Input() paqueteEditar!: PaqueteTuristicoResponse;
  @Input({required: true}) listaDestinos!: DestinoResponse[];
  @Output() paquete = new EventEmitter<PaqueteTuristicoRequest>();
  
  paqueteForm!: FormGroup;
  private formBuilder = inject(FormBuilder); 

  ngOnInit(): void {
    this.paqueteForm = this.formBuilder.group({
      nombre: [this.paqueteEditar?.nombre || '', [Validators.required, Validators.maxLength(250)]],
      destinoId: [this.paqueteEditar?.destinoId || '', [Validators.required]],
      duracion: [this.paqueteEditar?.duracion || '', [Validators.required, Validators.min(1)]],
      precioPublico: [this.paqueteEditar?.precioPublico || '', [Validators.required, Validators.min(1)]],
      capacidadMaxima: [this.paqueteEditar?.capacidadMaxima || '', [Validators.required, Validators.min(1)]],
      descripcion: [this.paqueteEditar?.descripcion || '', [Validators.maxLength(300)]]
    });
  }

  guardar(): void {
    if (this.paqueteForm.invalid) {
      this.paqueteForm.markAllAsTouched();
      return;
    }
    this.paquete.emit(this.paqueteForm.value);
  }

}
