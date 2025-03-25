import { Component, HostListener, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { InvMovAlmacen } from '@/business/model/inv-mov-almacen.model';
import { InvTipoMovAlmacen } from '@/business/model/inv-tipo-mov-almacen.model';
import { CnfMaestro } from '@/business/model/cnf-maestro.model';
import { CnfLocal } from '@/business/model/cnf-local.model';
import { CnfTipoComprobante } from '@/business/model/cnf-tipo-comprobante.model';
import { InvAlmacen } from '@/business/model/inv-almacen.model';
import { InvMovAlmacenService } from '@/business/service/inv-mov-almacen.service';
import { UtilService } from '@services/util.service';
import { InvTipoMovAlmacenService } from '@/business/service/inv-tipo-mov-almacen.service';
import { CnfMaestroService } from '@/business/service/cnf-maestro.service';
import { CnfLocalService } from '@/business/service/cnf-local.service';
import { CnfTipoComprobanteService } from '@/business/service/cnf-tipo-comprobante.service';
import { InvAlmacenService } from '@/business/service/inv-almacen.service';
import { InvMovAlmacenDetService } from '@/business/service/inv-mov-almacen-det.service';


@Component({
  selector: 'app-inv-mov-almacen-form',
  templateUrl: './inv-mov-almacen-form.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class InvMovAlmacenForm2Component implements OnInit {

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
  id: string = "";

  //variables propias
  public model: InvMovAlmacen = new InvMovAlmacen();
  selectDefaultInvTipoMovAlmacen: any = { id: 0, name: "- Seleccione -" }; 
  listInvTipoMovAlmacen: any;
  invTipoMovAlmacen: InvTipoMovAlmacen = new InvTipoMovAlmacen();
  loadingInvTipoMovAlmacen: boolean = false;
  
  selectDefaultCnfMaestro: any = { id: 0, name: "- Seleccione -" }; listCnfMaestro: any;
  cnfMaestro: CnfMaestro = new CnfMaestro();
  loadingCnfMaestro: boolean = false;
  selectDefaultCnfLocal: any = { id: 0, name: "- Seleccione -" }; listCnfLocal: any;
  cnfLocal: CnfLocal = new CnfLocal();
  loadingCnfLocal: boolean = false;
  selectDefaultCnfTipoComprobante: any = { id: 0, name: "- Seleccione -" }; listCnfTipoComprobante: any;
  cnfTipoComprobante: CnfTipoComprobante = new CnfTipoComprobante();
  loadingCnfTipoComprobante: boolean = false;
  selectDefaultInvAlmacen: any = { id: 0, name: "- Seleccione -" }; listInvAlmacen: any;
  invAlmacen: InvAlmacen = new InvAlmacen();
  loadingInvAlmacen: boolean = false;
  listInvMovAlmacenDet: any;
  selectedOptionInvMovAlmacenDet: any;
  protected redirect: string = "/inv-mov-almacen";
  selectedOption: any;
  passwordRepeat: any;
  isAdding: boolean = false;
  isSave: boolean = false;
  public modelOrig: InvMovAlmacen = new InvMovAlmacen();
  constructor(private invMovAlmacenService: InvMovAlmacenService,
    private router: Router,
    private utilService: UtilService,
    private invTipoMovAlmacenService: InvTipoMovAlmacenService,
    private cnfMaestroService: CnfMaestroService,
    private cnfLocalService: CnfLocalService,
    private cnfTipoComprobanteService: CnfTipoComprobanteService,
    private invAlmacenService: InvAlmacenService,
    private invMovAlmacenDetService: InvMovAlmacenDetService,
    private route: ActivatedRoute,) {
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.loadData();
  }
  getBack() {
    console.log("wa");
    
    this.router.navigate(['/mov-almacen']);
  }
  loadData() {
    this.getListInvTipoMovAlmacen();
    this.getListCnfMaestro();
    this.getListCnfLocal();
    this.getListCnfTipoComprobante();
    this.getListInvAlmacen();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if (!this.id) {
        this.isDataLoaded = true;
      }
      if (this.id) {
        this.invMovAlmacenService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      } 

    })

  }
  agregarInvMovAlmacenDet(): void {
    this.router.navigate(["/add-new-mov-almacen-det", { idParent: this.model.id }]);
  }
  editarInvMovAlmacenDet(invMovAlmacenDet: any): void {
    this.router.navigate(["/add-new-mov-almacen-det", { idParent: this.model.id, id: invMovAlmacenDet.id }]);
  }
  quitarInvMovAlmacenDet(e: any): void {
    if (!this.id) {
      this.model.listInvMovAlmacenDet = this.model.listInvMovAlmacenDet.filter(item => item.id != e.id);
    }
    if (this.id) {
      this.utilService.confirmDelete(e).then((result) => {
        if (result) {
          this.invMovAlmacenDetService.delete(e.id.toString()).subscribe(() => {
            this.utilService.msgOkDelete();
            this.loadData();
          }, err => {
            if (err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")) {
              this.utilService.msgProblemDelete();
            }
          });
        }

      });
    }

  }
  public save(): void {
    this.invMovAlmacenService.save(this.model).subscribe(m => {
      console.log(m);
      this.isOk = true;
      Swal.fire('Registro', `Grabado con �xito`, 'success');
      this.router.navigate([this.redirect]);
    }, err => {
      if (err.status === 400) {
        this.error = err.error;
        console.log(this.error);
        if (err.error.trace.includes("Date")) {
          Swal.fire({
            title: 'Problema al grabar',
            text: `Datos de tipo fecha tienen formato incorrecto`,
            icon: 'warning'
          });
        }
      }
    });
  }
  getListInvTipoMovAlmacen() {
    this.loadingInvTipoMovAlmacen = true;
    console.log(this.chargingsb);
    return this.invTipoMovAlmacenService.getAllDataCombo().subscribe(data => {
      this.listInvTipoMovAlmacen = data;
      this.loadingInvTipoMovAlmacen = false;
    })

  }
  compareInvTipoMovAlmacen(a1: InvTipoMovAlmacen, a2: InvTipoMovAlmacen): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfMaestro() {
    this.loadingCnfMaestro = true;
    console.log(this.chargingsb);
    return this.cnfMaestroService.getAllDataCombo().subscribe(data => {
      this.listCnfMaestro = data;
      this.loadingCnfMaestro = false;
    })

  }
  compareCnfMaestro(a1: CnfMaestro, a2: CnfMaestro): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfLocal() {
    this.loadingCnfLocal = true;
    console.log(this.chargingsb);
    return this.cnfLocalService.getAllDataCombo().subscribe(data => {
      this.listCnfLocal = data;
      this.loadingCnfLocal = false;
    })

  }
  compareCnfLocal(a1: CnfLocal, a2: CnfLocal): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfTipoComprobante() {
    this.loadingCnfTipoComprobante = true;
    return this.cnfTipoComprobanteService.getAllDataComboMovAlmacen().subscribe(data => {
      this.listCnfTipoComprobante = data;
      this.loadingCnfTipoComprobante = false;
    })

  }
  compareCnfTipoComprobante(a1: CnfTipoComprobante, a2: CnfTipoComprobante): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListInvAlmacen() {
    this.loadingInvAlmacen = true;
    return this.invAlmacenService.getAllDataCombo().subscribe(data => {
      this.listInvAlmacen = data;
      this.loadingInvAlmacen = false;
    })

  }
  compareInvAlmacen(a1: InvAlmacen, a2: InvAlmacen): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
}

