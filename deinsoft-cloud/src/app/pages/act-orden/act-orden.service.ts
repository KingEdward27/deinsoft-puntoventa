import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { ActOrden } from './act-orden.model';

@Injectable({
  providedIn: 'root'
})
export class ActOrdenService {
  url: string = environment.apiUrl + '/api/business/act-orden';
  constructor(private http: HttpClient,
    private router: Router) {
  }

  public getAllData(arg1: ActOrden): Observable<any> {
    let params = new HttpParams().set("serie", arg1.serie)
      .set("numero", arg1.numero)
      .set("observacion", arg1.observacion)
      .set("flagEstado", arg1.flagEstado)
      .set("flagIsventa", arg1.flagIsventa)
      .set("envioPseFlag", arg1.envioPseFlag)
      .set("envioPseMensaje", arg1.envioPseMensaje)
      .set("xmlhash", arg1.xmlhash)
      .set("codigoqr", arg1.codigoqr)
      .set("numTicket", arg1.numTicket)
      ; return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden`, { params });
  }
  public getAllDataCombo(): Observable<any> {
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-combo`);
  }
  public getAllByActOrdenId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-act-orden`, { params });
  }
  public getAllByCnfMaestroId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-cnf-maestro`, { params });
  }
  public getAllByCnfFormaPagoId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-cnf-forma-pago`, { params });
  }
  public getAllByCnfMonedaId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-cnf-moneda`, { params });
  }
  public getAllByCnfLocalId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-cnf-local`, { params });
  }
  public getAllByCnfTipoComprobanteId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-cnf-tipo-comprobante`, { params });
  }
  public getAllByInvAlmacenId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActOrden[]>(`${this.url}/get-all-act-orden-by-inv-almacen`, { params });
  }
  public getData(arg1: string): Observable<any> {
    let params = new HttpParams().set("id", arg1)
    return this.http.get<ActOrden>(`${this.url}/get-act-orden`, { params });
  }
  public save(form: any): Observable<ActOrden> {
    return this.http.post<ActOrden>(this.url + '/save-act-orden', form);
  }
  public saveCompra(form: any): Observable<ActOrden> {
    return this.http.post<ActOrden>(this.url + '/save-act-orden-compra', form);
  }
  public delete(arg1: string): Observable<HttpResponse<{}>> {
    let params = new HttpParams().set("id", arg1);
    return this.http.delete(this.url + '/delete-act-orden', { observe: 'response', params });
  }
  public sendApi(tableName:string,id: any): Observable<any> {
    let params = new HttpParams()
    .set("id", id.toString());
    return this.http.get<any>(this.url + '/sendapi', { params });
  }
  public getReport(form: any): Observable<ActOrden> {
    return this.http.post<ActOrden>(this.url + '/get-report-act-orden', form);
  }
  public genReportExcel(jsonData:any): Observable<any> {
    return this.http.post(this.url+"/export/excel", jsonData,{observe: 'response', responseType: 'blob'});
  }
  public invalidateActOrden(arg1:string): Observable<any>{
    let params = new HttpParams().set("id",arg1);
    return this.http.post(this.url+'/invalidate-act-orden', params); 
  }
  public validateActOrden(arg1:string): Observable<any>{
    let params = new HttpParams().set("id",arg1);
    return this.http.post(this.url+'/validate-act-orden', params); 
  }
  public getReportContable(localId: number): Observable<any> {
    let params = new HttpParams().set("cnfLocalId",localId);
    return this.http.get<any>(this.url + '/get-list-contable', { params });
  }
  public getTxtSire(form: any, responseType:any): Observable<any> {
    return this.http.post<any>(this.url + '/generateSireTxt', form, {observe: 'response', responseType: responseType});
  }

  public getDashboard(form: any): Observable<any> {
    return this.http.post<any>(this.url + '/get-dashboard-act-orden', form);
  }

}

