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

const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        canActivate: [AuthGuard],
        canActivateChild: [AuthGuard],
        children: [
            {
                path: 'profile',
                component: ProfileComponent
            },
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
            {path: 'venta',component: ActVentaFormComponent},
            {path: 'producto',component: CnfProductoComponent},
            {path: 'marca',component: CnfMarcaComponent},
            {path: 'categoria',component: CnfCategoriaComponent},
            {path: 'unidadmedida',component: CnfUnidadMedidaComponent},
            {path: 'subcategoria',component: CnfSubCategoriaComponent},
            {path: 'numcomprobante',component: CnfNumComprobanteComponent},
            {path: 'maestro',component: CnfMaestroComponent},
            {path: 'forma-pago',component: CnfFormaPagoComponent},
            {path: 'almacen',component: InvAlmacenComponent},
            {path: 'compra',component: ActCompraFormComponent},
            {path: 'usuario',component: SegUsuarioComponent},
            {path: 'perfil',component: SegRolComponent},
            {path: 'rpt-ventas',component: RptActVentaFormComponent},
            {path: 'rpt-compras',component: RptActCompraComponent}
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