import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfMenuTipoSistema } from '../cnf-menu-tipo-sistema.model';
import { CnfMenuTipoSistemaService } from '../cnf-menu-tipo-sistema.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { SegMenu } from "../../seg-menu/seg-menu.model";
import { SegMenuService } from "../../seg-menu/seg-menu.service";


@Component({
  selector: 'app-cnf-menu-tipo-sistema-form',
  templateUrl: './cnf-menu-tipo-sistema-form.component.html',
  styleUrls: ['./cnf-menu-tipo-sistema-form.component.css']
})
export class CnfMenuTipoSistemaFormComponent implements OnInit{

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
  public model : CnfMenuTipoSistema = new CnfMenuTipoSistema();
  selectDefaultSegMenu:any={id:0,name:"- Seleccione -"};listSegMenu:any;
segMenu:SegMenu = new SegMenu();
loadingSegMenu: boolean = false;
  protected redirect: string = "/cnf-menu-tipo-sistema";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfMenuTipoSistemaService:CnfMenuTipoSistemaService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private segMenuService : SegMenuService,
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
    this.getListSegMenu();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfMenuTipoSistemaService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfMenuTipoSistemaService.save(this.model).subscribe(m => {
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
  getListSegMenu(){
    this.loadingSegMenu = true;
    console.log(this.chargingsb);
    return this.segMenuService.getAllDataCombo().subscribe(data=>{
      this.listSegMenu = data;
      this.loadingSegMenu = false;
    })
    
  }
  compareSegMenu(a1: SegMenu, a2: SegMenu): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

