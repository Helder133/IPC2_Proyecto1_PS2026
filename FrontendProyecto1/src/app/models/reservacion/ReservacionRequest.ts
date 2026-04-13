export interface ReservacionRequest {
    paqueteId: number;
    usuarioId: number;
    fechaViaje: string;
    fechaCreacion: string;
    cantidadPersonas: number;
}