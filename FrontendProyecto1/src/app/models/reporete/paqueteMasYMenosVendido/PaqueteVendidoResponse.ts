import { DetalleReservacionPaqueteResponse } from "./DetalleReservacionPaqueteResponse";

export interface PaqueteVendidoResponse { 
    paqueteId: number; 
    nombrePaquete: string; 
    totalReservacion: number; 
    reservaciones: DetalleReservacionPaqueteResponse[]; }