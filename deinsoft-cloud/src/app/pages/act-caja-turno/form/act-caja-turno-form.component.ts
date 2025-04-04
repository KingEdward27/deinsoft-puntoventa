import { Component, HostListener, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { Observable } from 'rxjs';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { SegUsuarioService } from '@/business/service/seg-usuario.service';
import { SegUsuario } from '@/business/model/seg-usuario.model';
import { UtilService } from '@services/util.service';
import { ActCajaTurno } from '@/business/model/act-caja-turno.model';
import { ActCajaTurnoService } from '@/business/service/act-caja-turno.service';
import { ActCaja } from '@/business/model/act-caja.model';
import { ActCajaService } from '@/business/service/act-caja.service';
import { AppService } from '@services/app.service';
import dayjs from 'dayjs';
import { CustomDateParserFormatter } from '@/base/util/CustomDate';
import { ActCajaOperacionService } from '@/business/service/act-caja-operacion.service';
import { CnfLocalService } from '@/business/service/cnf-local.service';
import { CnfLocal } from '@/business/model/cnf-local.model';

@Component({
  selector: 'app-act-caja-turno-form',
  templateUrl: './act-caja-turno-form.component.html'
})
export class ActCajaTurnoFormComponent implements OnInit {

  //generic variables
  error: any;
  selectedItemsList = [];
  checkedIDs = [];
  chargingsb: boolean = true;
  isDataLoaded: Boolean = false;
  isOk: boolean = false;
  isWarning: boolean = false;
  isDanger: boolean = false;
  message: any = "";
  id: any = "";

  //variables propias
  public model: ActCajaTurno = new ActCajaTurno();

  selectDefaultSegUsuario: any = { id: 0, name: "- Seleccione -" }; 
  listSegUsuario: any;
  segUsuario: SegUsuario = new SegUsuario();
  loadingSegUsuario: boolean = false;

  selectDefaultActCaja: any = { id: 0, name: "- Seleccione -" }; 
  listActCaja: any;
  actCaja: SegUsuario = new SegUsuario();
  loadingActCaja: boolean = false;
  selectDefaultCnfLocal: any = { id: 0, nombre: "- Seleccione -" }; 
  listCnfLocal: any = [];
  loadingCnfLocal: boolean = false;
  protected redirect: string = "/act-caja-turno";
  selectedOption: any;
  passwordRepeat: any;

  constructor(private actCajaTurnoService: ActCajaTurnoService,
    private router: Router,
    private utilService: UtilService,
    private segUsuarioService: SegUsuarioService,
    private actCajaService:ActCajaService,
    private route: ActivatedRoute,
    private cnfLocalService: CnfLocalService,
    private ngbCalendar: NgbCalendar,
    private dateAdapter: NgbDateAdapter<dayjs.Dayjs>,
    private appService:AppService,
    private actCajaOperacionService:ActCajaOperacionService) {
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.loadData();
  }
  getBack() {
    this.router.navigate([this.redirect]);
  }
  leftPad(str: string, len: number, ch='.'): string {
    len = len - str.length + 1;
    return len > 0 ?
      new Array(len).join(ch) + str : str;
  }
  formatDateAndTime(date: Date | null): string {
    console.log(date);
    
    return date ? this.leftPad((date.getDate()).toString(),2,'0') 
    + "/" + this.leftPad((date.getMonth() + 1).toString(),2,'0') 
    + "/" + date.getFullYear().toString()
    + " " + this.leftPad(date.getHours().toString(),2,'0') 
    + ":" + this.leftPad(date.getMinutes().toString(),2,'0') 
    + ":" + this.leftPad(date.getSeconds().toString(),2,'0') :''
  }
  loadData() {
    this.getListCnfLocal();
    
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if (!this.id) {
        this.isDataLoaded = true;
      }
      if (this.id) {
        this.actCajaTurnoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          this.model.fechaCierre = this.formatDateAndTime(new Date())
          //this.titulo = 'Editar ' + this.nombreModel;
          let total = this.model.montoApertura
          this.actCajaOperacionService.getAllByActCajaTurnoId(this.id).subscribe(data =>{
            console.log(data);
            this.getDataByLocal();
            data.forEach(element => {
              if(element.flagIngreso == '1') {
                total = total + element.monto
              }else{
                total = total - element.monto
              }
            });
            this.model.montoCierre = Math.round((total + Number.EPSILON) * 100) / 100;
          })
        });
        
      } else {
        this.model.fechaApertura = this.formatDateAndTime(new Date())
      }

    })

  }
  public save(): void {
    //validar que no exista caja apertrada para el usuario o la caja
    //filtrar lista cajas turno por empresa
    //traer total caja en input monto cierre
    this.utilService.confirmOperation(null).then((result) => {
      if(result){
        this.model.estado = this.model.fechaCierre?'0':'1'
        this.actCajaTurnoService.save(this.model).subscribe(m => {
          this.utilService.msgOkSave()
          this.router.navigate([this.redirect]);
        }, err => {
          if (err.status === 422) {
            this.error = err.error;
            console.log(this.error);
          }
        });
      }
    })
    
  }
  getListActCaja() {
    this.loadingActCaja = true;
    let user = this.appService.getProfile()
    let cnfEmpresa = user.profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.actCajaService.getAllDataCombo().subscribe(data => {
        this.listActCaja = data;
        this.loadingActCaja = false;
      })
    }else{
      return this.actCajaService.getAllByCnfLocalId(cnfEmpresa,this.model.actCaja.cnfLocal.id).subscribe(data => {
          this.listActCaja = data;
          this.loadingActCaja = false;
        })
      }
  }
  compareActCaja(a1: ActCaja, a2: ActCaja): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListSegUsuario() {
    this.loadingSegUsuario = true;
    let user = this.appService.getProfile()
    let cnfEmpresa = user.profile.split("|")[1];
      return this.segUsuarioService.getAllDataByEmpresaAndLocal(this.model.actCaja.cnfLocal.id).subscribe(data => {
        this.listSegUsuario = data;
        this.loadingSegUsuario = false;
      })
  }
  compareSegUsuario(a1: SegUsuario, a2: SegUsuario): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }

  getListCnfLocal() {
    this.loadingCnfLocal = true;
    let user = this.appService.getProfile();
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.cnfLocalService.getAllDataCombo().subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;
      })
    }else{
      return this.cnfLocalService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;

        
        if(this.listCnfLocal.length == 1) {
          this.cnfLocalService.getData(this.listCnfLocal[0].id).subscribe(data => {
            this.model.actCaja.cnfLocal = data
            this.getDataByLocal();
            
          })
        }
      })
    }
    

  }

  getDataByLocal(){
    this.getListActCaja();
    this.getListSegUsuario();

  }
  compareCnfLocal(a1: CnfLocal, a2: CnfLocal): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
}

