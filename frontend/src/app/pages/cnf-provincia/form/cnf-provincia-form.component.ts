import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfProvincia } from '../cnf-provincia.model';
import { CnfProvinciaService } from '../cnf-provincia.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfRegion } from "../../cnf-region/cnf-region.model";
import { CnfRegionService } from "../../cnf-region/cnf-region.service";


@Component({
  selector: 'app-cnf-provincia-form',
  templateUrl: './cnf-provincia-form.component.html',
  styleUrls: ['./cnf-provincia-form.component.css']
})
export class CnfProvinciaFormComponent implements OnInit{

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
  public model : CnfProvincia = new CnfProvincia();
  selectDefaultCnfRegion:any={id:0,name:"- Seleccione -"};listCnfRegion:any;
cnfRegion:CnfRegion = new CnfRegion();
loadingCnfRegion: boolean = false;
  protected redirect: string = "/cnf-provincia";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfProvinciaService:CnfProvinciaService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfRegionService : CnfRegionService,
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
    this.getListCnfRegion();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfProvinciaService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfProvinciaService.save(this.model).subscribe(m => {
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
  getListCnfRegion(){
    this.loadingCnfRegion = true;
    console.log(this.chargingsb);
    return this.cnfRegionService.getAllDataCombo().subscribe(data=>{
      this.listCnfRegion = data;
      this.loadingCnfRegion = false;
    })
    
  }
  compareCnfRegion(a1: CnfRegion, a2: CnfRegion): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

