import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfRegion } from '../cnf-region.model';
import { CnfRegionService } from '../cnf-region.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfPais } from "../../cnf-pais/cnf-pais.model";
import { CnfPaisService } from "../../cnf-pais/cnf-pais.service";


@Component({
  selector: 'app-cnf-region-form',
  templateUrl: './cnf-region-form.component.html',
  styleUrls: ['./cnf-region-form.component.css']
})
export class CnfRegionFormComponent implements OnInit{

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
  public model : CnfRegion = new CnfRegion();
  selectDefaultCnfPais:any={id:0,name:"- Seleccione -"};listCnfPais:any;
cnfPais:CnfPais = new CnfPais();
loadingCnfPais: boolean = false;
  protected redirect: string = "/cnf-region";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfRegionService:CnfRegionService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfPaisService : CnfPaisService,
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
    this.getListCnfPais();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfRegionService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfRegionService.save(this.model).subscribe(m => {
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
  getListCnfPais(){
    this.loadingCnfPais = true;
    console.log(this.chargingsb);
    return this.cnfPaisService.getAllDataCombo().subscribe(data=>{
      this.listCnfPais = data;
      this.loadingCnfPais = false;
    })
    
  }
  compareCnfPais(a1: CnfPais, a2: CnfPais): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

