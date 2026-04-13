import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClienteResponse } from '../../models/cliente/ClienteResponse';
import { ClienteRequest } from '../../models/cliente/clienteRequest';
import { ClienteUpdate } from '../../models/cliente/ClienteUpdate';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}cliente`;

  getClientes(): Observable<ClienteResponse[]> {
    return this.httpClient.get<ClienteResponse[]>(this.apiUrl);
  }

  getCliente(id: number): Observable<ClienteResponse> {
    return this.httpClient.get<ClienteResponse>(`${this.apiUrl}/${id}`);
  }

  buscarClientes(termino: string): Observable<ClienteResponse[]> {
    return this.httpClient.get<ClienteResponse[]>(`${this.apiUrl}/coincidencia/${termino}`);
  }

  crearCliente(cliente: ClienteRequest): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/insertar`, cliente);
  }

  actualizarCliente(cliente: ClienteUpdate): Observable<any> {
    return this.httpClient.put(`${this.apiUrl}/actualizar`, cliente);
  }

  eliminarCliente(id: number): Observable<any> {
    return this.httpClient.delete(`${this.apiUrl}/eliminar/${id}`);
  }

}
