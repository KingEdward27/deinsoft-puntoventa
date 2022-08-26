import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfFormaPagoDetalle } from '../cnf-forma-pago-detalle.model';
import { CnfFormaPagoDetalleService } from '../cnf-forma-pago-detalle.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfFormaPago } from "../../cnf-forma-pago/cnf-forma-pago.model";
import { CnfFormaPagoService } from "../../cnf-forma-pago/cnf-forma-pago.service";


@Component({
  selector: 'app-cnf-forma-pago-detalle-form',
  templateUrl: './cnf-forma-pago-detalle-form.component.html',
  styleUrls: ['./cnf-forma-pago-detalle-form.component.css']
})
export class CnfFormaPagoDetalleFormComponent implements OnInit{

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
  public model : CnfFormaPagoDetalle = new CnfFormaPagoDetalle();
  selectDefaultCnfFormaPago:any={id:0,name:"- Seleccione -"};listCnfFormaPago:any;
cnfFormaPago:CnfFormaPago = new CnfFormaPago();
loadingCnfFormaPago: boolean = false;
  protected redirect: string = "/cnf-forma-pago-detalle";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfFormaPagoDetalleService:CnfFormaPagoDetalleService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfFormaPagoService : CnfFormaPagoService,
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
    this.getListCnfFormaPago();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfFormaPagoDetalleService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfFormaPagoDetalleService.save(this.model).subscribe(m => {
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
}

