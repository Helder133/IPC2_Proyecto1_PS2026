import { EnumProveedor } from "./EnumProveedor";

export interface ProveedorRequest {
    nombre: string;
    pais: string;
    tipo: EnumProveedor;
    contacto: string;
}