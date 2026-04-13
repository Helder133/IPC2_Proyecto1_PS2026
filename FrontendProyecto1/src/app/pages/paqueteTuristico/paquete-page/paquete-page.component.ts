import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { PaqueteCardComponent } from '../../../components/paqueteTuristico/paquete-card/paquete-card.component';
import { PaqueteTuristicoService } from '../../../services/paqueteTuristico/paquete-turistico.service';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { ConfirmationModalPaqueteComponent } from "../../../components/confirmation-modal/confirmation-modal-paquete/confirmation-modal-paquete.component";
import { FormsModule } from '@angular/forms';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { DestinoService } from '../../../services/destino/destino.service';

@Component({
  selector: 'app-paquete-page.component',
  imports: [CommonModule, FormsModule, RouterLink, FormsModule, PaqueteCardComponent, ConfirmationModalPaqueteComponent],
  templateUrl: './paquete-page.component.html'
})
export class PaquetePageComponent implements OnInit {
  private paqueteService = inject(PaqueteTuristicoService);
  private destinoService = inject(DestinoService);
  private router = inject(Router);
  listaDestinos = signal<DestinoResponse[]>([]);

  paqueteSeleccionado = signal<PaqueteTuristicoResponse>(null!);
  eliminar = signal<boolean>(false);
  
  paquetesOriguinales = signal<PaqueteTuristicoResponse[]>([]);
  cargando = signal(true);
  mensajeError = signal<string>('');

  concidencia = signal<string>('');
  filtroDestino = signal<string>('');

  paquetesFiltrados = computed(() => {
    let filtrados = this.paquetesOriguinales();

    if (this.concidencia()) {
      const busqueda = this.concidencia().toLowerCase();
      filtrados = filtrados.filter(paquete => paquete.nombre.toLowerCase().includes(busqueda));
    }

    if (this.filtroDestino()) {
      filtrados = filtrados.filter(paquete => paquete.destinoId === Number(this.filtroDestino()));
    }

    return filtrados;
  });

  ngOnInit(): void {
    this.cargarPaquetes();
  }

  private cargarPaquetes(): void {
    this.cargando.set(true);

    this.destinoService.getDestinos().subscribe({
      next: (destinos) => {
        this.listaDestinos.set(destinos);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

    this.paqueteService.getPaquetes().subscribe({
      next: (paquete) => {
        this.paquetesOriguinales.set(paquete);
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  limpiarFiltros(): void {
    this.filtroDestino.set('');
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

  /*buscar(): void {
    if (!this.concidencia().trim()) {
      this.cargarPaquetes();
      return;
    }

    this.cargando.set(true);
    this.paqueteService.getPaquete(this.concidencia()).subscribe({
      next: (paquetes: PaqueteTuristicoResponse[] | PaqueteTuristicoResponse) => {
        if (Array.isArray(paquetes)) {
          this.paquetesOriguinales.set(paquetes);
        } else {
          this.paquetesOriguinales.set([paquetes]);
        }
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }*/

}
