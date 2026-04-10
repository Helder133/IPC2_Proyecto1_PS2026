import { ProveedorResponse } from "../proveedor/ProveedorResponse";

export interface ServicioPaqueteResponse {
    proveedorId: number;
    paqueteId: number;
    descripcion: string;
    costo: number;
    proveedorResponse: ProveedorResponse;
}