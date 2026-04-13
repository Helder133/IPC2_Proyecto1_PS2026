import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReservacionConfirmadaResponse } from '../../models/reporete/ReservacionConfirmadaResponse';
import { ReservacionCanceladaResponse } from '../../models/reporete/ReservacionCanceladaResponse';
import { GananciaResponse } from '../../models/reporete/GananciaResponse';
import { ReporteMejorAgenteResponse } from '../../models/reporete/mejorAgente/ReporteMejorAgenteResponse';
import { AgenteConMasGananciaResponse } from '../../models/reporete/AgenteConMasGananciaResponse';
import { PaqueteVendidoResponse } from '../../models/reporete/paqueteMasYMenosVendido/PaqueteVendidoResponse';
import { DestinoConcurridoResponse } from '../../models/reporete/DestinoConcurridoResponse';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private apiUrl = `${this.restConstants.getApiUrl()}reporte`;

  getVentas(inicio: string, fin: string): Observable<ReservacionConfirmadaResponse[]> { 
    return this.httpClient.get<ReservacionConfirmadaResponse[]>(`${this.apiUrl}/${inicio}/reservacion/confirmada/${fin}`); 
  }
  getCancelaciones(inicio: string, fin: string): Observable<ReservacionCanceladaResponse[]> { 
    return this.httpClient.get<ReservacionCanceladaResponse[]>(`${this.apiUrl}/${inicio}/reservacion/cancelada/${fin}`); 
  }
  getGanancias(inicio: string, fin: string): Observable<GananciaResponse> { 
    return this.httpClient.get<GananciaResponse>(`${this.apiUrl}/${inicio}/ganancia/${fin}`); 
  }
  getMejorAgente(inicio: string, fin: string): Observable<ReporteMejorAgenteResponse> { 
    return this.httpClient.get<ReporteMejorAgenteResponse>(`${this.apiUrl}/${inicio}/usuario/${fin}`); 
  }
  getAgenteMasGanancias(inicio: string, fin: string): Observable<AgenteConMasGananciaResponse> { 
    return this.httpClient.get<AgenteConMasGananciaResponse>(`${this.apiUrl}/${inicio}/usuario/ganancias/${fin}`); 
  }
  getPaqueteMasVendido(inicio: string, fin: string): Observable<PaqueteVendidoResponse> { 
    return this.httpClient.get<PaqueteVendidoResponse>(`${this.apiUrl}/${inicio}/paquete/mas-vendido/${fin}`); 
  }
  getPaqueteMenosVendido(inicio: string, fin: string): Observable<PaqueteVendidoResponse> { 
    return this.httpClient.get<PaqueteVendidoResponse>(`${this.apiUrl}/${inicio}/paquete/menos-vendido/${fin}`); 
  }
  getDestinos(inicio: string, fin: string): Observable<DestinoConcurridoResponse[]> { 
    return this.httpClient.get<DestinoConcurridoResponse[]>(`${this.apiUrl}/${inicio}/destino/${fin}`); 
  }

}
