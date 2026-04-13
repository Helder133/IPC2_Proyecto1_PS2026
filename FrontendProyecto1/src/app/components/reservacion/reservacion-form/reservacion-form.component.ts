import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { ReservacionRequest } from '../../../models/reservacion/ReservacionRequest';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservacion-form-component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reservacion-form.component.html'
})
export class ReservacionFormComponent implements OnInit{

  @Input() reservacionEditar!: ReservacionResponse;
  @Input() listaPaquetes!: PaqueteTuristicoResponse[];
  @Output() reservacion = new EventEmitter<ReservacionRequest>();
  @Output() onCancel = new EventEmitter<void>();
  usuarioId = signal<number>(null!);

  reservacionForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  ngOnInit(): void {
    this.usuarioId.set(Number(localStorage.getItem('usuario_id')));
    const hoy = new Date().toISOString().split('T')[0]; // Fecha actual en formato YYYY-MM-DD

    this.reservacionForm = this.formBuilder.group({
      paqueteId: [this.reservacionEditar?.paqueteId || '', [Validators.required]],
      fechaViaje: [this.reservacionEditar?.fechaViaje || '', [Validators.required]],
      cantidadPersonas: [this.reservacionEditar?.cantidadPersona || 1, [Validators.required, Validators.min(1)]],
      fechaCreacion: [{ value: this.reservacionEditar?.fechaCreacion || hoy, disabled: this.reservacionEditar !== null }, [Validators.required]]
    });
  }

  guardar(): void {
    if (this.reservacionForm.invalid) {
      this.reservacionForm.markAllAsTouched();
      return;
    }

    const valores = this.reservacionForm.getRawValue();

    const request: ReservacionRequest = {
      paqueteId: Number(valores.paqueteId),
      usuarioId: this.usuarioId(),
      fechaViaje: valores.fechaViaje,
      fechaCreacion: valores.fechaCreacion,
      cantidadPersonas: Number(valores.cantidadPersonas)
    };

    this.reservacion.emit(request);
  }

}
