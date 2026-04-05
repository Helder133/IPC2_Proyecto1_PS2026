import { inject, Injectable } from '@angular/core';
import { RestConstants } from '../../shared/restapi/rest-constants';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ArchivoService {
  private restConstants = new RestConstants();
  private httpClient = inject(HttpClient);
  private url = `${this.restConstants.getApiUrl()}archivo`;

  public uploadFile(formData: FormData): Observable<string[]> {
    return this.httpClient.post<string[]>(this.url, formData);
  }
}
