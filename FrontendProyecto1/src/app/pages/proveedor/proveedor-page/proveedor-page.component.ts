import { Component, inject, OnInit, signal } from '@angular/core';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';
import { ProveedorService } from '../../../services/proveedor/proveedor.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProveedorCardComponent } from '../../../components/proveedor/proveedor-card/proveedor-card.component';
import { ConfirmationModalProveedorComponent } from '../../../components/confirmation-modal/confirmation-modal-proveedor/confirmation-modal-proveedor.component';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-proveedor-page.component',
  imports: [CommonModule, FormsModule, ProveedorCardComponent, ConfirmationModalProveedorComponent, RouterLink],
  templateUrl: './proveedor-page.component.html'
})
export class ProveedorPageComponent implements OnInit {
  private proveedorService = inject(ProveedorService);

  proveedores = signal<ProveedorResponse[]>([]);
  concidencia = signal<string>('');
  mensajeError = signal<string>('')
  eliminar = signal<boolean>(false);
  cargando = signal<boolean>(true);
  proveedorSeleccionado = signal<ProveedorResponse>(null!);

  ngOnInit(): void {
    this.getProveedores();
  }

  private getProveedores(): void {
    this.cargando.set(true);
    this.proveedorService.getProveedores().subscribe({
      next: (proveedores: ProveedorResponse[]) => {
        this.proveedores.set(proveedores);
        console.log(proveedores);
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
      this.getProveedores();
      return;
    }
    
    this.cargando.set(true);
    this.proveedorService.getProveedor(this.concidencia()).subscribe({
      next: (proveedores: ProveedorResponse[] | ProveedorResponse) => {
        if (Array.isArray(proveedores)) {
          this.proveedores.set(proveedores);
        } else {
          this.proveedores.set([proveedores]);
        }
        this.cargando.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.cargando.set(false);
      }
    });
  }

  seleccionarProveedor(proveedor: ProveedorResponse): void {
    this.proveedorSeleccionado.set(proveedor);
    this.eliminar.set(true);
  }

  eliminarProveedor(): void {
    if (!this.eliminar()) return;

    this.proveedorService.eliminarProveedor(this.proveedorSeleccionado().proveedorId).subscribe({
      next: () => {
        this.getProveedores();
        this.eliminar.set(false);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
        this.eliminar.set(false);
      }
    });
  }

}
