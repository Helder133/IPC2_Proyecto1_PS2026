export interface ReservacionCanceladaResponse { 
    reservacionId: number; 
    paquete: string; 
    fechaCancelacion: string; 
    montoReembolsado: number; 
    perdidaAgencia: number; 
}