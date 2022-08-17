import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfEmpresa } from '../cnf-empresa.model';
import { CnfEmpresaService } from '../cnf-empresa.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfTipoDocumento } from "../../cnf-tipo-documento/cnf-tipo-documento.model";
import { CnfTipoDocumentoService } from "../../cnf-tipo-documento/cnf-tipo-documento.service";
import { CnfDistrito } from "../../cnf-distrito/cnf-distrito.model";
import { CnfDistritoService } from "../../cnf-distrito/cnf-distrito.service";


@Component({
  selector: 'app-cnf-empresa-form',
  templateUrl: './cnf-empresa-form.component.html',
  styleUrls: ['./cnf-empresa-form.component.css']
})
export class CnfEmpresaFormComponent implements OnInit{

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
  public model : CnfEmpresa = new CnfEmpresa();
  selectDefaultCnfTipoDocumento:any={id:0,name:"- Seleccione -"};listCnfTipoDocumento:any;
cnfTipoDocumento:CnfTipoDocumento = new CnfTipoDocumento();
loadingCnfTipoDocumento: boolean = false;
selectDefaultCnfDistrito:any={id:0,name:"- Seleccione -"};listCnfDistrito:any;
cnfDistrito:CnfDistrito = new CnfDistrito();
loadingCnfDistrito: boolean = false;
  protected redirect: string = "/cnf-empresa";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfEmpresaService:CnfEmpresaService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfTipoDocumentoService : CnfTipoDocumentoService,
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
    this.getListCnfDistrito();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfEmpresaService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfEmpresaService.save(this.model).subscribe(m => {
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

