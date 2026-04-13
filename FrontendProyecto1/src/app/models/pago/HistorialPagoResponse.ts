import { EnumPago } from "./EnumPago";

export interface HistorialPagoResponse {
    historialId: number;
    reservacionId: number;
    monto: number;
    metodo: EnumPago;
    fecha: string;
}