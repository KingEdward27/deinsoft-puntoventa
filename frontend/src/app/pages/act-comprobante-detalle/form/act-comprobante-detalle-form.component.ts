import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActComprobanteDetalle } from '../act-comprobante-detalle.model';
import { ActComprobanteDetalleService } from '../act-comprobante-detalle.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { ActComprobante } from "../../act-comprobante/act-comprobante.model";
import { ActComprobanteService } from "../../act-comprobante/act-comprobante.service";
import { CnfProducto } from "../../cnf-producto/cnf-producto.model";
import { CnfProductoService } from "../../cnf-producto/cnf-producto.service";
import { CnfImpuestoCondicion } from "../../cnf-impuesto-condicion/cnf-impuesto-condicion.model";
import { CnfImpuestoCondicionService } from "../../cnf-impuesto-condicion/cnf-impuesto-condicion.service";


@Component({
  selector: 'app-act-comprobante-detalle-form',
  templateUrl: './act-comprobante-detalle-form.component.html',
  styleUrls: ['./act-comprobante-detalle-form.component.css']
})
export class ActComprobanteDetalleFormComponent implements OnInit{

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
  public model : ActComprobanteDetalle = new ActComprobanteDetalle();
  selectDefaultActComprobante:any={id:0,name:"- Seleccione -"};listActComprobante:any;
actComprobante:ActComprobante = new ActComprobante();
loadingActComprobante: boolean = false;
selectDefaultCnfProducto:any={id:0,name:"- Seleccione -"};listCnfProducto:any;
cnfProducto:CnfProducto = new CnfProducto();
loadingCnfProducto: boolean = false;
selectDefaultCnfImpuestoCondicion:any={id:0,name:"- Seleccione -"};listCnfImpuestoCondicion:any;
cnfImpuestoCondicion:CnfImpuestoCondicion = new CnfImpuestoCondicion();
loadingCnfImpuestoCondicion: boolean = false;
  protected redirect: string = "/act-comprobante-detalle";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private actComprobanteDetalleService:ActComprobanteDetalleService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private actComprobanteService : ActComprobanteService,
              private cnfProductoService : CnfProductoService,
              private cnfImpuestoCondicionService : CnfImpuestoCondicionService,
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
    this.getListActComprobante();
    this.getListCnfProducto();
    this.getListCnfImpuestoCondicion();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actComprobanteDetalleService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.actComprobanteDetalleService.save(this.model).subscribe(m => {
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
  getListActComprobante(){
    this.loadingActComprobante = true;
    console.log(this.chargingsb);
    return this.actComprobanteService.getAllDataCombo().subscribe(data=>{
      this.listActComprobante = data;
      this.loadingActComprobante = false;
    })
    
  }
  compareActComprobante(a1: ActComprobante, a2: ActComprobante): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfProducto(){
    this.loadingCnfProducto = true;
    console.log(this.chargingsb);
    return this.cnfProductoService.getAllDataCombo().subscribe(data=>{
      this.listCnfProducto = data;
      this.loadingCnfProducto = false;
    })
    
  }
  compareCnfProducto(a1: CnfProducto, a2: CnfProducto): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfImpuestoCondicion(){
    this.loadingCnfImpuestoCondicion = true;
    console.log(this.chargingsb);
    return this.cnfImpuestoCondicionService.getAllDataCombo().subscribe(data=>{
      this.listCnfImpuestoCondicion = data;
      this.loadingCnfImpuestoCondicion = false;
    })
    
  }
  compareCnfImpuestoCondicion(a1: CnfImpuestoCondicion, a2: CnfImpuestoCondicion): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

