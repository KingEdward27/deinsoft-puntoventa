import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { ActContrato } from './act-contrato.model';

@Injectable({
  providedIn: 'root'
})
export class ActContratoService {
  url: string = environment.apiUrl + '/api/business/act-contrato';
  constructor(private http: HttpClient,
    private router: Router) {
  }

  public getAllData(arg1: ActContrato): Observable<any> {
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
      .set("nroPoste", arg1.nroPoste)
      .set("urlMap", arg1.urlMap)
      ; return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato`, { params });
  }
  public getAllDataCombo(): Observable<any> {
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-combo`);
  }
  public getAllByActComprobanteId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-act-contrato`, { params });
  }
  public getAllByCnfMaestroId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-maestro`, { params });
  }
  public getAllByCnfFormaPagoId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-forma-pago`, { params });
  }
  public getAllByCnfMonedaId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-moneda`, { params });
  }
  public getAllByCnfLocalId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-local`, { params });
  }
  public getAllByCnfTipoComprobanteId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-cnf-tipo-comprobante`, { params });
  }
  public getAllByInvAlmacenId(id: number): Observable<any> {
    let params = new HttpParams().set("id", id.toString());
    return this.http.get<ActContrato[]>(`${this.url}/get-all-act-contrato-by-inv-almacen`, { params });
  }
  public getData(arg1: string): Observable<any> {
    let params = new HttpParams().set("id", arg1)
    return this.http.get<ActContrato>(`${this.url}/get-act-contrato`, { params });
  }
  public save(form: any): Observable<ActContrato> {
    return this.http.post<ActContrato>(this.url + '/save-act-contrato', form);
  }
  public saveCompra(form: any): Observable<ActContrato> {
    return this.http.post<ActContrato>(this.url + '/save-act-contrato-compra', form);
  }
  public delete(arg1: string): Observable<HttpResponse<{}>> {
    let params = new HttpParams().set("id", arg1);
    return this.http.delete(this.url + '/delete-act-contrato', { observe: 'response', params });
  }
  public sendApi(tableName:string,id: any): Observable<any> {
    let params = new HttpParams()
    .set("tableName", tableName)
    .set("id", id.toString());
    return this.http.get<any>(this.url + '/sendapi', { params });
  }
  public getReport(form: any): Observable<ActContrato> {
    return this.http.post<ActContrato>(this.url + '/get-report-act-contrato', form);
  }
  public genReportExcel(jsonData:any): Observable<any> {
    return this.http.post(this.url+"/export/excel", jsonData,{observe: 'response', responseType: 'blob'});
  }
  public invalidateActComprobante(arg1:string): Observable<any>{
    let params = new HttpParams().set("id",arg1);
    return this.http.post(this.url+'/invalidate-act-contrato', params); 
  }
}

