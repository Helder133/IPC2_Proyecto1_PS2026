import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PaqueteTuristicoResponse } from '../../models/paqueteTuristico/PaqueteTuristicoResponse';
import { PaqueteTuristicoRequest } from '../../models/paqueteTuristico/PaqueteTuristicoRequest';
import { PaqueteTuristicoUpdate } from '../../models/paqueteTuristico/PaqueteTuristicoUpdate';

@Injectable({
  providedIn: 'root',
})
export class PaqueteTuristicoService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}paquete-turistico`;

  getPaquetes(): Observable<PaqueteTuristicoResponse[]> {
    return this.httpClient.get<PaqueteTuristicoResponse[]>(this.apiUrl);
  }

  getPaquete(parametro: string | number): Observable<PaqueteTuristicoResponse | PaqueteTuristicoResponse[]> {
    return this.httpClient.get<PaqueteTuristicoResponse | PaqueteTuristicoResponse[]>(`${this.apiUrl}/${parametro}`);
  }

  crearPaquete(paquete: PaqueteTuristicoRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, paquete);
  }

  actualizarPaquete(paquete: PaqueteTuristicoUpdate): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar`, paquete);
  }

  cambiarEstado(id: number): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar/estado/${id}`, null);
  }

  eliminarPaquete(id: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/${id}`);
  }

}
