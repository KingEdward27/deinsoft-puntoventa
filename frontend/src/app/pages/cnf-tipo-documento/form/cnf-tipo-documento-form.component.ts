import { Component,HostListener,OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfTipoDocumento } from '../cnf-tipo-documento.model';
import { CnfTipoDocumentoService } from '../cnf-tipo-documento.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { Observable } from 'rxjs';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { UtilService } from 'src/app/services/util/util.service';


@Component({
  selector: 'app-cnf-tipo-documento-form',
  templateUrl: './cnf-tipo-documento-form.component.html',
  styleUrls: ['./cnf-tipo-documento-form.component.css']
})
export class CnfTipoDocumentoFormComponent implements OnInit{

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
  public model : CnfTipoDocumento = new CnfTipoDocumento();
    protected redirect: string = "/cnf-tipo-documento";
  selectedOption:any;
  passwordRepeat:any;

  constructor(private cnfTipoDocumentoService:CnfTipoDocumentoService, 
              private router: Router,
			   private utilService:UtilService, 
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
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfTipoDocumentoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }
      
    })
    
  }
  public save(): void {
    this.cnfTipoDocumentoService.save(this.model).subscribe(m => {
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
  }
