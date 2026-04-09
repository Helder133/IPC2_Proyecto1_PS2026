import { Component, inject, OnInit, signal } from '@angular/core';
import { DestinoService } from '../../../services/destino/destino.service';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DestinoCardComponent } from '../../../components/destino/destino-card/destino-card.component';
import { RouterLink } from '@angular/router';
import { ConfirmationModalDestinoComponent } from '../../../components/confirmation-modal/confirmation-modal-destino/confirmation-modal-destino.component';

@Component({
  selector: 'app-destino-page-component',
  imports: [CommonModule, FormsModule, DestinoCardComponent, ConfirmationModalDestinoComponent, RouterLink],
  templateUrl: './destino-page.component.html'
})
export class DestinoPageComponent implements OnInit {
  private destinoService = inject(DestinoService);

  destinos = signal<DestinoResponse[]>([]);
  concidencia = signal<string>('');
  mensajeError = signal<string>('');
  eliminar = signal<boolean>(false);
  cargando = signal<boolean>(true);
  destinoSeleccionado = signal<DestinoResponse>(null!);

  ngOnInit(): void {
    this.getDestinos();
  }

  private getDestinos(): void {
    this.cargando.set(true);
    this.destinoService.getDestinos().subscribe({
      next: (destinos: DestinoResponse[]) => {
        this.destinos.set(destinos);
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  buscar(): void {
    if (!this.concidencia().trim()) {
      this.getDestinos();
      return;
    }

    this.cargando.set(true);
    this.destinoService.getDestino(this.concidencia()).subscribe({
      next: (destinos: DestinoResponse[] | DestinoResponse) => {
        if (Array.isArray(destinos)) {
          this.destinos.set(destinos);
        } else {
          this.destinos.set([destinos]);
        }
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });

  }

  seleccionarDestino(destino: DestinoResponse): void {
    this.destinoSeleccionado.set(destino);
    this.eliminar.set(true);
  }

  eliminarDestino(): void {
    if (!this.eliminar()) return;

    this.destinoService.eliminarDestino(this.destinoSeleccionado().destinoId).subscribe({
      next: () => {
        this.getDestinos();
        this.eliminar.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.eliminar.set(false);
      }
    });
  }

}
