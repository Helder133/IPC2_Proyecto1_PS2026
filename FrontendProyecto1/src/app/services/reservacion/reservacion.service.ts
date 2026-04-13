import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservacionResponse } from '../../models/reservacion/ReservacionResponse';
import { ReservacionRequest } from '../../models/reservacion/ReservacionRequest';
import { ReservacionUpdate } from '../../models/reservacion/ReservacionUpdate';
import { ReservacionClienteRequest } from '../../models/reservacion/client/ReservacionClienteRequest';
import { ClienteResponse } from '../../models/cliente/ClienteResponse';

@Injectable({
  providedIn: 'root',
})
export class ReservacionService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}reservacion`;

  getAllReservaciones(): Observable<ReservacionResponse[]> {
    return this.httpClient.get<ReservacionResponse[]>(this.apiUrl);
  }

  getReservacion(id: number): Observable<ReservacionResponse> {
    return this.httpClient.get<ReservacionResponse>(`${this.apiUrl}/${id}`);
  }

  getReservacionesPorCliente(clienteId: number): Observable<ReservacionResponse[]> {
    return this.httpClient.get<ReservacionResponse[]>(`${this.apiUrl}/cliente/${clienteId}`);
  }

  getReservacionesPorUsuario(usuarioId: number): Observable<ReservacionResponse[]> {
    return this.httpClient.get<ReservacionResponse[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  crearReservacion(reservacion: ReservacionRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, reservacion);
  }

  actualizarReservacion(reservacion: ReservacionUpdate): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar`, reservacion);
  }

  getPasajerosDeReservacion(reservacionId: number): Observable<ClienteResponse[]> {
    return this.httpClient.get<ClienteResponse[]>(`${this.apiUrl}/${reservacionId}/pasajeros`);
  }

  cancelarReservacion(id: number): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar/cancelar/${id}`, {});
  }

  completarReservacion(id: number): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar/completada/${id}`, {});
  }

  eliminarReservacion(id: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/${id}`);
  }

  agregarClienteAReservacion(datos: ReservacionClienteRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar/reservacion/cliente`, datos);
  }

  eliminarClienteDeReservacion(reservacionId: number, clienteId: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/reservacion/${reservacionId}/cliente/${clienteId}`);
  }

}
