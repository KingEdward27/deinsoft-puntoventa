import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfProducto } from '../cnf-producto.model';
import { CnfProductoService } from '../cnf-producto.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfUnidadMedida } from "../../cnf-unidad-medida/cnf-unidad-medida.model";
import { CnfUnidadMedidaService } from "../../cnf-unidad-medida/cnf-unidad-medida.service";
import { CnfEmpresa } from "../../cnf-empresa/cnf-empresa.model";
import { CnfEmpresaService } from "../../cnf-empresa/cnf-empresa.service";
import { CnfSubCategoria } from "../../cnf-sub-categoria/cnf-sub-categoria.model";
import { CnfSubCategoriaService } from "../../cnf-sub-categoria/cnf-sub-categoria.service";
import { CnfMarca } from "../../cnf-marca/cnf-marca.model";
import { CnfMarcaService } from "../../cnf-marca/cnf-marca.service";


@Component({
  selector: 'app-cnf-producto-form',
  templateUrl: './cnf-producto-form.component.html',
  styleUrls: ['./cnf-producto-form.component.css']
})
export class CnfProductoFormComponent implements OnInit{

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
  public model : CnfProducto = new CnfProducto();
  selectDefaultCnfUnidadMedida:any={id:0,name:"- Seleccione -"};listCnfUnidadMedida:any;
cnfUnidadMedida:CnfUnidadMedida = new CnfUnidadMedida();
loadingCnfUnidadMedida: boolean = false;
selectDefaultCnfEmpresa:any={id:0,name:"- Seleccione -"};listCnfEmpresa:any;
cnfEmpresa:CnfEmpresa = new CnfEmpresa();
loadingCnfEmpresa: boolean = false;
selectDefaultCnfSubCategoria:any={id:0,name:"- Seleccione -"};listCnfSubCategoria:any;
cnfSubCategoria:CnfSubCategoria = new CnfSubCategoria();
loadingCnfSubCategoria: boolean = false;
selectDefaultCnfMarca:any={id:0,name:"- Seleccione -"};listCnfMarca:any;
cnfMarca:CnfMarca = new CnfMarca();
loadingCnfMarca: boolean = false;
  protected redirect: string = "/cnf-producto";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfProductoService:CnfProductoService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfUnidadMedidaService : CnfUnidadMedidaService,
              private cnfEmpresaService : CnfEmpresaService,
              private cnfSubCategoriaService : CnfSubCategoriaService,
              private cnfMarcaService : CnfMarcaService,
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
    this.getListCnfUnidadMedida();
    this.getListCnfEmpresa();
    this.getListCnfSubCategoria();
    this.getListCnfMarca();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfProductoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfProductoService.save(this.model).subscribe(m => {
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
  getListCnfUnidadMedida(){
    this.loadingCnfUnidadMedida = true;
    console.log(this.chargingsb);
    return this.cnfUnidadMedidaService.getAllDataCombo().subscribe(data=>{
      this.listCnfUnidadMedida = data;
      this.loadingCnfUnidadMedida = false;
    })
    
  }
  compareCnfUnidadMedida(a1: CnfUnidadMedida, a2: CnfUnidadMedida): boolean{
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
getListCnfSubCategoria(){
    this.loadingCnfSubCategoria = true;
    console.log(this.chargingsb);
    return this.cnfSubCategoriaService.getAllDataCombo().subscribe(data=>{
      this.listCnfSubCategoria = data;
      this.loadingCnfSubCategoria = false;
    })
    
  }
  compareCnfSubCategoria(a1: CnfSubCategoria, a2: CnfSubCategoria): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfMarca(){
    this.loadingCnfMarca = true;
    console.log(this.chargingsb);
    return this.cnfMarcaService.getAllDataCombo().subscribe(data=>{
      this.listCnfMarca = data;
      this.loadingCnfMarca = false;
    })
    
  }
  compareCnfMarca(a1: CnfMarca, a2: CnfMarca): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

