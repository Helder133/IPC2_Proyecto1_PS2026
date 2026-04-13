import { PaqueteTuristicoResponse } from "../paqueteTuristico/PaqueteTuristicoResponse";
import { EnumReservacion } from "./EnumReservacion";

export interface ReservacionResponse {
    reservacionId: number;
    paqueteId: number;
    usuarioId: number;
    fechaViaje: string;
    fechaCreacion: string;
    cantidadPersona: number;
    costoTotal: number;
    costoAgencia: number;
    estado: EnumReservacion;
    reembolso: number;
    fechaCancelacion: string | null;
    codigoArchivo: string | null;
    totalPagoRealizado: number;
    paqueteTuristico: PaqueteTuristicoResponse;
}