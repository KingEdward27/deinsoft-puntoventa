import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActPagoProgramacion } from '../act-pago-programacion.model';
import { ActPagoProgramacionService } from '../act-pago-programacion.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { ActComprobante } from "../../act-comprobante/act-comprobante.model";
import { ActComprobanteService } from "../../act-comprobante/act-comprobante.service";


@Component({
  selector: 'app-act-pago-programacion-form',
  templateUrl: './act-pago-programacion-form.component.html',
  styleUrls: ['./act-pago-programacion-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class ActPagoProgramacionFormComponent implements OnInit{

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
  public model : ActPagoProgramacion = new ActPagoProgramacion();
  selectDefaultActComprobante:any={id:0,name:"- Seleccione -"};listActComprobante:any;
actComprobante:ActComprobante = new ActComprobante();
loadingActComprobante: boolean = false;
  protected redirect: string = "/act-pago-programacion";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private actPagoProgramacionService:ActPagoProgramacionService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private actComprobanteService : ActComprobanteService,
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
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actPagoProgramacionService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.actPagoProgramacionService.save(this.model).subscribe(m => {
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
}

