import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfMaestro } from '../cnf-maestro.model';
import { CnfMaestroService } from '../cnf-maestro.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfTipoDocumento } from "../../cnf-tipo-documento/cnf-tipo-documento.model";
import { CnfTipoDocumentoService } from "../../cnf-tipo-documento/cnf-tipo-documento.service";
import { CnfEmpresa } from "../../cnf-empresa/cnf-empresa.model";
import { CnfEmpresaService } from "../../cnf-empresa/cnf-empresa.service";
import { CnfDistrito } from "../../cnf-distrito/cnf-distrito.model";
import { CnfDistritoService } from "../../cnf-distrito/cnf-distrito.service";


@Component({
  selector: 'app-cnf-maestro-form',
  templateUrl: './cnf-maestro-form.component.html',
  styleUrls: ['./cnf-maestro-form.component.css']
})
export class CnfMaestroFormComponent implements OnInit{

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
  public model : CnfMaestro = new CnfMaestro();
  selectDefaultCnfTipoDocumento:any={id:0,name:"- Seleccione -"};listCnfTipoDocumento:any;
cnfTipoDocumento:CnfTipoDocumento = new CnfTipoDocumento();
loadingCnfTipoDocumento: boolean = false;
selectDefaultCnfEmpresa:any={id:0,name:"- Seleccione -"};listCnfEmpresa:any;
cnfEmpresa:CnfEmpresa = new CnfEmpresa();
loadingCnfEmpresa: boolean = false;
selectDefaultCnfDistrito:any={id:0,name:"- Seleccione -"};listCnfDistrito:any;
cnfDistrito:CnfDistrito = new CnfDistrito();
loadingCnfDistrito: boolean = false;
  protected redirect: string = "/cnf-maestro";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfMaestroService:CnfMaestroService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfTipoDocumentoService : CnfTipoDocumentoService,
              private cnfEmpresaService : CnfEmpresaService,
              private cnfDistritoService : CnfDistritoService,
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
    this.getListCnfTipoDocumento();
    this.getListCnfEmpresa();
    this.getListCnfDistrito();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfMaestroService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfMaestroService.save(this.model).subscribe(m => {
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
  getListCnfTipoDocumento(){
    this.loadingCnfTipoDocumento = true;
    console.log(this.chargingsb);
    return this.cnfTipoDocumentoService.getAllDataCombo().subscribe(data=>{
      this.listCnfTipoDocumento = data;
      this.loadingCnfTipoDocumento = false;
    })
    
  }
  compareCnfTipoDocumento(a1: CnfTipoDocumento, a2: CnfTipoDocumento): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfEmpresa(){
    this.loadingCnfEmpresa = true;
    console.log(this.chargingsb);
    return this.cnfEmpresaService.getAllDataCombo().subscribe(data=>{
      this.listCnfEmpresa = data;
      this.loadingCnfEmpresa = false;
    })
    
  }
  compareCnfEmpresa(a1: CnfEmpresa, a2: CnfEmpresa): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfDistrito(){
    this.loadingCnfDistrito = true;
    console.log(this.chargingsb);
    return this.cnfDistritoService.getAllDataCombo().subscribe(data=>{
      this.listCnfDistrito = data;
      this.loadingCnfDistrito = false;
    })
    
  }
  compareCnfDistrito(a1: CnfDistrito, a2: CnfDistrito): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

