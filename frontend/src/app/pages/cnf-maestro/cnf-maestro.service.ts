import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { CnfMaestro } from './cnf-maestro.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CnfMaestroService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:CnfMaestro):Observable<any>{
    let params = new HttpParams().set("nroDoc",arg1.nroDoc)
.set("nombres",arg1.nombres)
.set("apellidoPaterno",arg1.apellidoPaterno)
.set("apellidoMaterno",arg1.apellidoMaterno)
.set("razonSocial",arg1.razonSocial)
.set("direccion",arg1.direccion)
.set("correo",arg1.correo)
.set("telefono",arg1.telefono)
.set("flagEstado",arg1.flagEstado)
;    return this.http.get<CnfMaestro[]>(`${this.url}/get-all-cnf-maestro`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<CnfMaestro[]>(`${this.url}/get-all-cnf-maestro-combo`);
  }
  public getAllByCnfTipoDocumentoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfMaestro[]>(`${this.url}/get-all-cnf-maestro-by-cnf-tipo-documento`,{params});
  } 
  public getAllByCnfEmpresaId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfMaestro[]>(`${this.url}/get-all-cnf-maestro-by-cnf-empresa`,{params});
  } 
  public getAllByCnfDistritoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<CnfMaestro[]>(`${this.url}/get-all-cnf-maestro-by-cnf-distrito`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<CnfMaestro>(`${this.url}/get-cnf-maestro`,{params});
  }
  public save(form:any): Observable<CnfMaestro>{
    return this.http.post<CnfMaestro>(this.url+'/save-cnf-maestro',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-cnf-maestro', { observe: 'response' ,params}); 
  }
}
