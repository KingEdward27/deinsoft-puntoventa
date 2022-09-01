import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActCajaOperacion } from '../act-caja-operacion.model';
import { ActCajaOperacionService } from '../act-caja-operacion.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { ActCajaTurno } from "../../act-caja-turno/act-caja-turno.model";
import { ActCajaTurnoService } from "../../act-caja-turno/act-caja-turno.service";
import { ActComprobante } from "../../act-comprobante/act-comprobante.model";
import { ActComprobanteService } from "../../act-comprobante/act-comprobante.service";
import { ActPago } from "../../act-pago/act-pago.model";
import { ActPagoService } from "../../act-pago/act-pago.service";


@Component({
  selector: 'app-act-caja-operacion-form',
  templateUrl: './act-caja-operacion-form.component.html',
  styleUrls: ['./act-caja-operacion-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class ActCajaOperacionFormComponent implements OnInit{

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
  public model : ActCajaOperacion = new ActCajaOperacion();
  selectDefaultActCajaTurno:any={id:0,name:"- Seleccione -"};listActCajaTurno:any;
actCajaTurno:ActCajaTurno = new ActCajaTurno();
loadingActCajaTurno: boolean = false;
selectDefaultActComprobante:any={id:0,name:"- Seleccione -"};listActComprobante:any;
actComprobante:ActComprobante = new ActComprobante();
loadingActComprobante: boolean = false;
selectDefaultActPago:any={id:0,name:"- Seleccione -"};listActPago:any;
actPago:ActPago = new ActPago();
loadingActPago: boolean = false;
  protected redirect: string = "/act-caja-operacion";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private actCajaOperacionService:ActCajaOperacionService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private actCajaTurnoService : ActCajaTurnoService,
              private actComprobanteService : ActComprobanteService,
              private actPagoService : ActPagoService,
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
    this.getListActCajaTurno();
    this.getListActComprobante();
    this.getListActPago();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actCajaOperacionService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.actCajaOperacionService.save(this.model).subscribe(m => {
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
  getListActCajaTurno(){
    this.loadingActCajaTurno = true;
    console.log(this.chargingsb);
    return this.actCajaTurnoService.getAllDataCombo().subscribe(data=>{
      this.listActCajaTurno = data;
      this.loadingActCajaTurno = false;
    })
    
  }
  compareActCajaTurno(a1: ActCajaTurno, a2: ActCajaTurno): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
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
getListActPago(){
    this.loadingActPago = true;
    console.log(this.chargingsb);
    return this.actPagoService.getAllDataCombo().subscribe(data=>{
      this.listActPago = data;
      this.loadingActPago = false;
    })
    
  }
  compareActPago(a1: ActPago, a2: ActPago): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

