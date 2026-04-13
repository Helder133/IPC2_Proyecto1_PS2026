import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReservacionCardComponent } from '../../../components/reservacion/reservacion-card/reservacion-card.component';
import { ReservacionFormComponent } from '../../../components/reservacion/reservacion-form/reservacion-form.component';
import { ReservacionService } from '../../../services/reservacion/reservacion.service';
import { PaqueteTuristicoService } from '../../../services/paqueteTuristico/paquete-turistico.service';
import { Router } from '@angular/router';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';
import { ReservacionUpdate } from '../../../models/reservacion/ReservacionUpdate';
import { ReservacionRequest } from '../../../models/reservacion/ReservacionRequest';
import { PasajeroModalComponent } from "../../../components/reservacion/pasajero-modal.component/pasajero-modal.component";
import { DestinoService } from '../../../services/destino/destino.service';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { ConfirmationModalReservacionCancelacionComponent } from "../../../components/confirmation-modal/confirmation-modal-reservacion-cancelacion/confirmation-modal-reservacion-cancelacion.component";
import { ConfirmationModalReservacionCompletadaComponent } from "../../../components/confirmation-modal/confirmation-modal-reservacion-completada/confirmation-modal-reservacion-completada.component";

@Component({
  selector: 'app-reservacion-page.component',
  imports: [CommonModule, FormsModule, ReservacionCardComponent, ReservacionFormComponent, PasajeroModalComponent, ConfirmationModalReservacionCancelacionComponent, ConfirmationModalReservacionCompletadaComponent],
  templateUrl: './reservacion-page.component.html'
})
export class ReservacionPageComponent implements OnInit {
  private reservacionService = inject(ReservacionService);
  private paqueteService = inject(PaqueteTuristicoService);
  private destinoService = inject(DestinoService);
  private router = inject(Router);

  reservacionesOriginales = signal<ReservacionResponse[]>([]);
  listaPaquetes = signal<PaqueteTuristicoResponse[]>([]);

  cargando = signal<boolean>(true);
  mensajeError = signal<string>('');

  mostrarModal = signal<boolean>(false);
  reservacionEnEdicion = signal<ReservacionResponse>(null!);

  filtroFecha = signal<string>('');
  listaDestinos = signal<DestinoResponse[]>([]);
  filtroDestinoId = signal<string>('');

  mostrarModalPasajeros = signal<boolean>(false);
  reservacionSeleccionada = signal<ReservacionResponse>(null!);

  reservacionCancelar = signal<ReservacionResponse>(null!);
  reservacionCompletar = signal<ReservacionResponse>(null!);

  reservacionesFiltradas = computed(() => {
    let filtradas = this.reservacionesOriginales();

    if (this.filtroFecha()) {
      filtradas = filtradas.filter(r => r.fechaViaje === this.filtroFecha());
    }
    if (this.filtroDestinoId()) {
      const destinoIdNum = Number(this.filtroDestinoId());
      filtradas = filtradas.filter(r => r.paqueteTuristico.destinoId === destinoIdNum);
    }

    return filtradas;
  });

  ngOnInit(): void {
    this.cargarDatosIniciales();
  }

  cargarDatosIniciales(): void {
    this.cargando.set(true);
    this.paqueteService.getPaquetes().subscribe({
      next: (data) => this.listaPaquetes.set(data),
      error: (error) => this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.")
    });

    this.reservacionService.getReservacionesPorUsuario(Number(localStorage.getItem('usuario_id'))).subscribe({
      next: (data) => {
        this.reservacionesOriginales.set(data);
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });

    this.destinoService.getDestinos().subscribe({
      next: (data) => this.listaDestinos.set(data),
      error: (error) => this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.")
    });
  }

  filtrarPorHoy(): void {
    const hoy = new Date().toISOString().split('T')[0];
    this.filtroFecha.set(hoy);
    this.filtroDestinoId.set('');
  }

  limpiarFiltros(): void {
    this.filtroFecha.set('');
    this.filtroDestinoId.set('');
  }

  abrirModalNuevo(): void {
    this.reservacionEnEdicion.set(null!);
    this.mostrarModal.set(true);
  }

  abrirModalEditar(reservacion: ReservacionResponse): void {
    this.reservacionEnEdicion.set(reservacion);
    this.mostrarModal.set(true);
  }

  cerrarModal(): void {
    this.mostrarModal.set(false);
    this.reservacionEnEdicion.set(null!);
  }

  abrirPasajeros(reservacion: ReservacionResponse): void {
    this.reservacionSeleccionada.set(reservacion);
    this.mostrarModalPasajeros.set(true);
  }

  guardarReservacion(datos: ReservacionRequest): void {
    if (this.reservacionEnEdicion()) {

      const actualizar: ReservacionUpdate = {
        reservacionId: this.reservacionEnEdicion().reservacionId,
        paqueteId: datos.paqueteId,
        usuarioId: datos.usuarioId,
        fechaViaje: datos.fechaViaje,
        cantidadPersonas: datos.cantidadPersonas
      };

      console.log("Enviando al backend (Update):", actualizar);

      this.reservacionService.actualizarReservacion(actualizar).subscribe({
        next: () => {
          this.cerrarModal();
          this.cargarDatosIniciales();
        },
        error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      });
    } else {
      this.reservacionService.crearReservacion(datos).subscribe({
        next: () => {
          this.cerrarModal();
          this.cargarDatosIniciales();
        },
        error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      });
    }
  }

  seleccionarReservacionCancelar(reservacion: ReservacionResponse): void {
    this.reservacionCancelar.set(reservacion);
  }

  seleccionarReservacionCompletar(reservacion: ReservacionResponse): void {
    this.reservacionCompletar.set(reservacion);
  }

  cancelar(reservacion: ReservacionResponse): void {
      this.reservacionService.cancelarReservacion(reservacion.reservacionId).subscribe({
        next: () => {
          this.cargarDatosIniciales();
          this.reservacionCancelar.set(null!);
        },
        error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      });
  }

  completar(reservacion: ReservacionResponse): void {
      this.reservacionService.completarReservacion(reservacion.reservacionId).subscribe({
        next: () => {
          this.cargarDatosIniciales();
          this.reservacionCompletar.set(null!);
        },
        error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      });
  }

  eliminar(reservacion: ReservacionResponse): void {
    if (confirm(`ALERTA DE BORRADO: ¿Eliminar la reservación #${reservacion.reservacionId} de la base de datos?`)) {
      this.reservacionService.eliminarReservacion(reservacion.reservacionId).subscribe({
        next: () => {
          this.cargarDatosIniciales();
        },
        error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      });
    }
  }

  irAPagos(id: number): void {
    this.router.navigate(['/reservacion', id, 'pago']);
  }
}
