import { Component, inject, signal } from '@angular/core';
import { ProveedorFormComponent } from '../../../components/proveedor/proveedor-form/proveedor-form.component';
import { ProveedorService } from '../../../services/proveedor/proveedor.service';
import { Router } from '@angular/router';
import { ProveedorRequest } from '../../../models/proveedor/ProveedorRequest';

@Component({
  selector: 'app-create-proveedor-page.component',
  imports: [ProveedorFormComponent],
  templateUrl: './create-proveedor-page.component.html'
})
export class CreateProveedorPageComponent {
  private proveedorService = inject(ProveedorService);
  private router = inject(Router);
  mensajeError = signal<string>('');

  crearProveedor(proveedorRequest: ProveedorRequest): void {
    this.proveedorService.crearProveedor(proveedorRequest).subscribe({
      next: () => {
        this.router.navigate(['/proveedor']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }
}
