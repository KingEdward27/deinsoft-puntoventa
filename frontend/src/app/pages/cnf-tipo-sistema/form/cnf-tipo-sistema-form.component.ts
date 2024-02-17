import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfTipoSistema } from '../cnf-tipo-sistema.model';
import { CnfTipoSistemaService } from '../cnf-tipo-sistema.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfMenuTipoSistemaService } from "../../cnf-menu-tipo-sistema/cnf-menu-tipo-sistema.service";


@Component({
  selector: 'app-cnf-tipo-sistema-form',
  templateUrl: './cnf-tipo-sistema-form.component.html',
  styleUrls: ['./cnf-tipo-sistema-form.component.css']
})
export class CnfTipoSistemaFormComponent implements OnInit{

  //generic variables
  error: any;
  selectedItemsList = [];
  checkedIDs = [];
  chargingsb:boolean = true;
  isDataLoaded :Boolean = false;
  isOk:boolean=false;
  isWarning:boolean=false;
  isDanger:boolean=false;
  message:any="";
  id: string = "";

  //variables propias
  public model : CnfTipoSistema = new CnfTipoSistema();
  listCnfMenuTipoSistema:any;
selectedOptionCnfMenuTipoSistema:any;
  protected redirect: string = "/cnf-tipo-sistema";
  selectedOption:any;
  passwordRepeat:any;
  isAdding: boolean = false;
  isSave: boolean = false;
  public modelOrig : CnfTipoSistema = new CnfTipoSistema();
  constructor(private cnfTipoSistemaService:CnfTipoSistemaService, 
              private router: Router,
			   private utilService:UtilService, 
			   private cnfMenuTipoSistemaService : CnfMenuTipoSistemaService,
              private route: ActivatedRoute,) { 
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.loadData();
  }
  getBack() {
    this.router.navigate([this.redirect]);
  }
  loadData(){
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfTipoSistemaService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  agregarCnfMenuTipoSistema(): void {
    this.router.navigate(["/add-new-cnf-menu-tipo-sistema", { idParent: this.model.id}]);
  }
  editarCnfMenuTipoSistema(cnfMenuTipoSistema: any): void {
    this.router.navigate(["/add-new-cnf-menu-tipo-sistema", { idParent: this.model.id,id: cnfMenuTipoSistema.id}]);
  }
  quitarCnfMenuTipoSistema(e: any): void {
    if(!this.id){
      this.model.listCnfMenuTipoSistema = this.model.listCnfMenuTipoSistema.filter(item => item.id != e.id);
    }
    if(this.id){
      this.utilService.confirmDelete(e).then((result) => { 
        if(result){
          this.cnfMenuTipoSistemaService.delete(e.id.toString()).subscribe(() => {
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
    this.cnfTipoSistemaService.save(this.model).subscribe(m => {
      console.log(m);
      this.isOk = true;
      Swal.fire('Registro',`Grabado con �xito`, 'success');
      this.router.navigate([this.redirect]);
    }, err => {
      if(err.status === 400){
        this.error = err.error;
        console.log(this.error);
      }
    });
  }
  }

