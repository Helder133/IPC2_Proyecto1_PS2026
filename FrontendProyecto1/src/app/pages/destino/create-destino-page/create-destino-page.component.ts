import { Component, inject, signal } from '@angular/core';
import { DestinoFormComponent } from '../../../components/destino/destino-form/destino-form.component';
import { DestinoService } from '../../../services/destino/destino.service';
import { Router } from '@angular/router';
import { DestinoRequest } from '../../../models/destino/DestinoRequest';

@Component({
  selector: 'app-create-destino-page-component',
  imports: [DestinoFormComponent],
  templateUrl: './create-destino-page.component.html'
})
export class CreateDestinoPageComponent {
  private destinoService = inject(DestinoService);
  private router = inject(Router);
  mensajeError = signal<string>('');

  crearDestino(destinoRequest: DestinoRequest): void {
    this.destinoService.crearDestino(destinoRequest).subscribe({
      next: () => {
        this.router.navigate(['/destino']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

}
