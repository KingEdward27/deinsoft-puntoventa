import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { ActComprobante } from './act-comprobante.model';

@Injectable({
  providedIn: 'root'
})
export class ActComprobanteService {
  url: string = environment.apiUrl + '/api/business/act-comprobante';
  constructor(private http: HttpClient,
    private router: Router) {
  }

  public getAllData(arg1: ActComprobante): Observable<any> {
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
      ; return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante`, { params });
  }
  public getAllDataCombo(): Observable<any> {
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-combo`);
  }
  public getAllByActComprobanteId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-act-comprobante`, { params });
  }
  public getAllByCnfMaestroId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-cnf-maestro`, { params });
  }
  public getAllByCnfFormaPagoId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-cnf-forma-pago`, { params });
  }
  public getAllByCnfMonedaId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-cnf-moneda`, { params });
  }
  public getAllByCnfLocalId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-cnf-local`, { params });
  }
  public getAllByCnfTipoComprobanteId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-cnf-tipo-comprobante`, { params });
  }
  public getAllByInvAlmacenId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActComprobante[]>(`${this.url}/get-all-act-comprobante-by-inv-almacen`, { params });
  }
  public getData(arg1: string): Observable<any> {
    let params = new HttpParams().set("id", arg1)
    return this.http.get<ActComprobante>(`${this.url}/get-act-comprobante`, { params });
  }
  public save(form: any): Observable<ActComprobante> {
    return this.http.post<ActComprobante>(this.url + '/save-act-comprobante', form);
  }
  public saveCompra(form: any): Observable<ActComprobante> {
    return this.http.post<ActComprobante>(this.url + '/save-act-comprobante-compra', form);
  }
  public delete(arg1: string): Observable<HttpResponse<{}>> {
    let params = new HttpParams().set("id", arg1);
    return this.http.delete(this.url + '/delete-act-comprobante', { observe: 'response', params });
  }
  public sendApi(tableName:string,id: any): Observable<any> {
    let params = new HttpParams()
    .set("id", id.toString());
    return this.http.get<any>(this.url + '/sendapi', { params });
  }
  public getReport(form: any): Observable<ActComprobante> {
    return this.http.post<ActComprobante>(this.url + '/get-report-act-comprobante', form);
  }
  public genReportExcel(jsonData:any): Observable<any> {
    return this.http.post(this.url+"/export/excel", jsonData,{observe: 'response', responseType: 'blob'});
  }
  public invalidateActComprobante(arg1:string): Observable<any>{
    let params = new HttpParams().set("id",arg1);
    return this.http.post(this.url+'/invalidate-act-comprobante', params); 
  }
  public validateActComprobante(arg1:string): Observable<any>{
    let params = new HttpParams().set("id",arg1);
    return this.http.post(this.url+'/validate-act-comprobante', params); 
  }
  public getReportContable(localId: number): Observable<any> {
    let params = new HttpParams().set("cnfLocalId",localId);
    return this.http.get<any>(this.url + '/get-list-contable', { params });
  }
  public getTxtSire(form: any, responseType:any): Observable<any> {
    return this.http.post<any>(this.url + '/generateSireTxt', form, {observe: 'response', responseType: responseType});
  }
}

