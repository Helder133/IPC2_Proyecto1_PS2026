export interface PaqueteTuristicoRequest {
    nombre: string;
    destinoId: number;
    duracion: number;
    precioPublico: number;
    capacidadMaxima: number;
    descripcion?: string;
}