import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { InvMovimientoProducto } from './inv-movimiento-producto.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InvMovimientoProductoService {
  url:string  = environment.apiUrl;
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public getAllData(arg1:InvMovimientoProducto):Observable<any>{
    let params = new HttpParams();    return this.http.get<InvMovimientoProducto[]>(`${this.url}/get-all-inv-movimiento-producto`,{params});
  }
  public getAllDataCombo():Observable<any>{
    return this.http.get<InvMovimientoProducto[]>(`${this.url}/get-all-inv-movimiento-producto-combo`);
  }
  public getAllByInvAlmacenId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<InvMovimientoProducto[]>(`${this.url}/get-all-inv-movimiento-producto-by-inv-almacen`,{params});
  } 
  public getAllByCnfProductoId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<InvMovimientoProducto[]>(`${this.url}/get-all-inv-movimiento-producto-by-cnf-producto`,{params});
  } 
  public getAllByActComprobanteId(id:number):Observable<any>{
    let params = new HttpParams().set("id",id.toString());
    return this.http.get<InvMovimientoProducto[]>(`${this.url}/get-all-inv-movimiento-producto-by-act-comprobante`,{params});
  } 
  public getData(arg1:string):Observable<any>{
    let params = new HttpParams().set("id",arg1)
    return this.http.get<InvMovimientoProducto>(`${this.url}/get-inv-movimiento-producto`,{params});
  }
  public save(form:any): Observable<InvMovimientoProducto>{
    return this.http.post<InvMovimientoProducto>(this.url+'/save-inv-movimiento-producto',form); 
  }
  public delete(arg1:string): Observable<HttpResponse<{}>>{
    let params = new HttpParams().set("id",arg1);
    return this.http.delete(this.url+'/delete-inv-movimiento-producto', { observe: 'response' ,params}); 
  }
}

