import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { InvMovimientoProducto } from '../inv-movimiento-producto.model';
import { InvMovimientoProductoService } from '../inv-movimiento-producto.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { InvAlmacen } from "../../inv-almacen/inv-almacen.model";
import { InvAlmacenService } from "../../inv-almacen/inv-almacen.service";
import { CnfProducto } from "../../cnf-producto/cnf-producto.model";
import { CnfProductoService } from "../../cnf-producto/cnf-producto.service";
import { ActComprobante } from "../../act-comprobante/act-comprobante.model";
import { ActComprobanteService } from "../../act-comprobante/act-comprobante.service";


@Component({
  selector: 'app-inv-movimiento-producto-form',
  templateUrl: './inv-movimiento-producto-form.component.html',
  styleUrls: ['./inv-movimiento-producto-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class InvMovimientoProductoFormComponent implements OnInit{

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
  public model : InvMovimientoProducto = new InvMovimientoProducto();
  selectDefaultInvAlmacen:any={id:0,name:"- Seleccione -"};listInvAlmacen:any;
invAlmacen:InvAlmacen = new InvAlmacen();
loadingInvAlmacen: boolean = false;
selectDefaultCnfProducto:any={id:0,name:"- Seleccione -"};listCnfProducto:any;
cnfProducto:CnfProducto = new CnfProducto();
loadingCnfProducto: boolean = false;
selectDefaultActComprobante:any={id:0,name:"- Seleccione -"};listActComprobante:any;
actComprobante:ActComprobante = new ActComprobante();
loadingActComprobante: boolean = false;
  protected redirect: string = "/inv-movimiento-producto";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private invMovimientoProductoService:InvMovimientoProductoService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private invAlmacenService : InvAlmacenService,
              private cnfProductoService : CnfProductoService,
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
    this.getListInvAlmacen();
    this.getListCnfProducto();
    this.getListActComprobante();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.invMovimientoProductoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.invMovimientoProductoService.save(this.model).subscribe(m => {
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

