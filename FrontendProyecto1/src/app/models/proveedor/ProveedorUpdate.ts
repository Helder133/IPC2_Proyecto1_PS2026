import { ProveedorRequest } from "./ProveedorRequest";

export interface ProveedorUpdate extends ProveedorRequest {
    proveedorId: number;
}