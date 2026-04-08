import { EnumUsuario } from "./EnumUsuario";

export interface UsuarioResponse {
    usuario_id: number;
    nombre: string;
    password?: string;
    rol: EnumUsuario
    estado: boolean;
}