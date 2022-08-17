import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfDistrito } from '../cnf-distrito.model';
import { CnfDistritoService } from '../cnf-distrito.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';
import { CnfProvincia } from "../../cnf-provincia/cnf-provincia.model";
import { CnfProvinciaService } from "../../cnf-provincia/cnf-provincia.service";


@Component({
  selector: 'app-cnf-distrito-form',
  templateUrl: './cnf-distrito-form.component.html',
  styleUrls: ['./cnf-distrito-form.component.css']
})
export class CnfDistritoFormComponent implements OnInit{

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
  public model : CnfDistrito = new CnfDistrito();
  selectDefaultCnfProvincia:any={id:0,name:"- Seleccione -"};listCnfProvincia:any;
cnfProvincia:CnfProvincia = new CnfProvincia();
loadingCnfProvincia: boolean = false;
  protected redirect: string = "/cnf-distrito";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfDistritoService:CnfDistritoService, 
              private router: Router,
			   private utilService:UtilService, 
			                 private cnfProvinciaService : CnfProvinciaService,
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
    this.getListCnfProvincia();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfDistritoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfDistritoService.save(this.model).subscribe(m => {
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
  getListCnfProvincia(){
    this.loadingCnfProvincia = true;
    console.log(this.chargingsb);
    return this.cnfProvinciaService.getAllDataCombo().subscribe(data=>{
      this.listCnfProvincia = data;
      this.loadingCnfProvincia = false;
    })
    
  }
  compareCnfProvincia(a1: CnfProvincia, a2: CnfProvincia): boolean{
    if(a1===undefined && a2===undefined){
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
    ? false : a1.id === a2.id;
  }
}

