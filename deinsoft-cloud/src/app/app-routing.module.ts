import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainComponent} from '@modules/main/main.component';
import {BlankComponent} from '@pages/blank/blank.component';
import {LoginComponent} from '@modules/login/login.component';
import {ProfileComponent} from '@pages/profile/profile.component';
import {RegisterComponent} from '@modules/register/register.component';
import {DashboardComponent} from '@pages/dashboard/dashboard.component';
import {AuthGuard} from '@guards/auth.guard';
import {NonAuthGuard} from '@guards/non-auth.guard';
import {ForgotPasswordComponent} from '@modules/forgot-password/forgot-password.component';
import {RecoverPasswordComponent} from '@modules/recover-password/recover-password.component';
import {PrivacyPolicyComponent} from '@modules/privacy-policy/privacy-policy.component';
import {MainMenuComponent} from '@pages/main-menu/main-menu.component';
import {SubMenuComponent} from '@pages/main-menu/sub-menu/sub-menu.component';
import { CnfCategoriaComponent } from '@pages/cnf-categoria/cnf-categoria';
import { CnfEmpresaComponent } from '@pages/cnf-empresa/cnf-empresa';
import { CnfMonedaComponent } from '@pages/cnf-moneda/cnf-moneda';
import { CnfRegionComponent } from '@pages/cnf-region/cnf-region';
import { CnfProvinciaComponent } from '@pages/cnf-provincia/cnf-provincia';
import { CnfDistritoComponent } from '@pages/cnf-distrito/cnf-distrito';
import { CnfTipoDocumentoComponent } from '@pages/cnf-tipodocumento/cnf-tipodocumento';
import { CnfLocalComponent } from '@pages/cnf-local/cnf-local';
import { CnfTipoComprobanteComponent } from '@pages/cnf-tipocomprobante/cnf-tipocomprobante';
import { CnfMarcaComponent } from '@pages/cnf-marca/cnf-marca';
import { CnfProductoComponent } from '@pages/cnf-producto/cnf-producto';
import { CnfUnidadMedidaComponent } from '@pages/cnf-unidadmedida/cnf-unidadmedida';
import { CnfSubCategoriaComponent } from '@pages/cnf-subcategoria/cnf-subcategoria';
import { CnfNumComprobanteComponent } from '@pages/cnf-numcomprobante/cnf-numcomprobante';
import { CnfMaestroComponent } from '@pages/cnf-maestro/cnf-maestro';
import { CnfFormaPagoComponent } from '@pages/cnf-formapago/cnf-formapago';
import { InvAlmacenComponent } from '@pages/inv-almacen/inv-almacen';
import { ActVentaFormComponent } from '@pages/act-venta/act-venta-form.component';
import { ActCompraFormComponent } from '@pages/act-compra/act-compra-form.component';
import { GenericFormComponent } from './base/components/generic-form/generic-form.component';
import { RptActVentaFormComponent } from '@pages/reports/act-venta/rpt-act-venta-form.component';
import { RptActCompraComponent } from '@pages/reports/act-compra/rpt-act-compra.component';
import { GenericChildFormComponent } from './base/components/generic-child/generic-child-form.component';
import { SegRolComponent } from '@pages/security/seg-rol/seg_rol';
import { SegUsuarioComponent } from '@pages/security/seg-usuario/seg_usuario';
import { ActComprobanteCompraReportFormComponent } from '@pages/reports/act-comprobante/act-comprobante-compra/act-comprobante-compra-report.component';
import { ActComprobanteListFormComponent } from '@pages/act-comprobante/act-comprobante-form/list/act-comprobante-list.component';
import { ActComprobanteReportFormComponent } from '@pages/reports/act-comprobante/act-comprobante-venta/act-comprobante-report.component';
import { InvAlmacenReportFormComponent } from '@pages/reports/inv-almacen/inv-almacen-report.component';
import { InvMovimientoProductoReportFormComponent } from '@pages/reports/inv-movimiento-producto/inv-movimiento-producto-report.component';
import { ActPagoProgramacionListFormComponent } from '@pages/act-pago-programacion/act-pago-programacion-list.component';
import { ActCajaComponent } from '@pages/act-caja/act-caja';
import { ActPagoProgramacionCompraListFormComponent } from '@pages/act-pago-programacion-compras/act-pago-programacion-compra-list.component';
import { ActCajaTurnoComponent } from '@pages/act-caja-turno/act-caja-turno';
import { ActCajaTurnoListComponent } from '@pages/act-caja-turno/list/act-caja-turno-list.component';
import { ActCajaTurnoFormComponent } from '@pages/act-caja-turno/form/act-caja-turno-form.component';
import { SegUsuarioEmpresaComponent } from '@pages/security/seg-usuario-empresa/seg_usuario';
import { SegMenuListComponent } from '@pages/security/seg-menu/list/seg-menu-list.component';
import { SegMenuFormComponent } from '@pages/security/seg-menu/form/seg-menu-form.component';
import { SegAccionFormComponent } from '@pages/security/seg-accion/form/seg-accion-form.component';
import { SegAccionListComponent } from '@pages/security/seg-accion/list/seg-accion-list.component';
import { SegPermisoFormComponent } from '@pages/security/seg-permiso/form/seg-permiso-form.component';
import { SegPermisoListComponent } from '@pages/security/seg-permiso/list/seg-permiso-list.component';
import { SegAccionComponent } from '@pages/security/seg-accion/seg_accion';
import { ActCajaOperacionListComponent } from '@pages/act-caja-operacion/list/act-caja-operacion-list.component';
import { ActCajaOperacionFormComponent } from '@pages/act-caja-operacion/form/act-caja-operacion-form.component';
import { ActPagoProgramacionReportComponent } from '@pages/reports/act-pago-programacion/act-pago-programacion-report.component';
import { InvTipoMovComponent } from '@pages/inv-tipo-mov/inv-tipo-mov';
import { InvMovAlmacenListComponent } from '@pages/inv-mov-almacen/list/inv-mov-almacen-list.component';
import { AddNewInvMovAlmacenDetComponent } from '@pages/inv-mov-almacen/add-new-inv-mov-almacen-det/add-new-inv-mov-almacen-det.component';
import { InvMovAlmacenFormComponent } from '@pages/inv-mov-almacen/inv-mov-almacen-form/inv-mov-almacen-form.component';
import { CnfProductoCodeBarListComponent } from './pages/cnf-producto/codebar/cnf-producto-codebar-list.component';
import { CnfProductoListComponent } from './pages/cnf-producto/list/cnf-producto-list.component';
import { CnfProductoFormComponent } from './pages/cnf-producto/form/cnf-producto-form.component';
import { ActComprobanteCompraListFormComponent } from '@pages/act-comprobante/act-comprobante-compra/list/act-comprobante-compra-list.component';
import { ActComprobanteCompraFormComponent } from './pages/act-comprobante/act-comprobante-compra/form/act-comprobante-compra-form.component';
import { ActContrato } from './pages/act-contrato/act-contrato.model';
import { ActContratoFormComponent } from './pages/act-contrato/act-contrato-form/act-contrato-form.component';
import { CnfPlanContratoComponent } from './pages/cnf-plan-contrato/cnf-plan-contrato';
import { CnfEmpresaEmpresaComponent } from '@pages/cnf-empresa/cnf-empresa-empresa';
import { ActComprobanteFormComponent } from './pages/act-comprobante/act-comprobante-form/form/act-comprobante-form.component';
import { ActContratoListComponent } from './pages/act-contrato/list/act-contrato-list.component';
import { ActPagoListComponent } from './pages/act-pago/list/act-pago-list.component';
import { ActContratoReportComponent } from './pages/reports/act-contrato/act-contrato-report.component';
import { CnfZonaComponent } from '@pages/cnf-zona/cnf-zona';
const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        children: [
            // {
            //     path: 'profile',
            //     component: ProfileComponent
            // },
            {
                path: 'blank',
                component: BlankComponent
            },
            {
                path: 'sub-menu-1',
                component: SubMenuComponent
            },
            {
                path: 'sub-menu-2',
                component: BlankComponent
            },
            {
                path: '',
                component: DashboardComponent
            },
            {path: 'generic-form',component: GenericFormComponent},
            {path: 'generic-child-form',component: GenericChildFormComponent},
            {path: 'categoria',component: CnfCategoriaComponent},
            {path: 'empresa',component: CnfEmpresaComponent},
            {path: 'moneda',component: CnfMonedaComponent},
            {path: 'region',component: CnfRegionComponent},
            {path: 'provincia',component: CnfProvinciaComponent},
            {path: 'distrito',component: CnfDistritoComponent},
            {path: 'tipo-documento',component: CnfTipoDocumentoComponent},
            {path: 'local',component: CnfLocalComponent},
            {path: 'tipo-comprobante',component: CnfTipoComprobanteComponent},
            {path: 'venta',component: ActComprobanteFormComponent},
            {path: 'marca',component: CnfMarcaComponent},
            {path: 'categoria',component: CnfCategoriaComponent},
            {path: 'unidadmedida',component: CnfUnidadMedidaComponent},
            {path: 'subcategoria',component: CnfSubCategoriaComponent},
            {path: 'numcomprobante',component: CnfNumComprobanteComponent},
            {path: 'maestro',component: CnfMaestroComponent},
            {path: 'forma-pago',component: CnfFormaPagoComponent},
            {path: 'almacen',component: InvAlmacenComponent},
            {path: 'compra',component: ActComprobanteCompraFormComponent},
            {path: 'usuario',component: SegUsuarioComponent},
            {path: 'perfil',component: SegRolComponent},
            {path: 'cuentas-cobrar',component: ActPagoProgramacionListFormComponent},
            {path: 'cuentas-pagar',component: ActPagoProgramacionCompraListFormComponent},
            {path: 'caja',component: ActCajaComponent},
            {path: 'act-caja-turno',component: ActCajaTurnoListComponent},
            {path: 'new-act-caja-turno',component: ActCajaTurnoFormComponent},
            {path: 'new-act-caja-turno/:id',component: ActCajaTurnoFormComponent},
            {path: 'caja-operacion',component: ActCajaOperacionListComponent},
            {path: 'new-caja-operacion',component: ActCajaOperacionFormComponent},
            {path: 'new-caja-operacion/:id',component: ActCajaOperacionFormComponent},
            {path: 'usuario-empresa',component: SegUsuarioEmpresaComponent},
            {path: 'tipo-mov',component: InvTipoMovComponent},
            {path: 'accion',component: SegAccionComponent},
            {path: 'zona',component: CnfZonaComponent},
            // {path: 'new-accion/:id',component: SegAccionFormComponent},
            // {path: 'new-accion/:id',component: SegAccionFormComponent},

            {path: 'menu',component: SegMenuListComponent},
            {path: 'new-menu',component: SegMenuFormComponent},
            {path: 'new-menu/:id',component: SegMenuFormComponent},

            {path: 'permiso',component: SegPermisoListComponent},
            {path: 'new-permiso',component: SegPermisoFormComponent},
            {path: 'new-permiso/:id',component: SegPermisoFormComponent},

            {path: 'list-ventas',component: ActComprobanteListFormComponent},
            {path: 'rpt-ventas',component: ActComprobanteReportFormComponent},
            {path: 'rpt-compras',component: ActComprobanteCompraReportFormComponent},
            {path: 'rpt-almacen',component: InvAlmacenReportFormComponent},
            {path: 'rpt-movimiento-producto',component: InvMovimientoProductoReportFormComponent},
            {path: 'rpt-pago-programacion',component: ActPagoProgramacionReportComponent},

            {path: 'mov-almacen',component: InvMovAlmacenListComponent},
            {path: 'new-mov-almacen',component: InvMovAlmacenFormComponent},
            {path: 'new-mov-almacen/:id',component: InvMovAlmacenFormComponent},
            {path: 'add-new-mov-almacen-det/:id',component: AddNewInvMovAlmacenDetComponent},

            
            {path: 'producto',component: CnfProductoListComponent},
            {path: 'cnf-producto',component: CnfProductoListComponent},
            {path: 'new-producto',component: CnfProductoFormComponent},
            {path: 'new-producto/:id',component: CnfProductoFormComponent},

            {path: 'producto-list',component: CnfProductoCodeBarListComponent},
            {path: 'list-compras',component: ActComprobanteCompraListFormComponent},
            {path: 'plan-contrato',component: CnfPlanContratoComponent},
            {path: 'new-contrato',component: ActContratoFormComponent},
            {path: 'contrato',component: ActContratoListComponent},
            {path: 'empresa-empresa',component: CnfEmpresaEmpresaComponent},
            {path: 'list-pagos',component: ActPagoListComponent},
            {path: 'rpt-contratos',component: ActContratoReportComponent},
        ]
    },
    {
        path: 'login',
        component: LoginComponent,
        canActivate: [NonAuthGuard]
    },
    {
        path: 'register',
        component: RegisterComponent,
        canActivate: [NonAuthGuard]
    },
    {
        path: 'forgot-password',
        component: ForgotPasswordComponent,
        canActivate: [NonAuthGuard]
    },
    {
        path: 'recover-password',
        component: RecoverPasswordComponent,
        canActivate: [NonAuthGuard]
    },
    {
        path: 'privacy-policy',
        component: PrivacyPolicyComponent,
        canActivate: [NonAuthGuard]
    },
    {path: '**', redirectTo: ''}
];

// @NgModule({
//     imports: [RouterModule.forRoot(routes, {relativeLinkResolution: 'legacy'})],
//     exports: [RouterModule]
// })
@NgModule({
    imports: [RouterModule.forRoot(routes,{ useHash:true,onSameUrlNavigation: 'reload' })],
    exports: [RouterModule]
  })
export class AppRoutingModule {}
