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
            //this.user = {nombre:"edward","picture":"logo.png"};
            let tokenDecrypt = helper.decodeToken(localStorage.getItem('token'));
            // console.log(tokenDecrypt);
            this.user = tokenDecrypt.user
            this.user.profile = tokenDecrypt.authorities[0].authority;
            console.log(this.user);
            // this.user.menu = []
            // this.user.menu.push({nombre:'Dashboard',
            //     path:['/'],
            //     icon:"fa-tachometer-alt"})
            // this.segPermisoService.getAllBySegRolNombre(this.user.profile.split("|")[0]).subscribe( data => {
            //     this.user.menu = data
            // })
            return this.user
            // if (this.user.profile.includes('ROLE_SUPER_ADMIN')) {
            //     this.user.menu = [
            //         {
            //             nombre: 'Dashboard',
            //             path: ['/'],
            //             icon: "fa-tachometer-alt"
            //         },
            //         {
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
            //         },
            //         {
            //             nombre: 'Seguridad',
            //             icon: "fa-shield-alt",
            //             children: [
            //                 { nombre: 'Perfiles', path: ['/perfil'] },
            //                 { nombre: 'Acciones', path: ['/accion'] },
            //                 { nombre: 'Opciones de menú', path: ['/menu'] },
            //                 { nombre: 'Permisos', path: ['/permiso'] },
            //                 { nombre: 'Usuarios', path: ['/usuario'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Configuración',
            //             icon: "fa-cog",
            //             children: [
            //                 { nombre: 'Usuarios', path: ['/usuario-empresa'] },
            //                 { nombre: 'Tipo de comprobante', path: ['/tipo-comprobante'] },
            //                 { nombre: 'Numeración de comprobante', path: ['/numcomprobante'] },
            //                 { nombre: 'Forma de Pago', path: ['/forma-pago'] },
            //                 { nombre: 'Caja', path: ['/caja'] },
            //                 { nombre: 'Clientes y Proveedores', path: ['/maestro'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Inventario',
            //             icon: "fa-box",
            //             children: [
            //                 { nombre: 'Local', path: ['/local'] },
            //                 { nombre: 'Almacen', path: ['/almacen'] },
            //                 { nombre: 'Marca', path: ['/marca'] },
            //                 { nombre: 'Categoría', path: ['/categoria'] },
            //                 { nombre: 'Sub Categoría', path: ['/subcategoria'] },
            //                 { nombre: 'Unidad Medida', path: ['/unidadmedida'] },
            //                 { nombre: 'Producto', path: ['/producto'] },
            //                 { nombre: 'Almacén', path: ['/almacen'] },
            //                 { nombre: 'Compra', path: ['/compra'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Ventas',
            //             icon: "fa-cart-plus",
            //             children: [
            //                 { nombre: 'Venta', path: ['/venta'] },
            //                 { nombre: 'Listado Ventas', path: ['/list-ventas'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Caja',
            //             icon: "fa-money-bill",
            //             children: [
            //                 { nombre: 'Turno de Caja', path: ['/act-caja-turno'] },
            //                 { nombre: 'Cuentas x pagar', path: ['/cuentas-pagar'] },
            //                 { nombre: 'Cuentas x cobrar', path: ['/cuentas-cobrar'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Reportes',
            //             icon: "fa-file",
            //             children: [
            //                 { nombre: 'Reporte Ventas', path: ['/rpt-ventas'] },
            //                 { nombre: 'Reporte Compras', path: ['/rpt-compras'] },
            //                 { nombre: 'Stock Valorizado', path: ['/rpt-almacen'] },
            //                 { nombre: 'Kardex Valorizado', path: ['/rpt-movimiento-producto'] }
            //             ]
            //         }
            //     ];
            // } else if (this.user.profile.includes('ROLE_ADMIN')) {
            //     this.user.menu = [
            //         {
            //             nombre: 'Dashboard',
            //             path: ['/'],
            //             icon: "fa-tachometer-alt"
            //         },
            //         // {
            //         //     nombre: 'Blank',
            //         //     path: ['/blank'],
            //         //     icon :"fa-tachometer-alt"
            //         // },
            //         // {
            //         //     nombre: 'Administración',
            //         //     icon :"fa-user-shield",
            //         //     children: [
            //         //         {nombre: 'Región',path: ['/region']},
            //         //         {nombre: 'Provincia',path: ['/provincia']},
            //         //         {nombre: 'Distrito',path: ['/distrito']},
            //         //         {nombre: 'Tipo de documento de identidad',path: ['/tipo-documento']},
            //         //         {nombre: 'Moneda',path: ['/moneda']},
            //         //         {nombre: 'Empresa',path: ['/empresa']},
            //         //         {nombre: 'Unidad Medida',path: ['/unidadmedida']}
            //         //     ]
            //         // },
            //         {
            //             nombre: 'Seguridad',
            //             icon: "fa-shield-alt",
            //             children: [
            //                 { nombre: 'Perfiles', path: ['/perfil'] },
            //                 { nombre: 'Acciones', path: ['/accion'] },
            //                 { nombre: 'Opciones de menú', path: ['/menu'] },
            //                 { nombre: 'Permisos', path: ['/permiso'] },
            //                 { nombre: 'Usuarios', path: ['/usuario'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Configuración',
            //             icon: "fa-cog",
            //             children: [
            //                 { nombre: 'Usuarios', path: ['/usuario-empresa'] },
            //                 { nombre: 'Tipo de comprobante', path: ['/tipo-comprobante'] },
            //                 { nombre: 'Numeración de comprobante', path: ['/numcomprobante'] },
            //                 { nombre: 'Forma de Pago', path: ['/forma-pago'] },
            //                 { nombre: 'Caja', path: ['/caja'] },
            //                 { nombre: 'Clientes y Proveedores', path: ['/maestro'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Inventario',
            //             icon: "fa-box",
            //             children: [
            //                 { nombre: 'Local', path: ['/local'] },
            //                 { nombre: 'Almacen', path: ['/almacen'] },
            //                 { nombre: 'Marca', path: ['/marca'] },
            //                 { nombre: 'Categoría', path: ['/categoria'] },
            //                 { nombre: 'Sub Categoría', path: ['/subcategoria'] },
            //                 { nombre: 'Unidad Medida', path: ['/unidadmedida'] },
            //                 { nombre: 'Producto', path: ['/producto'] },
            //                 { nombre: 'Almacén', path: ['/almacen'] },
            //                 { nombre: 'Compra', path: ['/compra'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Ventas',
            //             icon: "fa-cart-plus",
            //             children: [
            //                 { nombre: 'Venta', path: ['/venta'] },
            //                 { nombre: 'Listado Ventas', path: ['/list-ventas'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Caja',
            //             icon: "fa-money-bill",
            //             children: [
            //                 { nombre: 'Turno de Caja', path: ['/act-caja-turno'] },
            //                 { nombre: 'Cuentas x pagar', path: ['/cuentas-pagar'] },
            //                 { nombre: 'Cuentas x cobrar', path: ['/cuentas-cobrar'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Reportes',
            //             icon: "fa-file",
            //             children: [
            //                 { nombre: 'Reporte Ventas', path: ['/rpt-ventas'] },
            //                 { nombre: 'Reporte Compras', path: ['/rpt-compras'] },
            //                 { nombre: 'Stock Valorizado', path: ['/rpt-almacen'] },
            //                 { nombre: 'Kardex Valorizado', path: ['/rpt-movimiento-producto'] }
            //             ]
            //         }
            //     ];
            // } else {
            //     this.user.menu = [
            //         {
            //             nombre: 'Dashboard',
            //             path: ['/'],
            //             icon: "fa-tachometer-alt"
            //         },
            //         {
            //             nombre: 'Configuración',
            //             icon: "fa-cog",
            //             children: [
            //                 { nombre: 'Usuarios', path: ['/usuario-empresa'] },
            //                 { nombre: 'Tipo de comprobante', path: ['/tipo-comprobante'] },
            //                 { nombre: 'Numeración de comprobante', path: ['/numcomprobante'] },
            //                 { nombre: 'Forma de Pago', path: ['/forma-pago'] },
            //                 { nombre: 'Caja', path: ['/caja'] },
            //                 { nombre: 'Clientes y Proveedores', path: ['/maestro'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Inventario',
            //             icon: "fa-box",
            //             children: [
            //                 { nombre: 'Local', path: ['/local'] },
            //                 { nombre: 'Almacen', path: ['/almacen'] },
            //                 { nombre: 'Marca', path: ['/marca'] },
            //                 { nombre: 'Categoría', path: ['/categoria'] },
            //                 { nombre: 'Sub Categoría', path: ['/subcategoria'] },
            //                 { nombre: 'Producto', path: ['/producto'] },
            //                 { nombre: 'Almacén', path: ['/almacen'] },
            //                 { nombre: 'Compra', path: ['/compra'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Ventas',
            //             icon: "fa-cart-plus",
            //             children: [
            //                 { nombre: 'Venta', path: ['/venta'] },
            //                 { nombre: 'Listado Ventas', path: ['/list-ventas'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Caja',
            //             icon: "fa-money-bill",
            //             children: [
            //                 { nombre: 'Turno de Caja', path: ['/act-caja-turno'] },
            //                 { nombre: 'Cuentas x pagar', path: ['/cuentas-pagar'] },
            //                 { nombre: 'Cuentas x cobrar', path: ['/cuentas-cobrar'] }
            //             ]
            //         },
            //         {
            //             nombre: 'Reportes',
            //             icon: "fa-file",
            //             children: [
            //                 { nombre: 'Reporte Ventas', path: ['/rpt-ventas'] },
            //                 { nombre: 'Reporte Compras', path: ['/rpt-compras'] },
            //                 { nombre: 'Stock Valorizado', path: ['/rpt-almacen'] },
            //                 { nombre: 'Kardex Valorizado', path: ['/rpt-movimiento-producto'] }
            //             ]
            //         }
            //     ];
            // }
            //return this.user
            // this.user ={ID: "2e7ae590-dc86-4485-809a-9d805e73bb64",
            // createdAt: "2022-06-08T07:09:01.213Z",
            // email: "fake_51@hotmail.com",
            // isVerified: false,
            // metadata: {},
            // provider: "AUTH",
            // //"picture":"logo.png",
            // updatedAt: "2022-06-08T07:09:01.213Z",
            // usernombre: "fake_51"};
            // console.log(this.user);

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
