import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PaqueteCardComponent } from '../../../components/paqueteTuristico/paquete-card/paquete-card.component';
import { PaqueteTuristicoService } from '../../../services/paqueteTuristico/paquete-turistico.service';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { ConfirmationModalPaqueteComponent } from "../../../components/confirmation-modal/confirmation-modal-paquete/confirmation-modal-paquete.component";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-paquete-page.component',
  imports: [CommonModule, FormsModule, RouterLink, PaqueteCardComponent, ConfirmationModalPaqueteComponent],
  templateUrl: './paquete-page.component.html'
})
export class PaquetePageComponent implements OnInit {
  private paqueteService = inject(PaqueteTuristicoService);
  private router = inject(Router);
  paqueteSeleccionado = signal<PaqueteTuristicoResponse>(null!);
  eliminar = signal<boolean>(false);
  paquetes = signal<PaqueteTuristicoResponse[]>([]);
  cargando = signal(true);
  mensajeError = signal<string>('');
  concidencia = signal<string>('');

  ngOnInit(): void {
    this.cargarPaquetes();
  }

  private cargarPaquetes(): void {
    this.cargando.set(true);
    this.paqueteService.getPaquetes().subscribe({
      next: (paquete) => {
        this.paquetes.set(paquete);
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  cambiarEstado(id: number): void {
    this.paqueteService.cambiarEstado(id).subscribe({
      next: () => this.cargarPaquetes(),
      error: (error) => this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.")
    });
  }

  seleccionarPaquete(paquete: PaqueteTuristicoResponse): void {
    this.paqueteSeleccionado.set(paquete);
    this.eliminar.set(true);
  }

  eliminarPaquete(): void {
    if (!this.eliminar()) return;

    this.paqueteService.eliminarPaquete(this.paqueteSeleccionado().paqueteId).subscribe({
      next: () => {
        this.cargarPaquetes();
        this.eliminar.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.eliminar.set(false);
      }
    });
  }

  gestionarServicios(id: number): void {
    this.router.navigate(['/paquete', id, 'servicio']);
  }

  buscar(): void {
    if (!this.concidencia().trim()) {
      this.cargarPaquetes();
      return;
    }

    this.cargando.set(true);
    this.paqueteService.getPaquete(this.concidencia()).subscribe({
      next: (paquetes: PaqueteTuristicoResponse[] | PaqueteTuristicoResponse) => {
        if (Array.isArray(paquetes)) {
          this.paquetes.set(paquetes);
        } else {
          this.paquetes.set([paquetes]);
        }
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

}
