import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { CnfPlanContrato } from './cnf-plan-contrato.model';

@Injectable({
  providedIn: 'root'
})
export class CnfPlanContratoService {
  url:string  = environment.apiUrl + '/api/business/cnf-plan-contrato';
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:CnfPlanContrato):Observable<any>{
    let params = new HttpParams().set("nombre",arg1.nombre);    
    return this.http.get<CnfPlanContrato[]>(`${this.url}/get-all-cnf-plan-contrato`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<CnfPlanContrato[]>(`${this.url}/get-all-cnf-plan-contrato-combo`);
  }
  public getAllByCnfEmpresaId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfPlanContrato[]>(`${this.url}/get-all-cnf-plan-contrato-by-cnf-empresa`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<CnfPlanContrato>(`${this.url}/get-cnf-plan-contrato`,{params});
  }
  public save(form:any): Observable<CnfPlanContrato>{
    return this.http.post<CnfPlanContrato>(this.url+'/save-cnf-plan-contrato',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-cnf-plan-contrato', { observe: 'response' ,params}); 
  }
}

