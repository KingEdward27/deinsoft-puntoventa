import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { CnfMedioPago } from '../model/cnf-medio-pago.model';

@Injectable({
  providedIn: 'root'
})
export class CnfMedioPagoService {
  url:string  = environment.apiUrl + '/api/business/cnf-medio-pago';
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData():Observable<any>{
    let params = new HttpParams().set("nombre","")
        .set("flagEstado","1")
;    return this.http.get<CnfMedioPago[]>(`${this.url}/get-all-cnf-medio-pago`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<CnfMedioPago[]>(`${this.url}/get-all-cnf-medio-pago-combo`);
  }
  public getAllByCnfEmpresaId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfMedioPago[]>(`${this.url}/get-all-cnf-medio-pago-by-cnf-empresa`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<CnfMedioPago>(`${this.url}/get-cnf-medio-pago`,{params});
  }
  public save(form:any): Observable<CnfMedioPago>{
    return this.http.post<CnfMedioPago>(this.url+'/save-cnf-medio-pago',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-cnf-medio-pago', { observe: 'response' ,params}); 
  }
}

