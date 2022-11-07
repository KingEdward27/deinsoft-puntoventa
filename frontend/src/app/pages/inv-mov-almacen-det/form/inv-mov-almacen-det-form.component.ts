import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { InvMovAlmacenDet } from '../inv-mov-almacen-det.model';
import { InvMovAlmacenDetService } from '../inv-mov-almacen-det.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { InvMovAlmacen } from "../../inv-mov-almacen/inv-mov-almacen.model";
import { InvMovAlmacenService } from "../../inv-mov-almacen/inv-mov-almacen.service";
import { CnfProducto } from "../../cnf-producto/cnf-producto.model";
import { CnfProductoService } from "../../cnf-producto/cnf-producto.service";


@Component({
  selector: 'app-inv-mov-almacen-det-form',
  templateUrl: './inv-mov-almacen-det-form.component.html',
  styleUrls: ['./inv-mov-almacen-det-form.component.css']
})
export class InvMovAlmacenDetFormComponent implements OnInit{

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
  public model : InvMovAlmacenDet = new InvMovAlmacenDet();
  selectDefaultInvMovAlmacen:any={id:0,name:"- Seleccione -"};listInvMovAlmacen:any;
invMovAlmacen:InvMovAlmacen = new InvMovAlmacen();
loadingInvMovAlmacen: boolean = false;
selectDefaultCnfProducto:any={id:0,name:"- Seleccione -"};listCnfProducto:any;
cnfProducto:CnfProducto = new CnfProducto();
loadingCnfProducto: boolean = false;
  protected redirect: string = "/inv-mov-almacen-det";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private invMovAlmacenDetService:InvMovAlmacenDetService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private invMovAlmacenService : InvMovAlmacenService,
              private cnfProductoService : CnfProductoService,
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
    this.getListInvMovAlmacen();
    this.getListCnfProducto();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.invMovAlmacenDetService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.invMovAlmacenDetService.save(this.model).subscribe(m => {
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
  getListInvMovAlmacen(){
    this.loadingInvMovAlmacen = true;
    console.log(this.chargingsb);
    return this.invMovAlmacenService.getAllDataCombo().subscribe(data=>{
      this.listInvMovAlmacen = data;
      this.loadingInvMovAlmacen = false;
    })
    
  }
  compareInvMovAlmacen(a1: InvMovAlmacen, a2: InvMovAlmacen): boolean{
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
}

