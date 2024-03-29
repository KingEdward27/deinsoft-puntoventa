import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Detail } from '../components/model/Detail';
import { UpdateParam } from '../components/model/UpdateParam';

@Injectable({
  providedIn: 'root'
})
export  class CommonService {
  public properties: any;
  public baseEndpoint: string="";
  public updateParam:UpdateParam = new UpdateParam();
  protected cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(protected http: HttpClient) { }
  
  public getProperties(){
    return this.properties;
  }

  public getListWithFilters(jsonData:any): Observable<any[]> {
    return this.http.post<any[]>(this.baseEndpoint+"/select-by-tablename-with-filters", jsonData);
  }
  public getList(jsonData:any): Observable<any[]> {
    return this.http.post<any[]>(this.baseEndpoint+"/select-by-properties", jsonData);
  }
  public getListComboWithFilters(jsonData2:any): Observable<any[]> {
    return this.http.post<any[]>(this.baseEndpoint+"/select-combos-by-properties", jsonData2);
  }
  public getData(jsonData:any): Observable<any> {
    return this.http.post<any>(this.baseEndpoint+"/select-by-id", jsonData);
  }
  public getListComboByTableName(tableName:string,columns:string): Observable<any[]> {
    const params = new HttpParams()
    .set('tableName', tableName)
    .set('descriptionColumns', columns);
    return this.http.get<any[]>(this.baseEndpoint+"/select-combos-by-tablename", {params: params});
  }
  public selectByTableNameAndColumns(tableName:string,columns:string,condition:any): Observable<any[]> {
    const params = new HttpParams()
    .set('tableName', tableName)
    .set('descriptionColumns', columns)
    .set('condition', condition);
    return this.http.get<any[]>(this.baseEndpoint+"/select-columns", {params: params});
  }
  public selectByTableNameAndColumnsAsync(tableName:string,columns:string,condition:any): Promise<any[]> {
    const params = new HttpParams()
    .set('tableName', tableName)
    .set('descriptionColumns', columns)
    .set('condition', condition);
    console.log(columns);
    
    return this.http.get<any[]>(this.baseEndpoint+"/select-columns", {params: params}).toPromise();
  }
  // public listarPaginas(page: string, size: string): Observable<any>{
  //   const params = new HttpParams()
  //   .set('page', page)
  //   .set('size', size);
  //   return this.http.get<any>(`${this.baseEndpoint}/pagina`, {params: params});
  // }

  // public ver(id: number): Observable<any> {
  //   return this.http.get<any>(`${this.baseEndpoint}/${id}`);
  // }

  public create(jsonData: any): Observable<any> {
    return this.http.post<any>(this.baseEndpoint+"/save", jsonData,{ headers: this.cabeceras });
  }
  public createTransactional(jsonData: any): Observable<any> {
    return this.http.post<any>(this.baseEndpoint+"/save-transaction", jsonData,{ headers: this.cabeceras });
  }
  public createTransactionalAsync(jsonData: any): Promise<any> {
    return this.http.post<any>(this.baseEndpoint+"/save-transaction", jsonData,{ headers: this.cabeceras }).toPromise();
  }
  // public editar(e: any): Observable<any> {
  //   return this.http.put<any>(`${this.baseEndpoint}/${e.id}`, e,
  //     { headers: this.cabeceras });
  // }
  public genericRequest(url:string, param:any) : Promise<any> {
    let params = new HttpParams();
    console.log(param);
    param.forEach((element:any) => {
      console.log(params);
      params = params.set(element[0],element[1]);
    });
    console.log(params);
    
    return this.http.get<any>(this.baseEndpoint + url, {params: params}).toPromise();
  }
  public genericPostRequest(url:string, param:any,responseType:any):Observable<any> {
    //let params = new HttpParams();
    console.log(this.updateParam);
    // param.forEach((element:any) => {
    //   console.log(params);
    //   params = params.set(element[0],element[1]);
    // });
    //console.log(params);
    if(!responseType){
      responseType = 'text';
    }
    return this.http.post<any>(this.baseEndpoint + url,param,{responseType: responseType} );
  }
  public update(tableName:string, columns:any,condition: string):Observable<HttpResponse<{}>> {
    console.log("update");
    let param = new UpdateParam();
    param.tableName = tableName;
    param.columns = columns;
    param.condition = condition;
    // let map = new Map<string, Object>()
    // map.set("tableName",tableName)
    // map.set("filter",columns)
    // map.set("idValue",id);
    console.log(param);
    
    return this.http.post<any>(this.baseEndpoint+"/update", param);
  }
  public remove(tableName:string,id: number):Observable<HttpResponse<{}>> {
    let params = new HttpParams().set("tableName",tableName).set("id",id);
    return this.http.delete(this.baseEndpoint+"/delete", { observe: 'response' ,params});
  }
  public export(jsonData:any): Observable<any[]> {
    return this.http.post<any[]>(this.baseEndpoint+"/export/excel", jsonData);
  }
}
