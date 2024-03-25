import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpEventType } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class MediaService {
  url: string = environment.apiUrl + '/api/ope-video';
  fileSizeUnit: number = 1024;
  public isApiSetup = true;

  constructor(private http: HttpClient) { }

  getFileSize(fileSize: number): number {
    if (fileSize > 0) {
      if (fileSize < this.fileSizeUnit * this.fileSizeUnit) {
        fileSize = parseFloat((fileSize / this.fileSizeUnit).toFixed(2));
      } else if (fileSize < this.fileSizeUnit * this.fileSizeUnit * this.fileSizeUnit) {
        fileSize = parseFloat(
          (fileSize / this.fileSizeUnit / this.fileSizeUnit).toFixed(2)
        );
      } else if (fileSize < this.fileSizeUnit * this.fileSizeUnit * this.fileSizeUnit * this.fileSizeUnit) {
        fileSize = parseFloat(
          (fileSize / this.fileSizeUnit / this.fileSizeUnit / this.fileSizeUnit).toFixed(2)
        );
      }
    }

    return fileSize;
  }

  getFileSizeUnit(fileSize: number) {
    console.log(fileSize);

    let fileSizeInWords = 'bytes';

    if (fileSize > 0) {
      if (fileSize < this.fileSizeUnit) {
        fileSizeInWords = 'bytes';
      } else if (fileSize < this.fileSizeUnit * this.fileSizeUnit) {
        fileSizeInWords = 'KB';
      } else if (fileSize < this.fileSizeUnit * this.fileSizeUnit * this.fileSizeUnit) {
        fileSizeInWords = 'MB';
      }else if (fileSize < this.fileSizeUnit * this.fileSizeUnit * this.fileSizeUnit * this.fileSizeUnit) {
        fileSizeInWords = 'GB';
      }
    }

    return fileSizeInWords;
  }

  uploadMedia(formData: any) : Observable<any>{
    // const headers = new HttpHeaders().set('Content-Type', 'application/json');
    console.log(this.url + '/upload');

    return this.http
      .post(this.url + '/upload', formData, {
        reportProgress: true,
        observe: 'events',
      })
      // .pipe(
      //   map((event) => {
      //     switch (event.type) {
      //       case HttpEventType.UploadProgress:
      //         const progress = Math.round((100 * event.loaded) / event.total);
      //         return { status: 'progress', message: progress };

      //       case HttpEventType.Response:
      //         return event.body;
      //       default:
      //         return `Unhandled event: ${event.type}`;
      //     }
      //   })
      // );
  }
}
