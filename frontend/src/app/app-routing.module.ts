import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GenericFormComponent } from './base/components/generic-form/generic-form.component';
import { AuthGuard } from './config/auth.guard';
import { CompanyFormComponent } from './pages/company/form/company-form.component';
import { CompanyListComponent } from './pages/company/list/company-list.component';
import { CnfEmpresaComponent } from './pages/cnf-empresa/cnf-empresa';
import { CnfMonedaComponent } from './pages/cnf-moneda/cnf-moneda';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { CnfRegionComponent } from './pages/cnf-region/cnf-region';
import { CnfProvinciaComponent } from './pages/cnf-provincia/cnf-provincia';
import { CnfDistritoComponent } from './pages/cnf-distrito/cnf-distrito';
import { CnfTipoDocumentoComponent } from './pages/cnf-tipodocumento/cnf-tipodocumento';
import { CnfLocalComponent } from './pages/cnf-local/cnf-local';
import { CnfTipoComprobanteComponent } from './pages/cnf-tipocomprobante/cnf-tipocomprobante';
import { ActVentaFormComponent } from './pages/act-venta/act-venta-form.component';
import { CnfProductoComponent } from './pages/cnf-producto/cnf-producto';
import { CnfSubCategoriaComponent } from './pages/cnf-subcategoria/cnf-subcategoria';
import { CnfUnidadMedidaComponent } from './pages/cnf-unidadmedida/cnf-unidadmedida';
import { CnfCategoriaComponent } from './pages/cnf-categoria/cnf-categoria';
import { CnfMarcaComponent } from './pages/cnf-marca/cnf-marca';
import { CnfNumComprobanteComponent } from './pages/cnf-numcomprobante/cnf-numcomprobante';
import { CnfMaestroComponent } from './pages/cnf-maestro/cnf-maestro';
import { CnfFormaPagoComponent } from './pages/cnf-formapago/cnf-formapago';
import { InvAlmacenComponent } from './pages/inv-almacen/inv-almacen';
import { ActCompraFormComponent } from './pages/act-compra/act-compra-form.component';
import { RptActVentaFormComponent } from './pages/reports/act-venta/rpt-act-venta-form.component';


const routes: Routes = [
  { 
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuard],
    children: [
      {path: 'company',component: CompanyListComponent},
      {path: 'company-form',component: CompanyFormComponent},
      {path: 'generic-form',component: GenericFormComponent},
      {path: 'empresa',component: CnfEmpresaComponent},
      {path: 'moneda',component: CnfMonedaComponent},
      {path: 'region',component: CnfRegionComponent},
      {path: 'provincia',component: CnfProvinciaComponent},
      {path: 'distrito',component: CnfDistritoComponent},
      {path: 'tipo-documento',component: CnfTipoDocumentoComponent},
      {path: 'local',component: CnfLocalComponent},
      {path: 'tipo-comprobante',component: CnfTipoComprobanteComponent},
      {path: 'act-venta',component: ActVentaFormComponent},
      {path: 'producto',component: CnfProductoComponent},
      {path: 'marca',component: CnfMarcaComponent},
      {path: 'categoria',component: CnfCategoriaComponent},
      {path: 'unidadmedida',component: CnfUnidadMedidaComponent},
      {path: 'subcategoria',component: CnfSubCategoriaComponent},
      {path: 'numcomprobante',component: CnfNumComprobanteComponent},
      {path: 'maestro',component: CnfMaestroComponent},
      {path: 'forma-pago',component: CnfFormaPagoComponent},
      {path: 'almacen',component: InvAlmacenComponent},
      {path: 'act-compra',component: ActCompraFormComponent},
      {path: 'rpt-ventas',component: RptActVentaFormComponent}
    ]
  }
]

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash:true })],
  exports: [RouterModule]
})

export class AppRoutingModule { }
