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
    public menu = MENU;

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
    }
}

export const MENU = [
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
        name: 'Configuración',
        icon :"fa-cog",
        children: [
            {name: 'Región',path: ['/region']},
            {name: 'Provincia',path: ['/provincia']},
            {name: 'Distrito',path: ['/distrito']},
            {name: 'Tipo de documento de identidad',path: ['/tipo-documento']},
            {name: 'Moneda',path: ['/moneda']},
            {name: 'Empresa',path: ['/empresa']},
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
            {name: 'Compra',path: ['/act-compra']}
        ]
    },
    {
        name: 'Ventas',
        icon :"fa-cart-plus",
        children: [
            {name: 'Venta',path: ['/act-venta']}
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
