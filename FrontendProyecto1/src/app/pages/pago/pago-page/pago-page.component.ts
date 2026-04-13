import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ReservacionService } from '../../../services/reservacion/reservacion.service';
import { HistorialPagoService } from '../../../services/pago/historial-pago.service';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';
import { HistorialPagoResponse } from '../../../models/pago/HistorialPagoResponse';
import { EnumReservacion } from '../../../models/reservacion/EnumReservacion';
import { HistorialPagoRequest } from '../../../models/pago/HistorialPagoRequest';

@Component({
  selector: 'app-pago-page.component',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './pago-page.component.html'
})
export class PagoPageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private reservacionService = inject(ReservacionService);
  private pagoService = inject(HistorialPagoService);

  reservacionId = signal<number>(null!);
  reservacion = signal<ReservacionResponse>(null!);
  pagos = signal<HistorialPagoResponse[]>([]);

  cargando = signal<boolean>(true);
  mensajeError = signal<string>('');

  pagoForm!: FormGroup;
  private formBuilder = inject(FormBuilder);

  saldoPendiente = computed(() => {
    if (!this.reservacion()) return 0;
    if (this.reservacion().estado === EnumReservacion.Cancelada) return 0;
    return this.reservacion().costoTotal - this.reservacion().totalPagoRealizado;
  });

  ngOnInit(): void {
    this.reservacionId.set((this.route.snapshot.params['id']));
    this.inicializarFormulario();
    this.cargarDatos();
  }

  inicializarFormulario(): void {
    const hoy = new Date().toISOString().split('T')[0];
    this.pagoForm = this.formBuilder.group({
      monto: ['', [Validators.required, Validators.min(1)]],
      metodo: ['', [Validators.required]],
      fecha: [hoy, [Validators.required]]
    });
  }

  cargarDatos(): void {
    this.cargando.set(true);
    this.reservacionService.getReservacion(this.reservacionId()).subscribe({
      next: (res) => {
        this.reservacion.set(res);
        const pendiente = res.costoTotal - res.totalPagoRealizado;
        this.pagoForm.get('monto')?.setValidators([Validators.required, Validators.min(1), Validators.max(pendiente)]);
        this.pagoForm.get('monto')?.updateValueAndValidity();
      },
      error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
    });

    this.pagoService.getHistorialPagoReservacion(this.reservacionId()).subscribe({
      next: (pagos) => {
        this.pagos.set(pagos);
        this.cargando.set(false);
      },
      error: (err) => {
        this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  registrarPago(): void {
    if (this.pagoForm.invalid) {
      this.pagoForm.markAllAsTouched();
      return;
    }

    if (this.saldoPendiente() <= 0) {
      this.mensajeError.set('La reservación ya está pagada en su totalidad o cancelada.');
      setTimeout(() => this.mensajeError.set(''), 3000);
      return;
    }

    const request: HistorialPagoRequest = {
      reservacionId: this.reservacionId(),
      monto: this.pagoForm.value.monto,
      metodo: this.pagoForm.value.metodo,
      fecha: this.pagoForm.value.fecha
    };

    this.pagoService.insertar(request).subscribe({
      next: () => {
        this.pagoForm.reset({ fecha: new Date().toISOString().split('T')[0], metodo: '' });
        this.cargarDatos();
      },
      error: (err) => {
        this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

  generarRecibo(pago: HistorialPagoResponse): void {
    localStorage.setItem('recibo_pago', JSON.stringify(pago));
    localStorage.setItem('recibo_reservacion', JSON.stringify(this.reservacion()));
    const url = `/pago/recibo`;
    window.open(url, '_blank');
  }

}
