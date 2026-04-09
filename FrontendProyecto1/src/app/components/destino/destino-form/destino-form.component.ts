import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { DestinoRequest } from '../../../models/destino/DestinoRequest';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-destino-form-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './destino-form.component.html'
})
export class DestinoFormComponent implements OnInit {
  @Input() destinoEditar!: DestinoResponse;
  @Output() destino = new EventEmitter<DestinoRequest>();

  destinoForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.destinoForm = this.formBuilder.group({
      nombre: [this.destinoEditar?.nombre || '', [Validators.required, Validators.maxLength(250)]],
      pais: [this.destinoEditar?.pais || '', [Validators.required, Validators.maxLength(200)]],
      descripcion: [this.destinoEditar?.descripcion || '', [Validators.required, Validators.maxLength(300)]],
      clima_mejor_epoca: [this.destinoEditar?.clima_mejor_epoca || '', [Validators.maxLength(250)]],
      imagen: [this.destinoEditar?.imagen || '', [Validators.maxLength(300)]]
    });
  }

  guardar(): void {
    if (this.destinoForm.invalid) {
      this.destinoForm.markAllAsTouched();
      return;
    }

    this.destino.emit(this.destinoForm.value as DestinoRequest);
  }

}
