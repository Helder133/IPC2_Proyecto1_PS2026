import { DetalleReservacionResponse } from "./DetalleReservacionResponse";

export interface ReporteMejorAgenteResponse { 
    nombreAgente: string; 
    totalVendido: number; 
    reservaciones: DetalleReservacionResponse[]; 
}