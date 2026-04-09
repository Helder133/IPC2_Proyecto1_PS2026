import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';
import { ProveedorRequest } from '../../../models/proveedor/ProveedorRequest';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EnumProveedor } from '../../../models/proveedor/EnumProveedor';

@Component({
  selector: 'app-proveedor-form-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './proveedor-form.component.html'
})
export class ProveedorFormComponent implements OnInit {
  @Input() proveedorEditar!: ProveedorResponse;
  @Output() proveedor = new EventEmitter<ProveedorRequest>();

  proveedorForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  enumProveedor = EnumProveedor;
  tiposProveedor = Object.values(EnumProveedor);

  ngOnInit(): void {
    this.proveedorForm = this.formBuilder.group({
      nombre: [this.proveedorEditar?.nombre || '', [Validators.required, Validators.maxLength(250)]],
      pais: [this.proveedorEditar?.pais || '', [Validators.required, Validators.maxLength(250)]],
      tipo: [this.proveedorEditar?.tipo || '', [Validators.required]],
      contacto: [this.proveedorEditar?.contacto || '', [Validators.minLength(8), Validators.maxLength(20)]]
    });
  }

  guardar(): void {
    if (this.proveedorForm.invalid) {
      this.proveedorForm.markAllAsTouched();
      return;
    }

    this.proveedor.emit(this.proveedorForm.value as ProveedorRequest);
  }

}
