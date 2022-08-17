import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActComprobante } from '../act-comprobante.model';
import { ActComprobanteService } from '../act-comprobante.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfMaestro } from "../../cnf-maestro/cnf-maestro.model";
import { CnfMaestroService } from "../../cnf-maestro/cnf-maestro.service";
import { CnfFormaPago } from "../../cnf-forma-pago/cnf-forma-pago.model";
import { CnfFormaPagoService } from "../../cnf-forma-pago/cnf-forma-pago.service";
import { CnfMoneda } from "../../cnf-moneda/cnf-moneda.model";
import { CnfMonedaService } from "../../cnf-moneda/cnf-moneda.service";
import { CnfLocal } from "../../cnf-local/cnf-local.model";
import { CnfLocalService } from "../../cnf-local/cnf-local.service";
import { CnfTipoComprobante } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { CnfTipoComprobanteService } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.service";
import { InvAlmacen } from "../../inv-almacen/inv-almacen.model";
import { InvAlmacenService } from "../../inv-almacen/inv-almacen.service";
import { ActComprobanteDetalleService } from "../../act-comprobante-detalle/act-comprobante-detalle.service";


@Component({
  selector: 'app-act-comprobante-form',
  templateUrl: './act-comprobante-form.component.html',
  styleUrls: ['./act-comprobante-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class ActComprobanteFormComponent implements OnInit{

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
  public model : ActComprobante = new ActComprobante();
  selectDefaultActComprobante:any={id:0,name:"- Seleccione -"};listActComprobante:any;
actComprobante:ActComprobante = new ActComprobante();
loadingActComprobante: boolean = false;
selectDefaultCnfMaestro:any={id:0,name:"- Seleccione -"};listCnfMaestro:any;
cnfMaestro:CnfMaestro = new CnfMaestro();
loadingCnfMaestro: boolean = false;
selectDefaultCnfFormaPago:any={id:0,name:"- Seleccione -"};listCnfFormaPago:any;
cnfFormaPago:CnfFormaPago = new CnfFormaPago();
loadingCnfFormaPago: boolean = false;
selectDefaultCnfMoneda:any={id:0,name:"- Seleccione -"};listCnfMoneda:any;
cnfMoneda:CnfMoneda = new CnfMoneda();
loadingCnfMoneda: boolean = false;
selectDefaultCnfLocal:any={id:0,name:"- Seleccione -"};listCnfLocal:any;
cnfLocal:CnfLocal = new CnfLocal();
loadingCnfLocal: boolean = false;
selectDefaultCnfTipoComprobante:any={id:0,name:"- Seleccione -"};listCnfTipoComprobante:any;
cnfTipoComprobante:CnfTipoComprobante = new CnfTipoComprobante();
loadingCnfTipoComprobante: boolean = false;
selectDefaultInvAlmacen:any={id:0,name:"- Seleccione -"};listInvAlmacen:any;
invAlmacen:InvAlmacen = new InvAlmacen();
loadingInvAlmacen: boolean = false;
listActComprobanteDetalle:any;
selectedOptionActComprobanteDetalle:any;
  protected redirect: string = "/act-comprobante";
  selectedOption:any;
  passwordRepeat:any;
  isAdding: boolean = false;
  isSave: boolean = false;
  public modelOrig : ActComprobante = new ActComprobante();
  constructor(private actComprobanteService:ActComprobanteService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfMaestroService : CnfMaestroService,
              private cnfFormaPagoService : CnfFormaPagoService,
              private cnfMonedaService : CnfMonedaService,
              private cnfLocalService : CnfLocalService,
              private cnfTipoComprobanteService : CnfTipoComprobanteService,
              private invAlmacenService : InvAlmacenService,
private actComprobanteDetalleService : ActComprobanteDetalleService,
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
    this.getListCnfMaestro();
    this.getListCnfFormaPago();
    this.getListCnfMoneda();
    this.getListCnfLocal();
    this.getListCnfTipoComprobante();
    this.getListInvAlmacen();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actComprobanteService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  agregarActComprobanteDetalle(): void {
    this.router.navigate(["/add-new-act-comprobante-detalle", { idParent: this.model.id}]);
  }
  editarActComprobanteDetalle(actComprobanteDetalle: any): void {
    this.router.navigate(["/add-new-act-comprobante-detalle", { idParent: this.model.id,id: actComprobanteDetalle.id}]);
  }
  quitarActComprobanteDetalle(e: any): void {
    if(!this.id){
      this.model.listActComprobanteDetalle = this.model.listActComprobanteDetalle.filter(item => item.id != e.id);
    }
    if(this.id){
      this.utilService.confirmDelete(e).then((result) => { 
        if(result){
          this.actComprobanteDetalleService.delete(e.id.toString()).subscribe(() => {
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
    this.actComprobanteService.save(this.model).subscribe(m => {
      console.log(m);
      this.isOk = true;
      Swal.fire('Registro',`Grabado con �xito`, 'success');
      this.router.navigate([this.redirect]);
    }, err => {
      if(err.status === 400){
        this.error = err.error;
        console.log(this.error);
if(err.error.trace.includes("Date")){
          Swal.fire({
            title: 'Problema al grabar',
            text: `Datos de tipo fecha tienen formato incorrecto`,
            icon: 'warning'
          });
        }
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
getListCnfMaestro(){
    this.loadingCnfMaestro = true;
    console.log(this.chargingsb);
    return this.cnfMaestroService.getAllDataCombo().subscribe(data=>{
      this.listCnfMaestro = data;
      this.loadingCnfMaestro = false;
    })
    
  }
  compareCnfMaestro(a1: CnfMaestro, a2: CnfMaestro): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfFormaPago(){
    this.loadingCnfFormaPago = true;
    console.log(this.chargingsb);
    return this.cnfFormaPagoService.getAllDataCombo().subscribe(data=>{
      this.listCnfFormaPago = data;
      this.loadingCnfFormaPago = false;
    })
    
  }
  compareCnfFormaPago(a1: CnfFormaPago, a2: CnfFormaPago): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfMoneda(){
    this.loadingCnfMoneda = true;
    console.log(this.chargingsb);
    return this.cnfMonedaService.getAllDataCombo().subscribe(data=>{
      this.listCnfMoneda = data;
      this.loadingCnfMoneda = false;
    })
    
  }
  compareCnfMoneda(a1: CnfMoneda, a2: CnfMoneda): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfLocal(){
    this.loadingCnfLocal = true;
    console.log(this.chargingsb);
    return this.cnfLocalService.getAllDataCombo().subscribe(data=>{
      this.listCnfLocal = data;
      this.loadingCnfLocal = false;
    })
    
  }
  compareCnfLocal(a1: CnfLocal, a2: CnfLocal): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListCnfTipoComprobante(){
    this.loadingCnfTipoComprobante = true;
    console.log(this.chargingsb);
    return this.cnfTipoComprobanteService.getAllDataCombo().subscribe(data=>{
      this.listCnfTipoComprobante = data;
      this.loadingCnfTipoComprobante = false;
    })
    
  }
  compareCnfTipoComprobante(a1: CnfTipoComprobante, a2: CnfTipoComprobante): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListInvAlmacen(){
    this.loadingInvAlmacen = true;
    console.log(this.chargingsb);
    return this.invAlmacenService.getAllDataCombo().subscribe(data=>{
      this.listInvAlmacen = data;
      this.loadingInvAlmacen = false;
    })
    
  }
  compareInvAlmacen(a1: InvAlmacen, a2: InvAlmacen): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

