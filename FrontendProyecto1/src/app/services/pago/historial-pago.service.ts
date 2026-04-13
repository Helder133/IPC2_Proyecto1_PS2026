import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { HistorialPagoResponse } from '../../models/pago/HistorialPagoResponse';
import { Observable } from 'rxjs';
import { HistorialPagoRequest } from '../../models/pago/HistorialPagoRequest';

@Injectable({
  providedIn: 'root',
})
export class HistorialPagoService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}historial-pago`;

  insertar(pago: HistorialPagoRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, pago);
  }

  getHistorialPagoReservacion(reservacionId: number): Observable<HistorialPagoResponse[]> {
    return this.httpClient.get<HistorialPagoResponse[]>(`${this.apiUrl}/${reservacionId}`);
  }

}
