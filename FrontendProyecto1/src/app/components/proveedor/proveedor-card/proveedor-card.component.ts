import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ProveedorResponse } from '../../../models/proveedor/ProveedorResponse';
import { EnumProveedor } from '../../../models/proveedor/EnumProveedor';

@Component({
  selector: 'app-proveedor-card-component',
  imports: [CommonModule, RouterLink],
  templateUrl: './proveedor-card.component.html'
})
export class ProveedorCardComponent {
  @Input() proveedor!: ProveedorResponse;
  @Output() onDelete = new EventEmitter<ProveedorResponse>();

  getIconoPorTipo(tipo: EnumProveedor): string {
    switch (tipo) {
      case EnumProveedor.Aerolinea: return 'bi-airplane-engines-fill text-info';
      case EnumProveedor.Hotel: return 'bi-building-fill text-warning';
      case EnumProveedor.Tour: return 'bi-map-fill text-success';
      case EnumProveedor.Traslado: return 'bi-bus-front-fill text-primary';
      default: return 'bi-briefcase-fill text-secondary';
    }
  }
}
