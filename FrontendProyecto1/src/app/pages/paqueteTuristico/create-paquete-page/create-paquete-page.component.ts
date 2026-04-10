import { Component, inject, OnInit, signal } from '@angular/core';
import { PaqueteFormComponent } from '../../../components/paqueteTuristico/paquete-form/paquete-form.component';
import { PaqueteTuristicoService } from '../../../services/paqueteTuristico/paquete-turistico.service';
import { DestinoService } from '../../../services/destino/destino.service';
import { Router } from '@angular/router';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { PaqueteTuristicoRequest } from '../../../models/paqueteTuristico/PaqueteTuristicoRequest';

@Component({
  selector: 'app-create-paquete-page.component',
  imports: [PaqueteFormComponent],
  templateUrl: './create-paquete-page.component.html'
})
export class CreatePaquetePageComponent implements OnInit {
  private paqueteService = inject(PaqueteTuristicoService);
  private destinoService = inject(DestinoService);
  private router = inject(Router);

  mensajeError = signal<string>('');
  destinos = signal<DestinoResponse[]>([]);
  cargandoDestinos = signal<boolean>(true);

  ngOnInit(): void {
    this.destinoService.getDestinos().subscribe({
      next: (destinos) => {
        this.destinos.set(destinos);
        this.cargandoDestinos.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargandoDestinos.set(false);
      }
    });
  }

  crearPaquete(paquete: PaqueteTuristicoRequest): void {
    this.paqueteService.crearPaquete(paquete).subscribe({
      next: () => this.router.navigate(['/paquete']),
      error: (error) => this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.")
    });
  }

}
