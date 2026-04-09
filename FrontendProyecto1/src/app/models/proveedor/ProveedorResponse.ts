import { EnumProveedor } from "./EnumProveedor";

export interface ProveedorResponse {
    proveedorId: number;
    nombre: string;
    pais: string;
    tipo: EnumProveedor;
    contacto: string;
}