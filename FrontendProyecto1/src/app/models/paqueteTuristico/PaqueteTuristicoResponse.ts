import { DestinoResponse } from "../destino/DestinoResponse";

export interface PaqueteTuristicoResponse {
    paqueteId: number;
    nombre: string;
    destinoId: number;
    duracion: number;
    precioPublico: number;
    capacidadMaxima: number;
    descripcion?: string;
    estado: boolean;
    destinoResponse: DestinoResponse;
}