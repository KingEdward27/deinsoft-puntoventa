import { Injectable } from "@angular/core";
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {Router} from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { UtilService } from "../services/util.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor{

    constructor(
                private router: Router,private utilService:UtilService,
                private http: HttpClient){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        
        // let currentUser =  this.auth.currentUserValue 
            
        //     if(this.auth.getToken()){
        //         request = request.clone({
        //             setHeaders:{ Authorization: this.auth.getToken() }
        //         })
                
        //     }
        return next.handle(request)
              .pipe(
                catchError((error: HttpErrorResponse) => {
                  let errorMsg = '';
                  console.log(error);
                  if (error.error instanceof ErrorEvent) {
                    // console.log('this is client side error');
                    errorMsg = `Error: ${error.error.message}`;
                  }
                  else {
                    // console.log('this is server side error');
                    errorMsg = `Error Code: ${error.status},  Message: ${error.message}`;
                  }
                  if(!error?.url?.includes("login") && error.status != 417&& error.status != 422 && error.status != 0){
                    if(error.status == 403){
                        this.router.navigate(['/login']);
                        window.location.reload();
                    }
                    if (error.status === 401) {
                      this.utilService.msgAccessDeniedWithMessage(error.error);
                    }
                    if (error.status === 400) {
                      
                      if (error.error.message.includes("Date")) {
                        this.utilService.msgProblemDate();
                      }else{
                        this.utilService.msgHTTP400WithMessage(error.error);
                      }
                    }
                    if(error.status === 500 && error.error.message.includes("ConstraintViolationException")){
                      if(error?.url?.includes("delete")){
                        this.utilService.msgProblemDelete();
                      }else{
                        this.utilService.msgHTTP400WithMessage(error.error);
                      }
                      
                    }else{
                      // this.utilService.msgHTTP400WithMessage(error.error?error.error:error.message);
                      this.utilService.msgHTTP400WithMessage(error.error.message);
                    }
                  }
                  
                  return throwError(error);
                })
              )
    }

    // private catchError(error: HttpErrorResponse, req: any, next: any) {
    //     console.log("catchError wtf!",error);
    //     console.log("catchError wtf2!",req);
    //     if (error instanceof HttpErrorResponse) {
    //       return this.catchHttpError(error, req, next);
    //     } else {
    //       return Observable.throw(error);
    //     }
    // }

    // private catchHttpError(error: HttpErrorResponse, req: HttpRequest<any>, next: HttpHandler) {
    //     console.log()
    // if (error.status === 401 || error.status === 403) {
    //     this.errorResponse = JSON.parse(JSON.stringify(error.error));
    //     console.log("expired token!.!")
    //     if(this.errorResponse!= null && this.errorResponse.errorCode != null && this.errorResponse.errorCode == 10){
    //         return Observable.throw(error);
    //     }else{
    //         return this.catchUnauthorized(error, req, next);
    //     }
    // } else {
    //         return Observable.throw(error);
    //         }
    // }
      
    // private catchUnauthorized(error: HttpErrorResponse, req: HttpRequest<any>, next: HttpHandler) {
    // if(!this.auth.isAuthenticated() ){
    //     this.navigateToNoAutorizado();
    //     return Observable.throw(error);
    // }else{
    //     return Observable.throw(error);
    //     }
    // }

    // private navigateToNoAutorizado() {
    //     console.log("navigateTo No autorizado")
    //     //this.router.navigate(['/login']);
    // }
    
}