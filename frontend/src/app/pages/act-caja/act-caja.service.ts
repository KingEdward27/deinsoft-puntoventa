import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { ActCaja } from './act-caja.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActCajaService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:ActCaja):Observable<any>{
    let params = new HttpParams().set("nombre",arg1.nombre)
.set("estado",arg1.estado)
;    return this.http.get<ActCaja[]>(`${this.url}/get-all-act-caja`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<ActCaja[]>(`${this.url}/get-all-act-caja-combo`);
  }
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<ActCaja>(`${this.url}/get-act-caja`,{params});
  }
  public save(form:any): Observable<ActCaja>{
    return this.http.post<ActCaja>(this.url+'/save-act-caja',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-act-caja', { observe: 'response' ,params}); 
  }
}

