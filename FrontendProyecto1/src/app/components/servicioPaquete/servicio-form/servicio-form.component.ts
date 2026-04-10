import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { ServicioPaqueteResponse } from '../../../models/servicioPaquete/ServicioPaqueteResponse';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';
import { ServicioPaqueteRequest } from '../../../models/servicioPaquete/ServicioPaqueteRequest';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-servicio-form-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './servicio-form.component.html'
})
export class ServicioFormComponent implements OnInit {
  @Input() servicioEditar!: ServicioPaqueteResponse;
  @Input() listaProveedores!: ProveedorResponse[];
  @Input() paqueteId!: number;

  @Output() servicio = new EventEmitter<ServicioPaqueteRequest>();
  @Output() cancelar = new EventEmitter<void>();

  servicioForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.servicioForm = this.formBuilder.group({
      proveedorId: [{ value: this.servicioEditar?.proveedorId || '', disabled: this.servicioEditar !== null }, [Validators.required]],
      descripcion: [this.servicioEditar?.descripcion || '', [Validators.required, Validators.maxLength(250)]],
      costo: [this.servicioEditar?.costo || '', [Validators.required, Validators.min(1)]]
    });
  }

  guardar(): void {
    if (this.servicioForm.invalid) {
      this.servicioForm.markAllAsTouched();
      return;
    }

    const valores = this.servicioForm.getRawValue();
    const servicioRequest: ServicioPaqueteRequest = {
      proveedorId: Number(valores.proveedorId),
      paqueteId: this.paqueteId,
      descripcion: valores.descripcion,
      costo: Number(valores.costo)
    };

    this.servicio.emit(servicioRequest);
  }

}
