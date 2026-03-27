import { inject, Injectable } from "@angular/core";
import { RestConstants } from "../../shared/restapi/rest-constants";
import { HttpClient } from "@angular/common/http";
import { LoginRequest } from "../../models/login/LoginRequest";
import { Observable } from "rxjs";
import { UsuarioResponse } from "../../models/usuario/UsuarioResponse";

@Injectable({
    providedIn: 'root'
})

export class LoginService {
    private restConstants = new RestConstants();
    private httpClient = inject(HttpClient);
    private url = `${this.restConstants.getApiUrl()}login`;

    public loginUser(formLogin: LoginRequest): Observable<UsuarioResponse> {
        return this.httpClient.post<UsuarioResponse>(this.url, formLogin);
    }
}