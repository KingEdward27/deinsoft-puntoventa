import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { ActOrdenDetalle } from '../model/act-orden-detalle.model';

@Injectable({
  providedIn: 'root'
})
export class ActOrdenDetalleService {
  url:string  = environment.apiUrl + '/api/business/act-orden-detalle';
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:ActOrdenDetalle):Observable<any>{
    let params = new HttpParams().set("descripcion",arg1.descripcion)
;    return this.http.get<ActOrdenDetalle[]>(`${this.url}/get-all-act-orden-detalle`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<ActOrdenDetalle[]>(`${this.url}/get-all-act-orden-detalle-combo`);
  }
  public getAllByActOrdenId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActOrdenDetalle[]>(`${this.url}/get-all-act-orden-detalle-by-act-orden`,{params});
  } 
  public getAllByCnfProductoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActOrdenDetalle[]>(`${this.url}/get-all-act-orden-detalle-by-cnf-producto`,{params});
  } 
  public getAllByCnfImpuestoCondicionId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActOrdenDetalle[]>(`${this.url}/get-all-act-orden-detalle-by-cnf-impuesto-condicion`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<ActOrdenDetalle>(`${this.url}/get-act-orden-detalle`,{params});
  }
  public save(form:any): Observable<ActOrdenDetalle>{
    return this.http.post<ActOrdenDetalle>(this.url+'/save-act-orden-detalle',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-act-orden-detalle', { observe: 'response' ,params}); 
  }
}

