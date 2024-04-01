import { SegPermisoService } from '@/business/service/seg-permiso.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from './authentication.service';

const helper = new JwtHelperService();
@Injectable({
    providedIn: 'root'
})
export class AppService {
    public user: any = null;
    
    constructor(private router: Router, private toastr: ToastrService,
        private auth: AuthenticationService, private segPermisoService: SegPermisoService) {
        }

    async loginByAuth({ email, password }) {
        try {
            const token = await this.auth.login(email, password);
            localStorage.setItem('token', token);
            await this.getProfile();
            await this.setMenu();
            this.router.navigate(['/']);
        } catch (error) {
            if (error.code = 403) {
                this.toastr.error("Usuario o contraeña incorrecta");
            } else
            {
                this.toastr.error(error.message);
            }
        }
    }

    async registerByAuth({email, password}) {
        try {
            const token = "";//await Gatekeeper.registerByAuth(email, password);
            localStorage.setItem('token', token);
            await this.getProfile();
            this.router.navigate(['/']);
        } catch (error) {
            this.toastr.error(error.message);
        }
    }

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
    getMenu(){
        let tokenDecrypt = JSON.parse(localStorage.getItem('menu'));
        return tokenDecrypt
    }
    async setMenu() {
        let tokenDecrypt = helper.decodeToken(localStorage.getItem('token'));
        
        this.user = tokenDecrypt.user
        this.user.profile = tokenDecrypt.authorities[0].authority;
        let rolName = this.user.profile.split("|")[0]
        
        this.user.menu = []
        this.user.menu.push({
            nombre: 'Dashboard',
            path: ['/'],
            icon: "fa-tachometer-alt"
        })
        let menu = await this.segPermisoService.getAllBySegRolNombre(rolName)
        let menu2 = []
        
        menu.forEach(element => {
            
            
            if (element.segMenu.children?.length > 0) {
                    menu2.push({nombre:element.segMenu.nombre,
                        icon:element.segMenu.icon,
                        children:element.segMenu.children})
                
            }
            
            // {
                //             nombre: 'Administración',
                //             icon: "fa-user-shield",
                //             children: [
                //                 { nombre: 'Región', path: ['/region'] },
                //                 { nombre: 'Provincia', path: ['/provincia'] },
                //                 { nombre: 'Distrito', path: ['/distrito'] },
                //                 { nombre: 'Tipo de documento de identidad', path: ['/tipo-documento'] },
                //                 { nombre: 'Moneda', path: ['/moneda'] },
                //                 { nombre: 'Empresa', path: ['/empresa'] },
                //                 { nombre: 'Unidad Medida', path: ['/unidadmedida'] }
                //             ]
                //         }
        });
        
        localStorage.setItem('menu', JSON.stringify(menu2));
        // .subscribe(data => {
        //     this.user.menu = data
        //     return this.user
        // })
    }
    getProfile() {
        try {
            let tokenDecrypt = helper.decodeToken(localStorage.getItem('token'));
            this.user = tokenDecrypt.user
            this.user.profile = tokenDecrypt.authorities[0].authority;
            return this.user

        } catch (error) {
            this.logout();
            throw error;
            
        }
    }
    getUser() {
        try {
            let tokenDecrypt = helper.decodeToken(localStorage.getItem('token'));
            return tokenDecrypt.user
        } catch (error) {
            this.logout();
            throw error;
            
        }
    }
    logout() {
        localStorage.removeItem('token');
        //localStorage.removeItem('gatekeeper_token');
        this.user = null;
        this.router.navigate(['/login']);
    }
}
