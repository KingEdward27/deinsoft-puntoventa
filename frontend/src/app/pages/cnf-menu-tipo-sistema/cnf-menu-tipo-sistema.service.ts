import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { CnfMenuTipoSistema } from './cnf-menu-tipo-sistema.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CnfMenuTipoSistemaService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:CnfMenuTipoSistema):Observable<any>{
    let params = new HttpParams();    return this.http.get<CnfMenuTipoSistema[]>(`${this.url}/get-all-cnf-menu-tipo-sistema`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<CnfMenuTipoSistema[]>(`${this.url}/get-all-cnf-menu-tipo-sistema-combo`);
  }
  public getAllBySegMenuId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfMenuTipoSistema[]>(`${this.url}/get-all-cnf-menu-tipo-sistema-by-seg-menu`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<CnfMenuTipoSistema>(`${this.url}/get-cnf-menu-tipo-sistema`,{params});
  }
  public save(form:any): Observable<CnfMenuTipoSistema>{
    return this.http.post<CnfMenuTipoSistema>(this.url+'/save-cnf-menu-tipo-sistema',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-cnf-menu-tipo-sistema', { observe: 'response' ,params}); 
  }
}

