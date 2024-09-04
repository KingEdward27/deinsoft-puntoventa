import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { SegRolUsuario } from '../model/seg-rol-usuario.model';

@Injectable({
  providedIn: 'root'
})
export class SegRolUsuarioService {
  url:string  = environment.apiUrl + '/api/seg-rol-usuario';
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:SegRolUsuario):Observable<any>{
    let params = new HttpParams();    return this.http.get<SegRolUsuario[]>(`${this.url}/get-all-seg-rol-usuario`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<SegRolUsuario[]>(`${this.url}/get-all-seg-rol-usuario-combo`);
  }
  public getAllBySegRolId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<SegRolUsuario[]>(`${this.url}/get-all-seg-rol-usuario-by-seg-rol`,{params});
  } 
  public getAllBySegUsuarioId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<SegRolUsuario[]>(`${this.url}/get-all-seg-rol-usuario-by-seg-usuario`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<SegRolUsuario>(`${this.url}/get-seg-rol-usuario`,{params});
  }
  public save(form:any): Observable<SegRolUsuario>{
    return this.http.post<SegRolUsuario>(this.url+'/save-seg-rol-usuario',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-seg-rol-usuario', { observe: 'response' ,params}); 
  }
}

