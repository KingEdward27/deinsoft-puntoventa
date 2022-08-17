import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfSubCategoria } from '../cnf-sub-categoria.model';
import { CnfSubCategoriaService } from '../cnf-sub-categoria.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfCategoria } from "../../cnf-categoria/cnf-categoria.model";
import { CnfCategoriaService } from "../../cnf-categoria/cnf-categoria.service";
import { CnfEmpresa } from "../../cnf-empresa/cnf-empresa.model";
import { CnfEmpresaService } from "../../cnf-empresa/cnf-empresa.service";


@Component({
  selector: 'app-cnf-sub-categoria-form',
  templateUrl: './cnf-sub-categoria-form.component.html',
  styleUrls: ['./cnf-sub-categoria-form.component.css']
})
export class CnfSubCategoriaFormComponent implements OnInit{

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
  public model : CnfSubCategoria = new CnfSubCategoria();
  selectDefaultCnfCategoria:any={id:0,name:"- Seleccione -"};listCnfCategoria:any;
cnfCategoria:CnfCategoria = new CnfCategoria();
loadingCnfCategoria: boolean = false;
selectDefaultCnfEmpresa:any={id:0,name:"- Seleccione -"};listCnfEmpresa:any;
cnfEmpresa:CnfEmpresa = new CnfEmpresa();
loadingCnfEmpresa: boolean = false;
  protected redirect: string = "/cnf-sub-categoria";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfSubCategoriaService:CnfSubCategoriaService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfCategoriaService : CnfCategoriaService,
              private cnfEmpresaService : CnfEmpresaService,
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
    this.getListCnfCategoria();
    this.getListCnfEmpresa();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfSubCategoriaService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfSubCategoriaService.save(this.model).subscribe(m => {
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
  getListCnfCategoria(){
    this.loadingCnfCategoria = true;
    console.log(this.chargingsb);
    return this.cnfCategoriaService.getAllDataCombo().subscribe(data=>{
      this.listCnfCategoria = data;
      this.loadingCnfCategoria = false;
    })
    
  }
  compareCnfCategoria(a1: CnfCategoria, a2: CnfCategoria): boolean{
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
}

