import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActContrato } from '../act-contrato.model';
import { ActContratoService } from '../act-contrato.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfMaestro } from "../../cnf-maestro/cnf-maestro.model";
import { CnfMaestroService } from "../../cnf-maestro/cnf-maestro.service";
import { CnfLocal } from "../../cnf-local/cnf-local.model";
import { CnfLocalService } from "../../cnf-local/cnf-local.service";
import { CnfTipoComprobante } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { CnfTipoComprobanteService } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.service";
import { CnfFormaPago } from "../../cnf-forma-pago/cnf-forma-pago.model";
import { CnfFormaPagoService } from "../../cnf-forma-pago/cnf-forma-pago.service";
import { CnfPlanContrato } from "../../cnf-plan-contrato/cnf-plan-contrato.model";
import { CnfPlanContratoService } from "../../cnf-plan-contrato/cnf-plan-contrato.service";


@Component({
  selector: 'app-act-contrato-form',
  templateUrl: './act-contrato-form.component.html',
  styleUrls: ['./act-contrato-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class ActContratoFormComponent implements OnInit{

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
  public model : ActContrato = new ActContrato();
  selectDefaultCnfMaestro:any={id:0,name:"- Seleccione -"};listCnfMaestro:any;
cnfMaestro:CnfMaestro = new CnfMaestro();
loadingCnfMaestro: boolean = false;
selectDefaultCnfLocal:any={id:0,name:"- Seleccione -"};listCnfLocal:any;
cnfLocal:CnfLocal = new CnfLocal();
loadingCnfLocal: boolean = false;
selectDefaultCnfTipoComprobante:any={id:0,name:"- Seleccione -"};listCnfTipoComprobante:any;
cnfTipoComprobante:CnfTipoComprobante = new CnfTipoComprobante();
loadingCnfTipoComprobante: boolean = false;
selectDefaultCnfFormaPago:any={id:0,name:"- Seleccione -"};listCnfFormaPago:any;
cnfFormaPago:CnfFormaPago = new CnfFormaPago();
loadingCnfFormaPago: boolean = false;
selectDefaultCnfPlanContrato:any={id:0,name:"- Seleccione -"};listCnfPlanContrato:any;
cnfPlanContrato:CnfPlanContrato = new CnfPlanContrato();
loadingCnfPlanContrato: boolean = false;
  protected redirect: string = "/act-contrato";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private actContratoService:ActContratoService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfMaestroService : CnfMaestroService,
              private cnfLocalService : CnfLocalService,
              private cnfTipoComprobanteService : CnfTipoComprobanteService,
              private cnfFormaPagoService : CnfFormaPagoService,
              private cnfPlanContratoService : CnfPlanContratoService,
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
    this.getListCnfMaestro();
    this.getListCnfLocal();
    this.getListCnfTipoComprobante();
    this.getListCnfFormaPago();
    this.getListCnfPlanContrato();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actContratoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.actContratoService.save(this.model).subscribe(m => {
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
getListCnfPlanContrato(){
    this.loadingCnfPlanContrato = true;
    console.log(this.chargingsb);
    return this.cnfPlanContratoService.getAllDataCombo().subscribe(data=>{
      this.listCnfPlanContrato = data;
      this.loadingCnfPlanContrato = false;
    })
    
  }
  compareCnfPlanContrato(a1: CnfPlanContrato, a2: CnfPlanContrato): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

