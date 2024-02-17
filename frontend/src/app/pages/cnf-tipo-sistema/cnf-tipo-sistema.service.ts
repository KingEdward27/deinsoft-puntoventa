import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { CnfTipoSistema } from './cnf-tipo-sistema.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CnfTipoSistemaService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:CnfTipoSistema):Observable<any>{
    let params = new HttpParams().set("nombre",arg1.nombre)
;    return this.http.get<CnfTipoSistema[]>(`${this.url}/get-all-cnf-tipo-sistema`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<CnfTipoSistema[]>(`${this.url}/get-all-cnf-tipo-sistema-combo`);
  }
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<CnfTipoSistema>(`${this.url}/get-cnf-tipo-sistema`,{params});
  }
  public save(form:any): Observable<CnfTipoSistema>{
    return this.http.post<CnfTipoSistema>(this.url+'/save-cnf-tipo-sistema',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-cnf-tipo-sistema', { observe: 'response' ,params}); 
  }
}

