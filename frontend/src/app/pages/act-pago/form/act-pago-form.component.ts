import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActPago } from '../act-pago.model';
import { ActPagoService } from '../act-pago.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfTipoComprobante } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { CnfTipoComprobanteService } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.service";
import { ActPagoDetalleService } from "../../act-pago-detalle/act-pago-detalle.service";


@Component({
  selector: 'app-act-pago-form',
  templateUrl: './act-pago-form.component.html',
  styleUrls: ['./act-pago-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class ActPagoFormComponent implements OnInit{

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
  public model : ActPago = new ActPago();
  selectDefaultCnfTipoComprobante:any={id:0,name:"- Seleccione -"};listCnfTipoComprobante:any;
cnfTipoComprobante:CnfTipoComprobante = new CnfTipoComprobante();
loadingCnfTipoComprobante: boolean = false;
listActPagoDetalle:any;
selectedOptionActPagoDetalle:any;
  protected redirect: string = "/act-pago";
  selectedOption:any;
  passwordRepeat:any;
  isAdding: boolean = false;
  isSave: boolean = false;
  public modelOrig : ActPago = new ActPago();
  constructor(private actPagoService:ActPagoService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfTipoComprobanteService : CnfTipoComprobanteService,
private actPagoDetalleService : ActPagoDetalleService,
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
    this.getListCnfTipoComprobante();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actPagoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  agregarActPagoDetalle(): void {
    this.router.navigate(["/add-new-act-pago-detalle", { idParent: this.model.id}]);
  }
  editarActPagoDetalle(actPagoDetalle: any): void {
    this.router.navigate(["/add-new-act-pago-detalle", { idParent: this.model.id,id: actPagoDetalle.id}]);
  }
  quitarActPagoDetalle(e: any): void {
    if(!this.id){
      this.model.listActPagoDetalle = this.model.listActPagoDetalle.filter(item => item.id != e.id);
    }
    if(this.id){
      this.utilService.confirmDelete(e).then((result) => { 
        if(result){
          this.actPagoDetalleService.delete(e.id.toString()).subscribe(() => {
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
    this.actPagoService.save(this.model).subscribe(m => {
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
}

