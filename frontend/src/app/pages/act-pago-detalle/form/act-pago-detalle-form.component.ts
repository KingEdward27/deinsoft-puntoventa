import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActPagoDetalle } from '../act-pago-detalle.model';
import { ActPagoDetalleService } from '../act-pago-detalle.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { ActPago } from "../../act-pago/act-pago.model";
import { ActPagoService } from "../../act-pago/act-pago.service";
import { ActPagoProgramacion } from "../../act-pago-programacion/act-pago-programacion.model";
import { ActPagoProgramacionService } from "../../act-pago-programacion/act-pago-programacion.service";


@Component({
  selector: 'app-act-pago-detalle-form',
  templateUrl: './act-pago-detalle-form.component.html',
  styleUrls: ['./act-pago-detalle-form.component.css']
})
export class ActPagoDetalleFormComponent implements OnInit{

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
  public model : ActPagoDetalle = new ActPagoDetalle();
  selectDefaultActPago:any={id:0,name:"- Seleccione -"};listActPago:any;
actPago:ActPago = new ActPago();
loadingActPago: boolean = false;
selectDefaultActPagoProgramacion:any={id:0,name:"- Seleccione -"};listActPagoProgramacion:any;
actPagoProgramacion:ActPagoProgramacion = new ActPagoProgramacion();
loadingActPagoProgramacion: boolean = false;
  protected redirect: string = "/act-pago-detalle";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private actPagoDetalleService:ActPagoDetalleService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private actPagoService : ActPagoService,
              private actPagoProgramacionService : ActPagoProgramacionService,
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
    this.getListActPago();
    this.getListActPagoProgramacion();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actPagoDetalleService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.actPagoDetalleService.save(this.model).subscribe(m => {
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
getListActPagoProgramacion(){
    this.loadingActPagoProgramacion = true;
    console.log(this.chargingsb);
    return this.actPagoProgramacionService.getAllDataCombo().subscribe(data=>{
      this.listActPagoProgramacion = data;
      this.loadingActPagoProgramacion = false;
    })
    
  }
  compareActPagoProgramacion(a1: ActPagoProgramacion, a2: ActPagoProgramacion): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

