
import { SegUsuario } from '@/business/model/seg-usuario.model';
import { SegUsuarioService } from '@/business/service/seg-usuario.service';
import { Component, HostListener, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UtilService } from '@services/util.service';
// import { SegRolUsuarioService } from '../../../../business/service/seg-rol-usuario.service';
// import { SegAccionBoton } from '../../../../business/model/seg-accion-botones';
import { AppService } from '../../../../services/app.service';
import { SegAccionBoton } from '@/business/model/seg-accion-botones';
import { SegRolUsuarioService } from '@/business/service/seg-rol-usuario.service';


@Component({
  selector: 'app-usuario-form-password',
  templateUrl: './seg-usuario-form-password.component.html'
})
export class SegUsuarioFormPasswordComponent implements OnInit {

  //generic variables
  error: any;
  selectedItemsList = [];
  checkedIDs = [];
  chargingsb: boolean = true;
  isDataLoaded: Boolean = false;
  isOk: boolean = false;
  isWarning: boolean = false;
  isDanger: boolean = false;
  message: any = "";
  id: string = "";

  //variables propias
  public model: SegUsuario = new SegUsuario();
  protected redirect: string = "/usuario";
  selectedOption: any;
  segAccionBoton: SegAccionBoton = new SegAccionBoton();
  listSegRolUsuario:any;
  
  constructor(private segUsuarioService: SegUsuarioService,
    private router: Router,
    private utilService: UtilService,
    private route: ActivatedRoute,
    private segRolUsuarioService : SegRolUsuarioService, private appService: AppService) {
  }
  async ngOnInit() {
    this.isDataLoaded = false;
    await this.getPermisos();
    this.loadData();
  }
  async getPermisos() {
    this.segAccionBoton = await this.utilService.setAppPermisos(this.router.url);
    console.log(this.segAccionBoton);
  }

  get passwordMismatch(): boolean {
    return this.model.password !== this.model.repassword;
  }

  getBack() {
    this.router.navigate(['/']);
  }
  loadData() {
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if (!this.id) {
        this.isDataLoaded = true;
      }
      if (this.id) {
        this.segUsuarioService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }

    })

  }
  agregarSegRolUsuario(): void {
    this.router.navigate(["/add-new-seg-rol-usuario", { idParent: this.model.id}]);
  }
  editarSegRolUsuario(segRolUsuario: any): void {
    this.router.navigate(["/add-new-seg-rol-usuario", { idParent: this.model.id,id: segRolUsuario.id}]);
  }
  quitarSegRolUsuario(e: any): void {
    if(!this.id){
      this.model.listSegRolUsuario = this.model.listSegRolUsuario.filter(item => item.id != e.id);
    }
    if(this.id){
      this.utilService.confirmDelete(e).then((result) => { 
        if(result){
          this.segRolUsuarioService.delete(e.id.toString()).subscribe(() => {
            this.utilService.msgOkDelete();
            this.loadData();
          },err => {
            if(err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")){
              this.utilService.msgProblemDelete();
            }
          });
        }
        
      });
    }
    
  }
  public save(): void {
    this.error = {};
    this.model.id = this.appService.user.id;
    if (!this.model.password || this.model.password?.length < 6 || this.model.password?.length > 12) {
      this.error.password = "La contraseña debe tener un tamaño entre 6 y 12 caracteres";
      return;
    }
    if (this.model.password != this.model.repassword) {
      this.error.repassword = "Las contraseñas deben coincidir";
      return;
    }
    this.segUsuarioService.changePassword(this.model).subscribe(m => {
      console.log(m);
      this.isOk = true;
      this.utilService.msgOkSave()
      this.router.navigate([this.redirect]);
    }, err => {
      if (err.status === 422) {
        this.error = err.error;
        console.log(this.error);
      }
    });
  }

  
}

