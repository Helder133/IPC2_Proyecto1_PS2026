import { Component, inject, OnInit, signal } from '@angular/core';
import { ClientService } from '../../../services/client/client.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteFormComponent } from '../../../components/client/cliente-form/cliente-form.component';
import { ClienteResponse } from '../../../models/cliente/ClienteResponse';
import { ClienteRequest } from '../../../models/cliente/clienteRequest';
import { ClienteUpdate } from '../../../models/cliente/ClienteUpdate';

@Component({
  selector: 'app-update-client-page.component',
  imports: [ClienteFormComponent],
  templateUrl: './update-client-page.component.html'
})
export class UpdateClientPageComponent implements OnInit {

  private clienteService = inject(ClientService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private cliente_id = signal<number>(null!);

  clienteOriginal = signal<ClienteResponse>(null!);
  mensajeError = signal<string>('');

  ngOnInit(): void {
    this.cliente_id.set(this.route.snapshot.params['id']);
    this.clienteService.getCliente(this.cliente_id()).subscribe({
      next: (cliente) => {
        this.clienteOriginal.set(cliente as ClienteResponse);
      },
      error: (error) => {
        this.router.navigate(['/cliente']);
      }
    });
  }

  actualizarCliente(clienteRequest: ClienteRequest): void {
    if (this.cliente_id() === null) return;
    
    const clienteUpdate: ClienteUpdate = {
      ...clienteRequest,
      cliente_id: this.cliente_id()
    };

    this.clienteService.actualizarCliente(clienteUpdate).subscribe({
      next: () => {
        this.router.navigate(['/cliente']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

  }

}
