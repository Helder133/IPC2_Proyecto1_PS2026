import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ClienteCardComponent } from '../../../components/client/cliente-card/cliente-card.component';
import { ClientService } from '../../../services/client/client.service';
import { ClienteResponse } from '../../../models/cliente/ClienteResponse';

@Component({
  selector: 'app-client-page.component',
  imports: [CommonModule, RouterLink, FormsModule, ClienteCardComponent],
  templateUrl: './client-page.component.html'
})
export class ClientPageComponent implements OnInit {
  
  private clienteService = inject(ClientService);

  clientes = signal<ClienteResponse[]>([]);
  cargando = signal<boolean>(false);

  concidencia = signal<string>('');
  buscando = signal<boolean>(false);

  mensajeError = signal<string>('');

  ngOnInit(): void {
    this.cargarTodos();
  }

  cargarTodos(): void {
    this.cargando.set(true);
    this.clienteService.getClientes().subscribe({
      next: (data: ClienteResponse[]) => {
        this.clientes.set(data);
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  buscarClientes(): void {
    if (this.concidencia().trim() === '') {
      this.cargarTodos();
      return;
    }

    this.buscando.set(true);
    this.clienteService.buscarClientes(this.concidencia()).subscribe({
      next: (data) => {
        this.clientes.set(data);
        this.buscando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.buscando.set(false);
      }
    });
  }

  limpiarBusqueda(): void {
    this.concidencia.set('');
    this.cargarTodos();
  }

}
