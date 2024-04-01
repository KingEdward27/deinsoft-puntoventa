import { CnfMaestroService } from '@/business/service/cnf-maestro.service';
import {
    Component,
    OnInit,
    Renderer2,
    OnDestroy,
    HostBinding
} from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppService } from '@services/app.service';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from '../../base/services/common.service';
import { SegUsuario } from '../../business/model/seg-usuario.model';
import { CnfEmpresa } from '@/business/model/cnf-empresa.model';
import { CnfTipoDocumento } from '@/business/model/cnf-tipo-documento.model';
import { Router } from '@angular/router';
import { Option, PERFILES } from '@/utils/themes';
import { Store } from '@ngrx/store';
import { AppState } from '@/store/state';
import { Observable } from 'rxjs';
import { UiState } from '@/store/ui/state';
import { CnfLocal } from '@/business/model/cnf-local.model';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {
    @HostBinding('class') class = 'register-box';

    public registerForm: FormGroup;
    public isAuthLoading = false;
    public isGoogleLoading = false;
    public isFacebookLoading = false;
    segUsuario: SegUsuario = new SegUsuario();
    x: CnfLocal = new CnfLocal()
    public listPerfiles: any[] = [{ id: 0, nombre: "- Seleccione -" },
                                  { id: 1, nombre: "Ventas y Almacen" },
                                  { id: 2, nombre: "Control de pagos" }]
    // selectedPerfil:CnfLocal = new CnfLocal();
    // selectDefaultCnfLocal: any = { id: 0, nombre: "- Seleccione -" };  

    constructor(
        private cnfMaestroService: CnfMaestroService,
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private commonService: CommonService,
        public router: Router
    ) { }

    ngOnInit() {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'register-page'
        );


        // this.ui = this.store.select('ui');
        // this.ui.subscribe((state: UiState) => {
        //     this.selectedPerfil = state.selectedPerfil;
        // });
        // this.registerForm = new FormGroup({
        //     ruc: new FormControl(null, Validators.required),
        //     nombreEmpresa: new FormControl(null, Validators.required),
        //     email: new FormControl(null, Validators.required),
        //     password: new FormControl(null, [Validators.required]),
        //     retypePassword: new FormControl(null, [Validators.required])
        // });
    }

    async registerByAuth() {
        // if (this.registerForm.valid) {
        // this.segUsuario.rucEmpresa = this.registerForm.controls['ruc'].value;
        // this.segUsuario.nombreEmpresa = this.registerForm.controls['nombreEmpresa'].value;
        // this.segUsuario.email = this.registerForm.controls['email'].value;
        // this.segUsuario.password = this.registerForm.controls['password'].value;
        // this.segUsuario.perfilEmpresa = this.selectedPerfil.nombre;

        console.log(this.segUsuario);
        if (!this.segUsuario.rucEmpresa || !this.segUsuario.nombreEmpresa || !this.segUsuario.email || !this.segUsuario.password || !this.segUsuario.repassword) {
            this.toastr.error('Debe ingresar los datos faltantes!');
            
            return;
        }

        if (this.segUsuario.password !== this.segUsuario.repassword) {
            this.toastr.error('Las claves deben coincidir');
            return;
        }
        
        this.isAuthLoading = true;
        this.commonService.registerNewUser(this.segUsuario).subscribe(data => {
            this.router.navigate(["/login"]);
            this.toastr.success("Empresa registrada correctamente");
        });
        //await this.appService.registerByAuth(this.registerForm.value);
        this.isAuthLoading = false;
        // } else {
        //     this.toastr.error('Debe ingresar los datos faltantes!');
        // }
    }

    comparePerfil(a1: any, a2: any): boolean {
        if (a1 === undefined && a2 === undefined) {
            return true;
        }
        console.log(a1?.id+'-'+a2?.id);
        
        return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
            ? false : a1.id === a2.id;
    }

    async registerByGoogle() {
        this.isGoogleLoading = true;
        //await this.appService.registerByGoogle();
        this.isGoogleLoading = false;
    }


    // public onChange(value: string) {
    //     this.store.dispatch(new SetNavbarVariant(value));
    // }

    async registerByFacebook() {
        this.isFacebookLoading = true;
        //await this.appService.registerByFacebook();
        this.isFacebookLoading = false;
    }

    ngOnDestroy() {
        this.renderer.removeClass(
            document.querySelector('app-root'),
            'register-page'
        );
    }
    searchSunat() {
        this.cnfMaestroService.getApiNameByDoc2(this.segUsuario.rucEmpresa).subscribe(data => {
            console.log(data);
            this.segUsuario.nombreEmpresa = data.nombre;
        })
    }
}
