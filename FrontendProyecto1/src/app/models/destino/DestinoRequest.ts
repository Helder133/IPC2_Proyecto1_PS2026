export interface DestinoRequest {
    nombre: string;
    pais: string;
    descripcion: string;
    clima_mejor_epoca?: number;
    imagen?: string;
}