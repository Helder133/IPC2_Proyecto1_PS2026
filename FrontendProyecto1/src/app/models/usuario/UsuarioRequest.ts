import { EnumUsuario } from "./EnumUsuario";

export interface UsuarioRequest {
    nombre: string;
    password: string;
    rol: EnumUsuario;
}