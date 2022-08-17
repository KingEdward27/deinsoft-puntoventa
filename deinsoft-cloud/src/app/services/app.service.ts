import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import {ToastrService} from 'ngx-toastr';
import { AuthenticationService } from './authentication.service';

const helper = new JwtHelperService();
@Injectable({
    providedIn: 'root'
})
export class AppService {
    public user: any = null;

    constructor(private router: Router, private toastr: ToastrService,private auth:AuthenticationService) {}

    async loginByAuth({email, password}) {
        try {
            const token = await this.auth.login(email, password);
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.message);
        }
    }

    // async registerByAuth({email, password}) {
    //     try {
    //         const token = await Gatekeeper.registerByAuth(email, password);
    //         localStorage.setItem('token', token);
    //         await this.getProfile();
    //         this.router.navigate(['/']);
    //     } catch (error) {
    //         this.toastr.error(error.message);
    //     }
    // }
    
    // async loginByGoogle() {
    //     try {
    //         const token = await Gatekeeper.loginByGoogle();
    //         localStorage.setItem('token', token);
    //         await this.getProfile();
    //         this.router.navigate(['/']);
    //     } catch (error) {
    //         this.toastr.error(error.message);
    //     }
    // }

    // async registerByGoogle() {
    //     try {
    //         const token = await Gatekeeper.registerByGoogle();
    //         localStorage.setItem('token', token);
    //         await this.getProfile();
    //         this.router.navigate(['/']);
    //     } catch (error) {
    //         this.toastr.error(error.message);
    //     }
    // }

    // async loginByFacebook() {
    //     try {
    //         const token = await Gatekeeper.loginByFacebook();
    //         localStorage.setItem('token', token);
    //         await this.getProfile();
    //         this.router.navigate(['/']);
    //     } catch (error) {
    //         this.toastr.error(error.message);
    //     }
    // }

    // async registerByFacebook() {
    //     try {
    //         const token = await Gatekeeper.registerByFacebook();
    //         localStorage.setItem('token', token);
    //         await this.getProfile();
    //         this.router.navigate(['/']);
    //     } catch (error) {
    //         this.toastr.error(error.message);
    //     }
    // }

    getProfile():any {
        try {
            //this.user = {name:"edward","picture":"logo.png"};
            let tokenDecrypt = helper.decodeToken(localStorage.getItem('token'));
            console.log(tokenDecrypt);
            this.user = tokenDecrypt.user;;
            this.user.profile = tokenDecrypt.authorities[0].authority;
            console.log(this.user);
            return this.user
            // this.user ={ID: "2e7ae590-dc86-4485-809a-9d805e73bb64",
            // createdAt: "2022-06-08T07:09:01.213Z",
            // email: "fake_51@hotmail.com",
            // isVerified: false,
            // metadata: {},
            // provider: "AUTH",
            // //"picture":"logo.png",
            // updatedAt: "2022-06-08T07:09:01.213Z",
            // username: "fake_51"};
            // console.log(this.user);
            
        } catch (error) {
            this.logout();
            
            throw error;
            return null
        }
    }

    logout() {
        localStorage.removeItem('token');
        //localStorage.removeItem('gatekeeper_token');
        this.user = null;
        this.router.navigate(['/login']);
    }
}
