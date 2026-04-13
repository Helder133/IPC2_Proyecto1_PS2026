import { CommonModule } from '@angular/common';
import { Component, EventEmitter, inject, Input, OnInit, Output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReservacionService } from '../../../services/reservacion/reservacion.service';
import { ClientService } from '../../../services/client/client.service';
import { ClienteResponse } from '../../../models/cliente/ClienteResponse';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';
import { ReservacionClienteRequest } from '../../../models/reservacion/client/ReservacionClienteRequest';

@Component({
  selector: 'app-pasajero-modal-component',
  imports: [CommonModule, FormsModule],
  templateUrl: './pasajero-modal.component.html'
})
export class PasajeroModalComponent implements OnInit {

  private reservacionService = inject(ReservacionService);
  private clienteService = inject(ClientService);

  @Input() reservacion!: ReservacionResponse; 
  @Output() onClose = new EventEmitter<void>();
  @Output() onUpdate = new EventEmitter<void>();

  pasajeros = signal<ClienteResponse[]>([]);
  resultadosBusqueda = signal<ClienteResponse[]>([]);
  terminoBusqueda = signal<string>('');
  buscando = signal<boolean>(false);
  cargandoPasajeros = signal<boolean>(true);

  mensajeError = signal<string>('');

  ngOnInit(): void {
    this.cargarPasajeros();
  }

  cargarPasajeros(): void {
    this.cargandoPasajeros.set(true);
    this.reservacionService.getPasajerosDeReservacion(this.reservacion.reservacionId).subscribe({
      next: (data) => {
        this.pasajeros.set(data);
        this.cargandoPasajeros.set(false);
      },
      error: (err) => {
        this.cargandoPasajeros.set(false)
        this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      }
    });
  }

  buscarClientes(): void {
    if (this.terminoBusqueda().trim().length < 3) {
      this.resultadosBusqueda.set([]);
      return;
    }
    this.buscando.set(true);
    this.clienteService.buscarClientes(this.terminoBusqueda()).subscribe({
      next: (res) => {
        const idsActuales = this.pasajeros().map(p => p.cliente_id);
        this.resultadosBusqueda.set(res.filter(c => !idsActuales.includes(c.cliente_id)));
        this.buscando.set(false);
      },
      error: (err) => {
        this.buscando.set(false);
        this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      }
    });
  }

  agregarPasajero(clienteId: number): void {
    const totalActual = this.pasajeros().length;
    
    if (totalActual >= this.reservacion.paqueteTuristico.capacidadMaxima) {
      alert(`Límite alcanzado: Esta reserva es solo para ${this.reservacion.paqueteTuristico.capacidadMaxima} personas.`);
      return;
    }

    const request: ReservacionClienteRequest = {
      reservacionId: this.reservacion.reservacionId,
      clienteId: clienteId
    };

    this.reservacionService.agregarClienteAReservacion(request).subscribe({
      next: () => {
        this.terminoBusqueda.set('');
        this.resultadosBusqueda.set([]);
        this.cargarPasajeros();
        this.onUpdate.emit();
      },
      error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
    });
  }

  quitarPasajero(clienteId: number): void {
    if (confirm('¿Retirar a este pasajero de la reservación?')) {
      this.reservacionService.eliminarClienteDeReservacion(this.reservacion.reservacionId, clienteId).subscribe({
        next: () => {
          this.cargarPasajeros();
          this.onUpdate.emit();
        },
        error: (err) => this.mensajeError.set(err.error?.error || "Error de conexión con el servidor.")
      });
    }
  }

}
