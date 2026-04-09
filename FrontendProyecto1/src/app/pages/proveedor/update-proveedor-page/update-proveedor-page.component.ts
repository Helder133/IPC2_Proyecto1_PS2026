import { Component, inject, OnInit, signal } from '@angular/core';
import { ProveedorService } from '../../../services/proveedor/proveedor.service';
import { ProveedorFormComponent } from '../../../components/proveedor/proveedor-form/proveedor-form.component';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';
import { ActivatedRoute, Router } from '@angular/router';
import { ProveedorUpdate } from '../../../models/proveedor/ProveedorUpdate';
import { ProveedorRequest } from '../../../models/proveedor/ProveedorRequest';

@Component({
  selector: 'app-update-proveedor-page.component',
  imports: [ProveedorFormComponent],
  templateUrl: './update-proveedor-page.component.html'
})
export class UpdateProveedorPageComponent implements OnInit {

  private proveedorService = inject(ProveedorService);
  private proveedorId = signal<number>(null!);
  proveedorOriginal = signal<ProveedorResponse>(null!);
  mensajeError = signal<string>('');

  private router = inject(Router);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.proveedorId.set(this.route.snapshot.params['id']);
    this.proveedorService.getProveedor(this.proveedorId()).subscribe({
      next: (proveedor) => {
        this.proveedorOriginal.set(proveedor as ProveedorResponse);
      },
      error: (error) => {
        this.router.navigate(['/proveedor']);
      }
    });
  }

  actualizarProveedor(proveedorRequest: ProveedorRequest): void {
    if (this.proveedorId() === null) return;

    const proveedorUpdate: ProveedorUpdate = {
      ...proveedorRequest,
      proveedorId: this.proveedorId()
    };

    this.proveedorService.actualizarProveedor(proveedorUpdate).subscribe({
      next: () => {
        this.router.navigate(['/proveedor']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

}
