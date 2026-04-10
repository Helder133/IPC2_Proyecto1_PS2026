import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ServicioCardComponent } from '../../../components/servicioPaquete/servicio-card/servicio-card.component';
import { ServicioFormComponent } from '../../../components/servicioPaquete/servicio-form/servicio-form.component';
import { ProveedorService } from '../../../services/proveedor/proveedor.service';
import { PaqueteTuristicoService } from '../../../services/paqueteTuristico/paquete-turistico.service';
import { ServicioPaqueteService } from '../../../services/servicioPaquete/servicio-paquete.service';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';
import { PaqueteTuristicoResponse } from '../../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { ServicioPaqueteResponse } from '../../../models/servicioPaquete/ServicioPaqueteResponse';
import { ServicioPaqueteRequest } from '../../../models/servicioPaquete/ServicioPaqueteRequest';
import { ServicioPaqueteUpdate } from '../../../models/servicioPaquete/ServicioPaqueteUpdate';
import { ConfirmationModalServicioComponent } from "../../../components/confirmation-modal/confirmation-modal-servicio/confirmation-modal-servicio.component";

@Component({
  selector: 'app-servicio-page.component',
  imports: [CommonModule, RouterLink, ServicioCardComponent, ServicioFormComponent, ConfirmationModalServicioComponent],
  templateUrl: './servicio-page.component.html'
})
export class ServicioPageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private servicioPaqueteService = inject(ServicioPaqueteService);
  private paqueteService = inject(PaqueteTuristicoService);
  private proveedorService = inject(ProveedorService);

  paqueteActual = signal<PaqueteTuristicoResponse>(null!);
  listaServicios = signal<ServicioPaqueteResponse[]>([]);
  listaProveedores = signal<ProveedorResponse[]>([]);

  private paqueteId = signal<number>(null!);
  cargando = signal<boolean>(true);
  mostrarFormulario = signal<boolean>(false);
  servicioEnEdicion = signal<ServicioPaqueteResponse>(null!);

  servicioAEliminar = signal<ServicioPaqueteResponse>(null!);
  eliminar = signal<boolean>(false);

  mensajeError = signal<string>('');

  ngOnInit(): void {
    this.paqueteId.set(this.route.snapshot.params['id']);
    if (this.paqueteId()) {
      this.cargarDatosIniciales(this.paqueteId());
    }
  }

  private cargarDatosIniciales(paqueteId: number): void {
    this.cargando.set(true);

    this.paqueteService.getPaquete(paqueteId).subscribe({
      next: (paquete) => {
        this.paqueteActual.set(paquete as PaqueteTuristicoResponse);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

    this.proveedorService.getProveedores().subscribe({
      next: (proveedores: ProveedorResponse[]) => {
        this.listaProveedores.set(proveedores);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

    this.recargarServicios(paqueteId);

  }

  recargarServicios(paqueteId: number): void {
    this.servicioPaqueteService.getServiciosPorPaquete(paqueteId).subscribe({
      next: (servicios) => {
        this.listaServicios.set(servicios);
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  abrirNuevoServicio(): void {
    this.servicioEnEdicion.set(null!);
    this.mostrarFormulario.set(true);
  }

  abrirEditarServicio(servicio: ServicioPaqueteResponse): void {
    this.servicioEnEdicion.set(servicio);
    this.mostrarFormulario.set(true);
  }

  cerrarFormulario(): void {
    this.mostrarFormulario.set(false);
    this.servicioEnEdicion.set(null!);
  }

  guardarServicio(datos: ServicioPaqueteRequest): void {
    if (this.servicioEnEdicion()) {
      // Actualizar
      this.servicioPaqueteService.actualizarServicio(datos as ServicioPaqueteUpdate).subscribe({
        next: () => {
          this.cerrarFormulario();
          this.recargarServicios(this.paqueteActual()!.paqueteId);
        },
        error: (err) => this.mensajeError.set(err.error?.error || 'Error al actualizar.')
      });
    } else {
      // Crear Nuevo
      this.servicioPaqueteService.crearServicio(datos).subscribe({
        next: () => {
          this.cerrarFormulario();
          this.recargarServicios(this.paqueteActual()!.paqueteId);
        },
        error: (err) => this.mensajeError.set(err.error?.error || 'Error al agregar servicio.')
      });
    }
  }

  seleccionarServicio(servicio: ServicioPaqueteResponse): void {
    this.servicioAEliminar.set(servicio);
    this.eliminar.set(true);
  }

  eliminarServicio(): void {
    this.servicioPaqueteService.eliminarServicio(this.servicioAEliminar().paqueteId, this.servicioAEliminar().proveedorId).subscribe({
      next: () => {
        this.cargarDatosIniciales(this.paqueteId());
      },
      error: (err) => this.mensajeError.set(err.error?.error || 'Error al eliminar.')
    });

  }

}
