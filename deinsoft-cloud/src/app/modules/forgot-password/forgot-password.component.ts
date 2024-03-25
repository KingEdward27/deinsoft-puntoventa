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

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit, OnDestroy {
    @HostBinding('class') class = 'login-box';
    public forgotPasswordForm: FormGroup;
    public isAuthLoading = false;

    message:string;
    constructor(
        private renderer: Renderer2,
        private toastr: ToastrService,
        private appService: AppService,
        private segUsuarioService: SegUsuarioService
    ) {}

    ngOnInit(): void {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
        this.forgotPasswordForm = new FormGroup({
            email: new FormControl(null, Validators.required)
        });
    }

    // forgotPassword() {
    //     if (this.forgotPasswordForm.valid) {
    //     } else {
    //         this.toastr.error('Hello world!', 'Toastr fun!');
    //     }
    // }
    forgotPassword() {
        console.log(this.forgotPasswordForm.valid);
        
        if (this.forgotPasswordForm.valid) {
            let model = new SegUsuario();
            model.email = this.forgotPasswordForm.controls['email'].value;
            this.segUsuarioService.getRecoverPassword(model).subscribe(m => {
                console.log(m);
                this.toastr.success("Registrado correctamente");
                setTimeout(() => {
                    this.message = "Un correo se ha enviado a tu buzón electrónico. Dbe seguir las instrucciones para recuperar su cuenta"
                }, 1000);
                // this.router.navigate([this.redirect]);
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
