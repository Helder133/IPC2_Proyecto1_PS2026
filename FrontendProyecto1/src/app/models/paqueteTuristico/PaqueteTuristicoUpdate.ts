import { PaqueteTuristicoRequest } from "./PaqueteTuristicoRequest";

export interface PaqueteTuristicoUpdate extends PaqueteTuristicoRequest {
    paqueteId: number;
}