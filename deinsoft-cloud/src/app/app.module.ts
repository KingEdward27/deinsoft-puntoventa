import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClient, HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import {AppRoutingModule} from '@/app-routing.module';
import {AppComponent} from './app.component';
import {MainComponent} from '@modules/main/main.component';
import {LoginComponent} from '@modules/login/login.component';
import {HeaderComponent} from '@modules/main/header/header.component';
import {FooterComponent} from '@modules/main/footer/footer.component';
import {MenuSidebarComponent} from '@modules/main/menu-sidebar/menu-sidebar.component';
import {BlankComponent} from '@pages/blank/blank.component';
import {ReactiveFormsModule} from '@angular/forms';
import {ProfileComponent} from '@pages/profile/profile.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RegisterComponent} from '@modules/register/register.component';
import {DashboardComponent} from '@pages/dashboard/dashboard.component';
import {ToastrModule} from 'ngx-toastr';
import {MessagesComponent} from '@modules/main/header/messages/messages.component';
import {NotificationsComponent} from '@modules/main/header/notifications/notifications.component';
import {ButtonComponent} from './components/button/button.component';

import {registerLocaleData} from '@angular/common';
import localeEn from '@angular/common/locales/en';
import {UserComponent} from '@modules/main/header/user/user.component';
import {ForgotPasswordComponent} from '@modules/forgot-password/forgot-password.component';
import {RecoverPasswordComponent} from '@modules/recover-password/recover-password.component';
import {LanguageComponent} from '@modules/main/header/language/language.component';
import {PrivacyPolicyComponent} from './modules/privacy-policy/privacy-policy.component';
import {MainMenuComponent} from './pages/main-menu/main-menu.component';
import {SubMenuComponent} from './pages/main-menu/sub-menu/sub-menu.component';
import {MenuItemComponent} from './components/menu-item/menu-item.component';
import {DropdownComponent} from './components/dropdown/dropdown.component';
import {DropdownMenuComponent} from './components/dropdown/dropdown-menu/dropdown-menu.component';
import {ControlSidebarComponent} from './modules/main/control-sidebar/control-sidebar.component';
import {StoreModule} from '@ngrx/store';
import {authReducer} from './store/auth/reducer';
import {uiReducer} from './store/ui/reducer';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SelectComponent } from './components/select/select.component';
import { CheckboxComponent } from './components/checkbox/checkbox.component';
import { GenericListComponent } from './base/components/generic-list/generic-list.component';
import { GenericFormComponent } from './base/components/generic-form/generic-form.component';
import { GenericMasterDetailFormComponent } from './base/components/generic-master-detail-form/generic-master-detail-form.component';
import { GenericReportComponent } from './base/components/generic-report/generic-report.component';
import { GenericModalComponent } from './base/components/generic-modal/generic-modal.component';
import { CnfCategoriaComponent } from '@pages/cnf-categoria/cnf-categoria';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { translatePartialLoader } from './config/translation.config';
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
import { CnfSubCategoriaComponent } from '@pages/cnf-subcategoria/cnf-subcategoria';
import { CnfUnidadMedidaComponent } from '@pages/cnf-unidadmedida/cnf-unidadmedida';
import { CnfNumComprobanteComponent } from '@pages/cnf-numcomprobante/cnf-numcomprobante';
import { CnfMaestroComponent } from '@pages/cnf-maestro/cnf-maestro';
import { CnfFormaPagoComponent } from '@pages/cnf-formapago/cnf-formapago';
import { InvAlmacenComponent } from '@pages/inv-almacen/inv-almacen';
import { ActCompraFormComponent } from '@pages/act-compra/act-compra-form.component';
import { ActVentaFormComponent } from '@pages/act-venta/act-venta-form.component';
import { JwtInterceptor } from './config/jwt.interceptor';
import { RptActVentaFormComponent } from '@pages/reports/act-venta/rpt-act-venta-form.component';
import { RptActCompraComponent } from '@pages/reports/act-compra/rpt-act-compra.component';
import { GenericList2Component } from './base/components/generic-list2/generic-list2.component';
import { GenericChildFormComponent } from './base/components/generic-child/generic-child-form.component';
import { SegUsuarioComponent } from '@pages/security/seg-usuario/seg_usuario';
import { SegRolComponent } from '@pages/security/seg-rol/seg_rol';

registerLocaleData(localeEn, 'en-EN');

@NgModule({
    declarations: [
        AppComponent,
        MainComponent,
        LoginComponent,
        HeaderComponent,
        FooterComponent,
        MenuSidebarComponent,
        BlankComponent,
        ProfileComponent,
        RegisterComponent,
        DashboardComponent,
        MessagesComponent,
        NotificationsComponent,
        ButtonComponent,
        UserComponent,
        ForgotPasswordComponent,
        RecoverPasswordComponent,
        LanguageComponent,
        PrivacyPolicyComponent,
        MainMenuComponent,
        SubMenuComponent,
        MenuItemComponent,
        DropdownComponent,
        DropdownMenuComponent,
        ControlSidebarComponent,
        SelectComponent,
        CheckboxComponent,
        GenericListComponent,
        GenericFormComponent,
        GenericMasterDetailFormComponent,
        GenericModalComponent,
        GenericReportComponent,
        
        CnfCategoriaComponent,
        CnfEmpresaComponent,
        CnfMonedaComponent,
        CnfRegionComponent,
        CnfProvinciaComponent,
        CnfDistritoComponent,
        CnfTipoDocumentoComponent,
        CnfLocalComponent,
        CnfTipoComprobanteComponent,
        
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
        ActVentaFormComponent,
        SegUsuarioComponent,
        SegRolComponent,
        RptActVentaFormComponent,
        RptActCompraComponent,
        GenericReportComponent,
        GenericList2Component,
        GenericChildFormComponent
    ],
    imports: [
        BrowserModule,
        StoreModule.forRoot({auth: authReducer, ui: uiReducer}),
        HttpClientModule,
        AppRoutingModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot({
            timeOut: 3000,
            positionClass: 'toast-top-right',
            preventDuplicates: true
        }),
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
export class AppModule {}
export function HttpLoaderFactory(http:HttpClient){
	return new TranslateHttpLoader(http);
}