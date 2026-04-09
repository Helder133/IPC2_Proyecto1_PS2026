import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DestinoResponse } from '../../models/destino/DestinoResponse';
import { DestinoRequest } from '../../models/destino/DestinoRequest';
import { DestinoUpdate } from '../../models/destino/DestinoUpdate';

@Injectable({
  providedIn: 'root',
})
export class DestinoService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}destino`;

  getDestinos(): Observable<DestinoResponse[]> {
    return this.httpClient.get<DestinoResponse[]>(this.apiUrl);
  }

  getDestino(parametro: string | number): Observable<DestinoResponse | DestinoResponse[]> {
    return this.httpClient.get<DestinoResponse | DestinoResponse[]>(`${this.apiUrl}/${parametro}`);
  }

  crearDestino(destino: DestinoRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, destino);
  }

  actualizarDestino(destino: DestinoUpdate): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar`, destino);
  }

  eliminarDestino(id: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/${id}`);
  }
}
