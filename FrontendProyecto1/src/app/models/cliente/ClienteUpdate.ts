import { ClienteRequest } from "./clienteRequest";

export interface ClienteUpdate extends ClienteRequest {
    cliente_id: number;
}