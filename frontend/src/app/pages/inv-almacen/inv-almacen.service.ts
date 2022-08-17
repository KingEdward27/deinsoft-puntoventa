import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { InvAlmacen } from './inv-almacen.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InvAlmacenService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:InvAlmacen):Observable<any>{
    let params = new HttpParams().set("nombre",arg1.nombre)
.set("flagEstado",arg1.flagEstado)
;    return this.http.get<InvAlmacen[]>(`${this.url}/get-all-inv-almacen`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<InvAlmacen[]>(`${this.url}/get-all-inv-almacen-combo`);
  }
  public getAllByCnfLocalId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<InvAlmacen[]>(`${this.url}/get-all-inv-almacen-by-cnf-local`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<InvAlmacen>(`${this.url}/get-inv-almacen`,{params});
  }
  public save(form:any): Observable<InvAlmacen>{
    return this.http.post<InvAlmacen>(this.url+'/save-inv-almacen',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-inv-almacen', { observe: 'response' ,params}); 
  }
}

