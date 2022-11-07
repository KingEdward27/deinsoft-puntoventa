import { Injectable } from '@angular/core';
import {
    CanActivate,
    CanActivateChild,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    UrlTree,
    Router
} from '@angular/router';
import { Observable } from 'rxjs';
import { AppService } from '@services/app.service';
import { AuthenticationService } from '@services/authentication.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(private router: Router, private appService: AppService, private authenticationService: AuthenticationService) { }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ):
        | Observable<boolean | UrlTree>
        | Promise<boolean | UrlTree>
        | boolean
        | UrlTree {
        const isAuthenticated = this.authenticationService.isAuthenticated();
        console.log(isAuthenticated);

        if(!isAuthenticated){
            this.appService.logout() // , { queryParams: { returnUrl: state.url }}
            return false;
        }
        // return true;
        // console.log(state.url);
        let optionValid = []
        // return this.getProfile();
        let wa = this.appService.getMenu();
        // console.log(wa);
        // for (let we in wa){
        //     console.log(we);
        // }
        wa?.forEach(element => {
            // console.log(element);
            
            // console.log(element.children);
            if (!element.segMenu) {
                optionValid.push(element)
            }
            else if (!element.segMenu.children) {
                if (element.segMenu.path[0] == state.url) {
                    optionValid.push(element.segMenu)
                }
            } else {
                console.log(element.segMenu.children);
                let wa = element.segMenu.children.filter(
                    item => item.path[0] == state.url)
                // console.log(wa);

                if (wa.length > 0) {
                    optionValid.push(wa);
                }

            }
        }, err => {
            return false
        });
        // console.log(optionValid);
        if (optionValid.length > 0 || state.url == '/' 
        || state.url == '/generic-form' || state.url == '/generic-child-form' || state.url == '/swagger-ui') {
            return this.getProfile();
        }
        else {
            // this.router.navigate(['']); // , { queryParams: { returnUrl: state.url }}
            return false;
        }
        //return this.getProfile();
    }
    canActivateChild(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ):
        | Observable<boolean | UrlTree>
        | Promise<boolean | UrlTree>
        | boolean
        | UrlTree {
        return this.canActivate(next, state);
    }

    async getProfile() {
        console.log("getProfile");
        // await this.appService.getProfile();
        // return true;
        if (this.appService.user) {
            console.log("??");

            return true;
        }

        try {
            await this.appService.getProfile();
            console.log("sdf");
            return true;
        } catch (error) {
            console.log(error);
            return false;
        }
    }
}
