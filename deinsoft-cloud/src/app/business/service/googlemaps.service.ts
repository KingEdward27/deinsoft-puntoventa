import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { SegUsuario } from '../model/seg-usuario.model';
import { GoogleMapModel } from '../model/googlemaps.model copy';

const GOOGLE_MAPS_API_KEY = 'AIzaSyDGEmmGhFeA2PwL-pJrrnwIIrtuY3v8lug';

export type Maps = typeof google.maps;

@Injectable({
  providedIn: 'root'
})
export class GoogleMapsService {
  url:string  = 'https://maps.googleapis.com/maps/api/place/autocomplete/json';
  constructor(private http: HttpClient,
              private router: Router) { 
  }

  public readonly api = this.load();

  private load(): Promise<Maps> {
    const script = document.createElement('script');
    script.type = 'text/javascript';
    script.async = true;
    script.defer = true;
    // tslint:disable-next-line:no-bitwise
    const callbackName = `GooglePlaces_cb_` + ((Math.random() * 1e9) >>> 0);
    script.src = this.getScriptSrc(callbackName);

    interface MyWindow { [name: string]: Function; };
    const myWindow: MyWindow = window as any;

    const promise = new Promise((resolve, reject) => {
      myWindow[callbackName] = resolve;
      script.onerror = reject;
    });
    document.body.appendChild(script);
    return promise.then(() => google.maps);
  }

  private getScriptSrc(callback: string): string {
    interface QueryParams { [key: string]: string; };
    const query: QueryParams = {
      v: '3',
      callback,
      key: GOOGLE_MAPS_API_KEY,
      libraries: 'places',
    };
    const params = Object.keys(query).map(key => `${key}=${query[key]}`).join('&');
    return `//maps.googleapis.com/maps/api/js?${params}&language=pe`;
  }
  public getAllDataComboTypeHead(direccion: string): Observable<any> {
    return this.http.get<any[]>(`${this.url}?input=`+direccion+`&inputtype=textquery&fields=formatted_address%2Cgeometry&key=AIzaSyDGEmmGhFeA2PwL-pJrrnwIIrtuY3v8lug`);
  }
}
