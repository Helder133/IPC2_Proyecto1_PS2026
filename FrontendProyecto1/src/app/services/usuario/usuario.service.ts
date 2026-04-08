import { inject, Injectable } from "@angular/core";
import { RestConstants } from "../../shared/restapi/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { UsuarioResponse } from "../../models/usuario/UsuarioResponse";
import { UsuarioRequest } from "../../models/usuario/UsuarioRequest";
import { UsuarioUpdate } from "../../models/usuario/UsuarioUpdate";

@Injectable({
    providedIn: 'root'
})

export class UsuarioService {
    private restConstants = new RestConstants();
    private httpClient = inject(HttpClient);
    private url = `${this.restConstants.getApiUrl()}usuario`;

    public createUsuario(formUsuario: UsuarioRequest): Observable<null> {
        return this.httpClient.post<any>(`${this.url}/insertar`, formUsuario);
    }

    public getUsuarios(): Observable<UsuarioResponse[]> {
        return this.httpClient.get<UsuarioResponse[]>(this.url);
    } 

    public getUsuarioById(id: number): Observable<UsuarioResponse> {
        return this.httpClient.get<UsuarioResponse>(`${this.url}/${id}`);
    }

    public notEstado(usuarioId: number): Observable<null> {
        return this.httpClient.put<any>(`${this.url}/actualizar/estado/${usuarioId}`, null);
    }

    public getUsuarioByCoincidence(coincidence: string): Observable<UsuarioResponse[]> {
        return this.httpClient.get<UsuarioResponse[]>(`${this.url}/${coincidence}`);
    }

    public updateUsuario(formUsuario: UsuarioUpdate): Observable<null> {
        return this.httpClient.put<any>(`${this.url}/actualizar`, formUsuario);
    }

    public deleteUsuario(id: number): Observable<null> {
        return this.httpClient.delete<any>(`${this.url}/eliminar/${id}`);
    }

}