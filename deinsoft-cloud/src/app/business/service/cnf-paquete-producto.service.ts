import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { CnfPaqueteProducto } from '../model/cnf-paquete-producto.model';

@Injectable({
  providedIn: 'root'
})
export class CnfPaqueteProductoService {
  url: string = environment.apiUrl + '/api/business/cnf-paquete-producto';
  constructor(private http: HttpClient,
    private router: Router) {
  }


  public getAllDataCombo(): Observable<any> {
    return this.http.get<CnfPaqueteProducto[]>(`${this.url}/get-all-cnf-paquete-producto-combo`);
  }
  public getData(arg1: string): Observable<any> {
    let params = new HttpParams().set("id", arg1)
    return this.http.get<CnfPaqueteProducto>(`${this.url}/get-cnf-paquete-producto`, { params });
  }
  // public save(form: any): Observable<CnfPaqueteProducto> {
  //   return this.http.post<CnfPaqueteProducto>(this.url + '/save-cnf-paquete-producto', form);
  // }
  public delete(arg1: string): Observable<HttpResponse<{}>> {
    let params = new HttpParams().set("id", arg1);
    return this.http.delete(this.url + '/delete-cnf-paquete-producto', { observe: 'response', params });
  }

  public save(form: any, fileToUpload:any): Observable<CnfPaqueteProducto> {
    const formData: FormData = new FormData();
    const mData = JSON.stringify(form);
    formData.append('CnfPaqueteProducto', mData);
    if (fileToUpload) {
      formData.append('file', fileToUpload, fileToUpload?.name);
    }
    
    return this.http.post<CnfPaqueteProducto>(this.url + '/save-cnf-paquete-producto',formData);
  }

  public postFile(fileToUpload: File): Observable<any> {
    const endpoint = this.url + '/upload';
    const formData: FormData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);
    return this.http.post(endpoint, formData, { headers: {}, reportProgress: true, observe: 'events' })

  }

  public getVideoPathFromResources(nameVideo: string,sizeVideo: string): Observable<string> {
    return this.http.get(`${this.url}/get-ope-video-path?fileName=${nameVideo}&fileSize=${sizeVideo}`, {responseType: 'text'});
  }
}

