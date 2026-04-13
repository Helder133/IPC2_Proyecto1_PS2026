import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ReservacionCardComponent } from '../../../components/reservacion/reservacion-card/reservacion-card.component';
import { ReservacionService } from '../../../services/reservacion/reservacion.service';
import { ClientService } from '../../../services/client/client.service';
import { ClienteResponse } from '../../../models/cliente/ClienteResponse';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';

@Component({
  selector: 'app-historial-reservacion-cliente.component',
  imports: [CommonModule, RouterLink, ReservacionCardComponent],
  templateUrl: './historial-reservacion-cliente.component.html'
})
export class HistorialReservacionClienteComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private reservacionService = inject(ReservacionService);
  private clienteService = inject(ClientService);

  cliente = signal<ClienteResponse>(null!);
  reservaciones = signal<ReservacionResponse[]>([]);
  cargando = signal<boolean>(true);
  mensajeError = signal<string>('');



  ngOnInit(): void {
    const id = (this.route.snapshot.params['id']);
    if (id) {
      this.cargarHistorial(id);
    }
  }

  private cargarHistorial(clienteId: number): void {
    this.cargando.set(true);

    this.clienteService.getCliente(clienteId).subscribe({
      next: (data) => this.cliente.set(data),
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

    this.reservacionService.getReservacionesPorCliente(clienteId).subscribe({
      next: (data) => {
        this.reservaciones.set(data);
        this.cargando.set(false);
      },
      error: (err) => {
        this.mensajeError.set(err.error?.error || "Error al cargar el historial de viajes.");
        this.cargando.set(false);
      }
    });
  }

}
