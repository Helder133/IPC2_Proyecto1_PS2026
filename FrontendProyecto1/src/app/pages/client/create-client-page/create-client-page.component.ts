import { Component, inject, signal } from '@angular/core';
import { ClienteFormComponent } from '../../../components/client/cliente-form/cliente-form.component';
import { Router } from '@angular/router';
import { ClientService } from '../../../services/client/client.service';
import { ClienteRequest } from '../../../models/cliente/clienteRequest';

@Component({
  selector: 'app-create-client-page.component',
  imports: [ClienteFormComponent],
  templateUrl: './create-client-page.component.html'
})
export class CreateClientPageComponent {
  private clienteService = inject(ClientService);
  private router = inject(Router);
  mensajeError = signal<string>('');

  crearCliente(cliente: ClienteRequest): void {
    this.clienteService.crearCliente(cliente).subscribe({
      next: () => {
        this.router.navigate(['/cliente']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

}
