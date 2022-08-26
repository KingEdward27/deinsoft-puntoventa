import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { CnfFormaPagoDetalle } from './cnf-forma-pago-detalle.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CnfFormaPagoDetalleService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:CnfFormaPagoDetalle):Observable<any>{
    let params = new HttpParams();    return this.http.get<CnfFormaPagoDetalle[]>(`${this.url}/get-all-cnf-forma-pago-detalle`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<CnfFormaPagoDetalle[]>(`${this.url}/get-all-cnf-forma-pago-detalle-combo`);
  }
  public getAllByCnfFormaPagoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfFormaPagoDetalle[]>(`${this.url}/get-all-cnf-forma-pago-detalle-by-cnf-forma-pago`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<CnfFormaPagoDetalle>(`${this.url}/get-cnf-forma-pago-detalle`,{params});
  }
  public save(form:any): Observable<CnfFormaPagoDetalle>{
    return this.http.post<CnfFormaPagoDetalle>(this.url+'/save-cnf-forma-pago-detalle',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-cnf-forma-pago-detalle', { observe: 'response' ,params}); 
  }
}

