import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { ProveedorRequest } from '../../models/proveedor/ProveedorRequest';
import { Observable } from 'rxjs';
import { ProveedorResponse } from '../../models/proveedor/ProveedorResponse';
import { ProveedorUpdate } from '../../models/proveedor/ProveedorUpdate';

@Injectable({
  providedIn: 'root',
})
export class ProveedorService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}proveedor`;

  crearProveedor(proveedor: ProveedorRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, proveedor);
  }

  getProveedores(): Observable<ProveedorResponse[]> {
    return this.httpClient.get<ProveedorResponse[]>(this.apiUrl);
  }

  getProveedor(parametro: string | number): Observable<ProveedorResponse | ProveedorResponse[]> {
    return this.httpClient.get<ProveedorResponse | ProveedorResponse[]>(`${this.apiUrl}/${parametro}`);
  }

  actualizarProveedor(proveedor: ProveedorUpdate): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar`, proveedor);
  }

  eliminarProveedor(id: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/${id}`);
  }
}
