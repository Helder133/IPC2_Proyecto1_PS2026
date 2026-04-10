import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServicioPaqueteResponse } from '../../models/servicioPaquete/ServicioPaqueteResponse';
import { ServicioPaqueteRequest } from '../../models/servicioPaquete/ServicioPaqueteRequest';
import { ServicioPaqueteUpdate } from '../../models/servicioPaquete/ServicioPaqueteUpdate';

@Injectable({
  providedIn: 'root',
})
export class ServicioPaqueteService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}servicio-paquete`;

  getServiciosPorPaquete(paqueteId: number): Observable<ServicioPaqueteResponse[]> {
    return this.httpClient.get<ServicioPaqueteResponse[]>(`${this.apiUrl}/${paqueteId}`);
  }

  crearServicio(servicio: ServicioPaqueteRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, servicio);
  }

  actualizarServicio(servicio: ServicioPaqueteUpdate): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar`, servicio);
  }

  eliminarServicio(paqueteId: number, proveedorId: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/${paqueteId}/${proveedorId}`);
  }

}
