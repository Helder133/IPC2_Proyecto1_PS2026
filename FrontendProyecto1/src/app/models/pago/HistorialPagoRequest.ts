import { EnumPago } from "./EnumPago";

export interface HistorialPagoRequest {
    reservacionId: number;
    monto: number;
    metodo: EnumPago;
    fecha: string;
}