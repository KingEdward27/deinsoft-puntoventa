import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActContratoCorte } from '../act-contrato-corte.model';
import { ActContratoCorteService } from '../act-contrato-corte.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { ActContrato } from "../../act-contrato/act-contrato.model";
import { ActContratoService } from "../../act-contrato/act-contrato.service";
import { SegUsuario } from "../../seg-usuario/seg-usuario.model";
import { SegUsuarioService } from "../../seg-usuario/seg-usuario.service";


@Component({
  selector: 'app-act-contrato-corte-form',
  templateUrl: './act-contrato-corte-form.component.html',
  styleUrls: ['./act-contrato-corte-form.component.css'],
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class ActContratoCorteFormComponent implements OnInit{

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
  public model : ActContratoCorte = new ActContratoCorte();
  selectDefaultActContrato:any={id:0,name:"- Seleccione -"};listActContrato:any;
actContrato:ActContrato = new ActContrato();
loadingActContrato: boolean = false;
selectDefaultSegUsuario:any={id:0,name:"- Seleccione -"};listSegUsuario:any;
segUsuario:SegUsuario = new SegUsuario();
loadingSegUsuario: boolean = false;
  protected redirect: string = "/act-contrato-corte";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private actContratoCorteService:ActContratoCorteService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private actContratoService : ActContratoService,
              private segUsuarioService : SegUsuarioService,
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
    this.getListActContrato();
    this.getListSegUsuario();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actContratoCorteService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.actContratoCorteService.save(this.model).subscribe(m => {
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
  getListActContrato(){
    this.loadingActContrato = true;
    console.log(this.chargingsb);
    return this.actContratoService.getAllDataCombo().subscribe(data=>{
      this.listActContrato = data;
      this.loadingActContrato = false;
    })
    
  }
  compareActContrato(a1: ActContrato, a2: ActContrato): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
getListSegUsuario(){
    this.loadingSegUsuario = true;
    console.log(this.chargingsb);
    return this.segUsuarioService.getAllDataCombo().subscribe(data=>{
      this.listSegUsuario = data;
      this.loadingSegUsuario = false;
    })
    
  }
  compareSegUsuario(a1: SegUsuario, a2: SegUsuario): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

