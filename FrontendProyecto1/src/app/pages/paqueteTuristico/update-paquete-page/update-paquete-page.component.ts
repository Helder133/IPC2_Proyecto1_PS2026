import { Component, inject, OnInit, signal } from '@angular/core';
import { DestinoService } from '../../../services/destino/destino.service';
import { ActivatedRoute, Router } from '@angular/router';
import { PaqueteTuristicoService } from '../../../services/paqueteTuristico/paquete-turistico.service';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { PaqueteFormComponent } from '../../../components/paqueteTuristico/paquete-form/paquete-form.component';
import { PaqueteTuristicoRequest } from '../../../models/paqueteTuristico/PaqueteTuristicoRequest';
import { PaqueteTuristicoUpdate } from '../../../models/paqueteTuristico/PaqueteTuristicoUpdate';

@Component({
  selector: 'app-update-paquete-page.component',
  imports: [PaqueteFormComponent],
  templateUrl: './update-paquete-page.component.html'
})
export class UpdatePaquetePageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private paqueteService = inject(PaqueteTuristicoService);
  private destinoService = inject(DestinoService);
  private paqueteId = signal<number>(null!);

  mensajeError = signal<string>('');
  paqueteOriginal = signal<PaqueteTuristicoResponse>(null!);
  listaDestinos = signal<DestinoResponse[]>([]);

  ngOnInit(): void {
    this.paqueteId.set(this.route.snapshot.params['id']);
    if (this.paqueteId() === null) {
      this.router.navigate(['/paquete']);
      return;
    }

    this.paqueteService.getPaquete(this.paqueteId()).subscribe({
      next: (paquete) => {
        this.paqueteOriginal.set(paquete as PaqueteTuristicoResponse);
      },
      error: (error) => {
        this.router.navigate(['/paquete']);
      }
    });

    this.destinoService.getDestinos().subscribe({
      next: (destinos) => {
        this.listaDestinos.set(destinos);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

  actualizarPaquete(paquete: PaqueteTuristicoRequest): void {
    if (this.paqueteId() === null) return;

    const paqueteUpdate: PaqueteTuristicoUpdate = {
      ...paquete,
      paqueteId: this.paqueteId()
    }

    this.paqueteService.actualizarPaquete(paqueteUpdate).subscribe({
      next: () => {
        this.router.navigate(['/paquete']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

  }
}
