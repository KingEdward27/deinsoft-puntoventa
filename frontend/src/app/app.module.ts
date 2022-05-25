import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GenericFormComponent } from './base/components/generic-form/generic-form.component';
import { GenericListComponent } from './base/components/generic-list/generic-list.component';
import { translatePartialLoader } from './config/translation.config';
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
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { GenericMasterDetailFormComponent } from './base/components/generic-master-detail-form/generic-master-detail-form.component';
import { ActVentaFormComponent } from './pages/act-venta/act-venta-form.component';
import { CnfProductoComponent } from './pages/cnf-producto/cnf-producto';
import { CnfMarcaComponent } from './pages/cnf-marca/cnf-marca';
import { CnfCategoriaComponent } from './pages/cnf-categoria/cnf-categoria';
import { CnfSubCategoriaComponent } from './pages/cnf-subcategoria/cnf-subcategoria';
import { CnfUnidadMedidaComponent } from './pages/cnf-unidadmedida/cnf-unidadmedida';
import { CnfNumComprobanteComponent } from './pages/cnf-numcomprobante/cnf-numcomprobante';
import { CnfMaestroComponent } from './pages/cnf-maestro/cnf-maestro';
import { CnfFormaPagoComponent } from './pages/cnf-formapago/cnf-formapago';
import { InvAlmacenComponent } from './pages/inv-almacen/inv-almacen';
import { GenericModalComponent } from './base/components/generic-modal/generic-modal.component';
import { RouterModule } from '@angular/router';
import { ActCompraFormComponent } from './pages/act-compra/act-compra-form.component';
import { RptActVentaFormComponent } from './pages/reports/act-venta/rpt-act-venta-form.component';
import { GenericReportComponent } from './base/components/generic-report/generic-report.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    CompanyFormComponent,
    CompanyListComponent,
    GenericListComponent,
    GenericFormComponent,
    GenericMasterDetailFormComponent,
    CnfEmpresaComponent,
    CnfMonedaComponent,
    CnfRegionComponent,
    CnfProvinciaComponent,
    CnfDistritoComponent,
    CnfTipoDocumentoComponent,
    CnfLocalComponent,
    CnfTipoComprobanteComponent,
    ActVentaFormComponent,
    CnfProductoComponent,
    CnfMarcaComponent,
    CnfCategoriaComponent,
    CnfSubCategoriaComponent,
    CnfUnidadMedidaComponent,
    CnfNumComprobanteComponent,
    CnfMaestroComponent,
    CnfFormaPagoComponent,
    InvAlmacenComponent,
    GenericModalComponent,
    ActCompraFormComponent,
    RptActVentaFormComponent,
    GenericReportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: translatePartialLoader,
        deps: [HttpClient],
      }
      })
  ],
  providers: [
    HttpClientModule,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function HttpLoaderFactory(http:HttpClient){
	return new TranslateHttpLoader(http);
}