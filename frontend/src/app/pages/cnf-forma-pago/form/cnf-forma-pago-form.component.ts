import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfFormaPago } from '../cnf-forma-pago.model';
import { CnfFormaPagoService } from '../cnf-forma-pago.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfEmpresa } from "../../cnf-empresa/cnf-empresa.model";
import { CnfEmpresaService } from "../../cnf-empresa/cnf-empresa.service";


@Component({
  selector: 'app-cnf-forma-pago-form',
  templateUrl: './cnf-forma-pago-form.component.html',
  styleUrls: ['./cnf-forma-pago-form.component.css']
})
export class CnfFormaPagoFormComponent implements OnInit{

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
  public model : CnfFormaPago = new CnfFormaPago();
  selectDefaultCnfEmpresa:any={id:0,name:"- Seleccione -"};listCnfEmpresa:any;
cnfEmpresa:CnfEmpresa = new CnfEmpresa();
loadingCnfEmpresa: boolean = false;
  protected redirect: string = "/cnf-forma-pago";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfFormaPagoService:CnfFormaPagoService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfEmpresaService : CnfEmpresaService,
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
    this.getListCnfEmpresa();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfFormaPagoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfFormaPagoService.save(this.model).subscribe(m => {
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
  getListCnfEmpresa(){
    this.loadingCnfEmpresa = true;
    console.log(this.chargingsb);
    return this.cnfEmpresaService.getAllDataCombo().subscribe(data=>{
      this.listCnfEmpresa = data;
      this.loadingCnfEmpresa = false;
    })
    
  }
  compareCnfEmpresa(a1: CnfEmpresa, a2: CnfEmpresa): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

