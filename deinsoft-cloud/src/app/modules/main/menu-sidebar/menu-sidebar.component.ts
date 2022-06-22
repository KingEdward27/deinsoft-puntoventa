import {AppState} from '@/store/state';
import {UiState} from '@/store/ui/state';
import {Component, HostBinding, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {AppService} from '@services/app.service';
import {Observable} from 'rxjs';

const BASE_CLASSES = 'main-sidebar elevation-4';
@Component({
    selector: 'app-menu-sidebar',
    templateUrl: './menu-sidebar.component.html',
    styleUrls: ['./menu-sidebar.component.scss']
})
export class MenuSidebarComponent implements OnInit {
    @HostBinding('class') classes: string = BASE_CLASSES;
    public ui: Observable<UiState>;
    public user;
    public menu;

    constructor(
        public appService: AppService,
        private store: Store<AppState>
    ) {}

    ngOnInit() {
        this.ui = this.store.select('ui');
        this.ui.subscribe((state: UiState) => {
            this.classes = `${BASE_CLASSES} ${state.sidebarSkin}`;
        });
        this.user = this.appService.user;
        if(this.user.profile == 'ROLE_ADMIN'){
            this.menu = [
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
                        {name: 'Empresa',path: ['/empresa']}
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
                        {name: 'Venta',path: ['/venta']}
                    ]
                },
                {
                    name: 'Reportes',
                    icon :"fa-file",
                    children: [
                        {name: 'Reporte Ventas',path: ['/rpt-ventas']},
                        {name: 'Reporte Compras',path: ['/rpt-compras']}
                    ]
                }
            ];
        }else{
            this.menu = [
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
                        {name: 'Venta',path: ['/venta']}
                    ]
                },
                {
                    name: 'Reportes',
                    icon :"fa-file",
                    children: [
                        {name: 'Reporte Ventas',path: ['/rpt-ventas']},
                        {name: 'Reporte Compras',path: ['/rpt-compras']}
                    ]
                }
            ];
        }
    }
}

// export const MENU = [
//     {
//         name: 'Dashboard',
//         path: ['/'],
//         icon :"fa-tachometer-alt"
//     },
//     // {
//     //     name: 'Blank',
//     //     path: ['/blank'],
//     //     icon :"fa-tachometer-alt"
//     // },
//     {
//         name: 'Administración',
//         icon :"fa-user-shield",
//         children: [
//             {name: 'Región',path: ['/region']},
//             {name: 'Provincia',path: ['/provincia']},
//             {name: 'Distrito',path: ['/distrito']},
//             {name: 'Tipo de documento de identidad',path: ['/tipo-documento']},
//             {name: 'Moneda',path: ['/moneda']},
//             {name: 'Empresa',path: ['/empresa']}
//         ]
//     },
//     {
//         name: 'Seguridad',
//         icon :"fa-shield-alt",
//         children: [
//             {name: 'Perfiles',path: ['/perfil']},
//             {name: 'Usuarios',path: ['/usuario']}
//         ]
//     },
//     {
//         name: 'Configuración',
//         icon :"fa-cog",
//         children: [
//             {name: 'Tipo de comprobante',path: ['/tipo-comprobante']},
//             {name: 'Numeración de comprobante',path: ['/numcomprobante']},
//             {name: 'Forma de Pago',path: ['/forma-pago']},
//             {name: 'Clientes y Proveedores',path: ['/maestro']}
//         ]
//     },
//     {
//         name: 'Inventario',
//         icon :"fa-box",
//         children: [
//             {name: 'Local',path: ['/local']},
//             {name: 'Almacen',path: ['/almacen']},
//             {name: 'Marca',path: ['/marca']},
//             {name: 'Categoría',path: ['/categoria']},
//             {name: 'Sub Categoría',path: ['/subcategoria']},
//             {name: 'Unidad Medida',path: ['/unidadmedida']},
//             {name: 'Producto',path: ['/producto']},
//             {name: 'Almacén',path: ['/almacen']},
//             {name: 'Compra',path: ['/compra']}
//         ]
//     },
//     {
//         name: 'Ventas',
//         icon :"fa-cart-plus",
//         children: [
//             {name: 'Venta',path: ['/venta']}
//         ]
//     },
//     {
//         name: 'Reportes',
//         icon :"fa-file",
//         children: [
//             {name: 'Reporte Ventas',path: ['/rpt-ventas']},
//             {name: 'Reporte Compras',path: ['/rpt-compras']}
//         ]
//     }
// ];
