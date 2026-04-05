import { Component, inject, signal } from '@angular/core';
import { ArchivoService } from '../../services/archivo/archivo.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-carga-archivo-page',
  imports: [CommonModule],
  templateUrl: './carga-archivo-page.component.html',
})
export class CargaArchivoPageComponent {
  private archivoService = inject(ArchivoService);

  archivoSeleccionado = signal<File | null>(null);
  cargando = signal(false);

  mensajeExito = signal('');
  mensajeErrorGeneral = signal('');
  resumenErrores = signal<string[]>([]);

  seleccionarArchivo(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.archivoSeleccionado.set(input.files[0]);
      this.limpiarMensajes();
    }
  }

  limpiarMensajes(): void {
    this.mensajeExito.set('');
    this.mensajeErrorGeneral.set('');
    this.resumenErrores.set([]);
  }

  subirArchivo(): void {
    if (!this.archivoSeleccionado()) {
      this.mensajeErrorGeneral.set('Por favor, seleccione un archivo antes de subirlo.');
      return;
    }

    this.cargando.set(true);
    this.limpiarMensajes();

    const formData = new FormData();
    formData.append('file', this.archivoSeleccionado()!);

    this.archivoService.uploadFile(formData).subscribe({
      next: (res) => {
        this.cargando.set(false);
        this.archivoSeleccionado.set(null);
        this.mensajeExito.set('¡El archivo se procesó correctamente! Todos los registros fueron guardados.');
      },
      error: (err) => {
        this.cargando.set(false);
        if (Array.isArray(err.error)) {
          this.resumenErrores.set(err.error);
        } 
        else if (err.error && err.error.error) {
          this.mensajeErrorGeneral.set(err.error.error);
        }
        else {
          this.mensajeErrorGeneral.set('Ocurrió un error inesperado al conectar con el servidor.');
        }
      }
    });
  }
}
