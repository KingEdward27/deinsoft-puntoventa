import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { SegUsuario } from '../model/seg-usuario.model';

@Injectable({
  providedIn: 'root'
})
export class SegUsuarioService {
  url:string  = environment.apiUrl + '/api/business/seg-usuario';
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:SegUsuario):Observable<any>{
    let params = new HttpParams().set("nombre",arg1.nombre)
.set("email",arg1.email)
.set("password",arg1.password)
;    return this.http.get<SegUsuario[]>(`${this.url}/get-all-seg-usuario`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<SegUsuario[]>(`${this.url}/get-all-seg-usuario-combo`);
  }
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<SegUsuario>(`${this.url}/get-seg-usuario`,{params});
  }
  public save(form:any): Observable<SegUsuario>{
    return this.http.post<SegUsuario>(this.url+'/save-seg-usuario',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-seg-usuario', { observe: 'response' ,params}); 
  }

  public changePassword(form:any): Observable<SegUsuario>{
    return this.http.post<SegUsuario>(this.url+'/change-password-seg-usuario',form); 
  }
  public getRecoverPassword(form:any): Observable<SegUsuario>{
    return this.http.post<SegUsuario>(this.url+'/get-recover-password',form); 
  }
  public recoverPassword(form:any): Observable<SegUsuario>{
    return this.http.post<SegUsuario>(this.url+'/recover-password',form); 
  }
  public getAllDataComboByEmpresa():Observable<any>{
;    return this.http.get<SegUsuario[]>(`${this.url}/get-all-seg-usuario-combo-by-empresa`);
  }

  public getAllDataByEmpresaAndLocal(localId:number):Observable<any>{
    let params = new HttpParams().set("localId",localId.toString());
;    return this.http.get<SegUsuario[]>(`${this.url}/get-all-seg-usuario-combo-by-local`,{params});
  }
}

