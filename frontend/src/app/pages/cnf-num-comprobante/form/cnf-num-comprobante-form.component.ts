import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfNumComprobante } from '../cnf-num-comprobante.model';
import { CnfNumComprobanteService } from '../cnf-num-comprobante.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfTipoComprobante } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { CnfTipoComprobanteService } from "../../cnf-tipo-comprobante/cnf-tipo-comprobante.service";
import { CnfLocal } from "../../cnf-local/cnf-local.model";
import { CnfLocalService } from "../../cnf-local/cnf-local.service";


@Component({
  selector: 'app-cnf-num-comprobante-form',
  templateUrl: './cnf-num-comprobante-form.component.html',
  styleUrls: ['./cnf-num-comprobante-form.component.css']
})
export class CnfNumComprobanteFormComponent implements OnInit{

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
  public model : CnfNumComprobante = new CnfNumComprobante();
  selectDefaultCnfTipoComprobante:any={id:0,name:"- Seleccione -"};listCnfTipoComprobante:any;
cnfTipoComprobante:CnfTipoComprobante = new CnfTipoComprobante();
loadingCnfTipoComprobante: boolean = false;
selectDefaultCnfLocal:any={id:0,name:"- Seleccione -"};listCnfLocal:any;
cnfLocal:CnfLocal = new CnfLocal();
loadingCnfLocal: boolean = false;
  protected redirect: string = "/cnf-num-comprobante";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfNumComprobanteService:CnfNumComprobanteService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfTipoComprobanteService : CnfTipoComprobanteService,
              private cnfLocalService : CnfLocalService,
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
    this.getListCnfLocal();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfNumComprobanteService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfNumComprobanteService.save(this.model).subscribe(m => {
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
}

