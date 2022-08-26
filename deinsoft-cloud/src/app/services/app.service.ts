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
            this.user = tokenDecrypt.user
            this.user.profile = tokenDecrypt.authorities[0].authority;
            console.log(this.user);

            if(this.user.profile.includes('ROLE_ADMIN')){
                this.user.menu = [
                    {
                        name: 'Dashboard',
                        path: ['/'],
                        icon :"fa-tachometer-alt"
                    },
                    // {
                    //     name: 'Blank',
                    //     path: ['/blank'],
                    //     icon :"fa-tachometer-alt"
                    // },
                    {
                        name: 'Administración',
                        icon :"fa-user-shield",
                        children: [
                            {name: 'Región',path: ['/region']},
                            {name: 'Provincia',path: ['/provincia']},
                            {name: 'Distrito',path: ['/distrito']},
                            {name: 'Tipo de documento de identidad',path: ['/tipo-documento']},
                            {name: 'Moneda',path: ['/moneda']},
                            {name: 'Empresa',path: ['/empresa']},
                            {name: 'Unidad Medida',path: ['/unidadmedida']}
                        ]
                    },
                    {
                        name: 'Seguridad',
                        icon :"fa-shield-alt",
                        children: [
                            {name: 'Perfiles',path: ['/perfil']},
                            {name: 'Usuarios',path: ['/usuario']}
                        ]
                    },
                    {
                        name: 'Configuración',
                        icon :"fa-cog",
                        children: [
                            {name: 'Tipo de comprobante',path: ['/tipo-comprobante']},
                            {name: 'Numeración de comprobante',path: ['/numcomprobante']},
                            {name: 'Forma de Pago',path: ['/forma-pago']},
                            {name: 'Clientes y Proveedores',path: ['/maestro']}
                        ]
                    },
                    {
                        name: 'Inventario',
                        icon :"fa-box",
                        children: [
                            {name: 'Local',path: ['/local']},
                            {name: 'Almacen',path: ['/almacen']},
                            {name: 'Marca',path: ['/marca']},
                            {name: 'Categoría',path: ['/categoria']},
                            {name: 'Sub Categoría',path: ['/subcategoria']},
                            {name: 'Unidad Medida',path: ['/unidadmedida']},
                            {name: 'Producto',path: ['/producto']},
                            {name: 'Almacén',path: ['/almacen']},
                            {name: 'Compra',path: ['/compra']}
                        ]
                    },
                    {
                        name: 'Ventas',
                        icon :"fa-cart-plus",
                        children: [
                            {name: 'Venta',path: ['/venta']},
                            {name: 'Listado Ventas',path: ['/list-ventas']}
                        ]
                    },
                    {
                        name: 'Reportes',
                        icon :"fa-file",
                        children: [
                            {name: 'Reporte Ventas',path: ['/rpt-ventas']},
                            {name: 'Reporte Compras',path: ['/rpt-compras']},
                            {name: 'Stock Valorizado',path: ['/rpt-almacen']},
                            {name: 'Kardex Valorizado',path: ['/rpt-movimiento-producto']}
                        ]
                    }
                ];
            }else{
                this.user.menu = [
                    {
                        name: 'Dashboard',
                        path: ['/'],
                        icon :"fa-tachometer-alt"
                    },
                    {
                        name: 'Configuración',
                        icon :"fa-cog",
                        children: [
                            {name: 'Tipo de comprobante',path: ['/tipo-comprobante']},
                            {name: 'Numeración de comprobante',path: ['/numcomprobante']},
                            {name: 'Forma de Pago',path: ['/forma-pago']},
                            {name: 'Clientes y Proveedores',path: ['/maestro']}
                        ]
                    },
                    {
                        name: 'Inventario',
                        icon :"fa-box",
                        children: [
                            {name: 'Local',path: ['/local']},
                            {name: 'Almacen',path: ['/almacen']},
                            {name: 'Marca',path: ['/marca']},
                            {name: 'Categoría',path: ['/categoria']},
                            {name: 'Sub Categoría',path: ['/subcategoria']},
                            {name: 'Producto',path: ['/producto']},
                            {name: 'Almacén',path: ['/almacen']},
                            {name: 'Compra',path: ['/compra']}
                        ]
                    },
                    {
                        name: 'Ventas',
                        icon :"fa-cart-plus",
                        children: [
                            {name: 'Venta',path: ['/venta']},
                            {name: 'Listado Ventas',path: ['/list-ventas']}
                        ]
                    },
                    {
                        name: 'Reportes',
                        icon :"fa-file",
                        children: [
                            {name: 'Reporte Ventas',path: ['/rpt-ventas']},
                            {name: 'Reporte Compras',path: ['/rpt-compras']},
                            {name: 'Stock Valorizado',path: ['/rpt-almacen']},
                            {name: 'Kardex Valorizado',path: ['/rpt-movimiento-producto']}
                        ]
                    }
                ];
            }
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
