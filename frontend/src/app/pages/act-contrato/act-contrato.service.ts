import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { ActContrato } from './act-contrato.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ActContratoService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:ActContrato):Observable<any>{
    let params = new HttpParams().set("serie",arg1.serie)
.set("numero",arg1.numero)
.set("observacion",arg1.observacion)
.set("flagEstado",arg1.flagEstado)
.set("nroPoste",arg1.nroPoste)
.set("urlMap",arg1.urlMap)
;    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-combo`);
  }
  public getAllByCnfMaestroId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-maestro`,{params});
  } 
  public getAllByCnfLocalId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-local`,{params});
  } 
  public getAllByCnfTipoComprobanteId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-tipo-comprobante`,{params});
  } 
  public getAllByCnfFormaPagoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-forma-pago`,{params});
  } 
  public getAllByCnfPlanContratoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-plan-contrato`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<ActContrato>(`${this.url}/get-act-contrato`,{params});
  }
  public save(form:any): Observable<ActContrato>{
    return this.http.post<ActContrato>(this.url+'/save-act-contrato',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-act-contrato', { observe: 'response' ,params}); 
  }
}

