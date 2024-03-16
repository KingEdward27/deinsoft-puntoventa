import { CnfMaestroService } from '@/business/service/cnf-maestro.service';
import {
    Component,
    OnInit,
    Renderer2,
    OnDestroy,
    HostBinding
} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {AppService} from '@services/app.service';
import {ToastrService} from 'ngx-toastr';
import { CommonService } from '../../base/services/common.service';
import { SegUsuario } from '../../business/model/seg-usuario.model';
import { CnfEmpresa } from '@/business/model/cnf-empresa.model';
import { CnfTipoDocumento } from '@/business/model/cnf-tipo-documento.model';
import { Router } from '@angular/router';

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
    private segUsuario: SegUsuario = new SegUsuario();
    constructor(
        private cnfMaestroService: CnfMaestroService,
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private commonService: CommonService,
        public router: Router,
    ) {}

    ngOnInit() {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'register-page'
        );
        this.registerForm = new FormGroup({
            ruc: new FormControl(null, Validators.required),
            nombreEmpresa: new FormControl(null, Validators.required),
            email: new FormControl(null, Validators.required),
            password: new FormControl(null, [Validators.required]),
            retypePassword: new FormControl(null, [Validators.required])
        });
    }

    async registerByAuth() {
        if (this.registerForm.valid) {
            this.isAuthLoading = true;
            this.segUsuario.rucEmpresa = this.registerForm.controls['ruc'].value;
            this.segUsuario.nombreEmpresa = this.registerForm.controls['nombreEmpresa'].value;
            this.segUsuario.email = this.registerForm.controls['email'].value;
            this.segUsuario.password = this.registerForm.controls['password'].value;
            this.commonService.registerNewUser(this.segUsuario).subscribe(data => {
                this.router.navigate(["/login"]);
                this.toastr.success("Empresa registrada correctamente");
            });
            //await this.appService.registerByAuth(this.registerForm.value);
            this.isAuthLoading = false;
        } else {
            this.toastr.error('Debe ingresar los datos faltantes!');
        }
    }

    async registerByGoogle() {
        this.isGoogleLoading = true;
        //await this.appService.registerByGoogle();
        this.isGoogleLoading = false;
    }

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
    searchSunat(){
        this.cnfMaestroService.getApiNameByDoc2(this.registerForm.controls['ruc'].value).subscribe(data => {
            console.log(data.nombre);
            this.registerForm.get('nombreEmpresa').setValue(data.nombre);
        })
    }
}
