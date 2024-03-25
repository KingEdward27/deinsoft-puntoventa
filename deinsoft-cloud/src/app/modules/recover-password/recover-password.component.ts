import {
    Component,
    HostBinding,
    OnDestroy,
    OnInit,
    Renderer2
} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {AppService} from '@services/app.service';
import { SegUsuario } from '@/business/model/seg-usuario.model';
import { SegUsuarioService } from '@/business/service/seg-usuario.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-recover-password',
    templateUrl: './recover-password.component.html',
    styleUrls: ['./recover-password.component.scss']
})
export class RecoverPasswordComponent implements OnInit, OnDestroy {
    @HostBinding('class') class = 'login-box';

    public recoverPasswordForm: FormGroup;
    public isAuthLoading = false;

    constructor(
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private route: ActivatedRoute,
        private segUsuarioService: SegUsuarioService
    ) {}

    ngOnInit(): void {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
        this.recoverPasswordForm = new FormGroup({
            password: new FormControl(null, Validators.required),
            confirmPassword: new FormControl(null, Validators.required)
        });
    }

    recoverPassword() {
        if (this.recoverPasswordForm.valid) {
            let model = new SegUsuario();
            return this.route.paramMap.subscribe(params => {
                model.tokenRecoverPassword = params.get('string')!;
                model.password = this.recoverPasswordForm.controls['password'].value;
                this.segUsuarioService.recoverPassword(model).subscribe(m => {
                    console.log(m);
                    this.toastr.success("Registrado correctamente");
                    // this.router.navigate([this.redirect]);
                });
            });
            
        } else {
            this.toastr.error('Ingrese los datos del formulario', 'Error');
        }
    }

    ngOnDestroy(): void {
        this.renderer.removeClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }
}
