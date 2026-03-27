import { EnumUsuario } from "./EnumUsuario";

export interface UsuarioUpdate {
    usuario_id: number;
    nombre: string;
    password: string;
    rol: EnumUsuario;
}