import { Component, HostListener, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { Observable, of } from 'rxjs';

import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter, NgbModal, NgbModalConfig, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { CnfLocal } from '@/business/model/cnf-local.model';
import { CnfMoneda } from '@/business/model/cnf-moneda.model';
import { CnfFormaPago } from '@/business/model/cnf-forma-pago.model';
import { CnfMaestro } from '@/business/model/cnf-maestro.model';
import { CnfTipoComprobante } from '@/business/model/cnf-tipo-comprobante.model';
import { InvAlmacen } from '@/business/model/inv-almacen.model';
import { CnfMaestroService } from '@/business/service/cnf-maestro.service';
import { CnfFormaPagoService } from '@/business/service/cnf-forma-pago.service';
import { CnfMonedaService } from '@/business/service/cnf-moneda.service';
import { CnfLocalService } from '@/business/service/cnf-local.service';
import { CnfTipoComprobanteService } from '@/business/service/cnf-tipo-comprobante.service';
import { InvAlmacenService } from '@/business/service/inv-almacen.service';
import { ActComprobanteDetalleService } from '@/business/service/act-comprobante-detalle.service';
import { UtilService } from '@services/util.service';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { catchError, debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';
import { CnfProductoService } from '@/business/service/cnf-producto.service';
import { CnfProducto } from '@/business/model/cnf-producto.model';
import { ActComprobanteDetalle } from '@/business/model/act-comprobante-detalle.model';
import { CnfImpuestoCondicion } from '@/business/model/cnf-impuesto-condicion.model';
import { CnfImpuestoCondicionService } from '@/business/service/cnf-impuesto-condicion.service';
import { CnfNumComprobanteService } from '@/business/service/cnf-num-comprobante.service';
import { CommonService } from '@/base/services/common.service';
import { UpdateParam } from '@/base/components/model/UpdateParam';
import dayjs from 'dayjs';
import { AppService } from '@services/app.service';
import { ActComprobante } from '@pages/act-comprobante/act-comprobante.model';
import { ActComprobanteService } from '@pages/act-comprobante/act-comprobante.service';
import { MessageModalComponent } from '@pages/act-comprobante/modal/message-modal.component';
import { CommonReportFormComponent, MyBaseComponentDependences } from '@pages/reports/base/common-report.component';



@Component({
  selector: 'app-act-pago-programacion-list',
  templateUrl: './act-pago-programacion-list.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class ActPagoProgramacionListFormComponent extends CommonReportFormComponent implements OnInit {

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
  searchFailed = false;
  //variables propias
  selectDefaultActComprobante: any = { id: 0, nombre: "- Seleccione -" }; listActComprobante: any;
  actComprobante: ActComprobante = new ActComprobante();
  loadingActComprobante: boolean = false;
  selectDefaultCnfMaestro: any = { id: 0, nombre: "- Seleccione -" }; listCnfMaestro: any;
  cnfMaestro: CnfMaestro = new CnfMaestro();
  loadingCnfMaestro: boolean = false;
  selectDefaultCnfFormaPago: any = { id: 0, nombre: "- Seleccione -" }; listCnfFormaPago: any;
  cnfFormaPago: CnfFormaPago = new CnfFormaPago();
  loadingCnfFormaPago: boolean = false;
  selectDefaultCnfMoneda: any = { id: 0, nombre: "- Seleccione -" }; listCnfMoneda: any;
  cnfMoneda: CnfMoneda = new CnfMoneda();
  loadingCnfMoneda: boolean = false;
  selectDefaultCnfLocal: any = { id: 0, nombre: "- Seleccione -" }; listCnfLocal: any;
  cnfLocal: CnfLocal = new CnfLocal();
  loadingCnfLocal: boolean = false;
  selectDefaultCnfTipoComprobante: any = { id: 0, nombre: "- Seleccione -" }; listCnfTipoComprobante: any;
  cnfTipoComprobante: CnfTipoComprobante = new CnfTipoComprobante();
  loadingCnfTipoComprobante: boolean = false;
  selectDefaultInvAlmacen: any = { id: 0, nombre: "- Seleccione -" }; listInvAlmacen: any;
  invAlmacen: InvAlmacen = new InvAlmacen();
  loadingInvAlmacen: boolean = false;
  listActComprobanteDetalle: any;
  selectedOptionActComprobanteDetalle: any;
  protected redirect: string = "/act-comprobante";
  selectedOption: any;
  passwordRepeat: any;
  isAdding: boolean = false;
  isSave: boolean = false;
  public modelOrig: ActComprobante = new ActComprobante();
  cnfProducto: any;
  formatter = (x: { nombre: string }) => x.nombre;
  dataTable:any;
  loadingCnfImpuestoCondicion: boolean = false;
  selectDefaultImpuestoCondicion: any = { id: 0, nombre: "- Seleccione -" };
  listImpuestoCondicion: any;
  public modalRef!: NgbModalRef;
  listData: any;
  total :any = 0;
  totalMontos :any = 0;
  totalPendiente :any = 0;
  constructor(public deps: MyBaseComponentDependences) {
    super(deps);
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.titleExport = "Lista de Cuentas x Cobrar/x Pagar"
    super.ngOnInit();
    //this.getListData();
  }
  getListData() {
    this.model.flagIsventa = '1';
    this.totalMontos = 0
    this.totalPendiente = 0
    this.indexInputs = [8]
    this.model.fechaVencimiento = this.model.fechaVencimiento?this.model.fechaVencimiento:''
    return this.deps.actPagoProgramacionService
    .getAllByCnfMaestroId(this.model.cnfMaestro.id,this.model.fechaVencimiento).subscribe(data => {
      this.listData = data;
      this.loadingCnfMaestro = false;
      setTimeout(() => {
        this.dataTable = $('#dtDataListActPagoProgramacion').DataTable(this.datablesSettingsWithInputs);
      }, 1);
      this.dataTable?.destroy();
      this.listData.forEach(element => {
        this.totalMontos = this.totalMontos + element.monto
        this.totalPendiente = this.totalPendiente + element.montoPendiente
      });
      console.log(data);
    })
  }
  print(item: any) {
    this.modalRef = this.deps.modalService.open(MessageModalComponent);
    this.modalRef.componentInstance.message = "Impresión de comprobante";
    this.modalRef.componentInstance.id = item.id;

  }
  sendApi(item: any) {
    this.deps.utilService.confirmOperation(null).then((result) => {
      if (result) {
        this.deps.actComprobanteService.sendApi("act_comprobante", item.id.toString()).subscribe(() => {
          this.deps.utilService.msgOkOperation();
          this.loadData();
        });
      }

    });
  }
  export() {
    var contentType = 'application/pdf';
    var extension = "pdf";
    this.deps.actComprobanteService.genReportExcel(this.model).subscribe(data => {
      if (data.body.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ||
        data.body.type == "application/octet-stream") {
        contentType = data.body.type;
        extension = "xlsx";
      }
      const blob = new Blob([data.body], { type: contentType });
      this.generateAttachment(blob, extension);
    })
    
  }
  calculateFromTotal(){
    let total = this.total;
    this.listData.forEach((element: any) => {
      console.log(total);
      if(total >= element.montoPendiente){
        element.amtToPay = element.montoPendiente;
        total = total - element.montoPendiente;
      }else{
        element.amtToPay = total;
        total = 0;
      }
    });
  }
  onChangeTotal(event: any): void {  
    this.calculateFromTotal();
  }
  save() {
    if(!this.listData || this.listData?.length == 0){
      this.deps.utilService.msgProblemNoItems();
      return;
    }
    if(!this.total || this.total == 0){
      Swal.fire('Problema para continuar', `Debe ingresar un monto a pagar`, 'error');
      return;
    }
    let error;
    // this.listData.forEach((element: any) => {
    //   if(element.amtToPay == 0){
    //     this.deps.utilService.msgProblemItemsCero();
    //     error = true;
    //   }
    // });
    if(!error){
      console.log(this.listData);
      this.deps.actPagoService.saveFromList(this.listData).subscribe(m => {
        console.log(m);
        this.isOk = true;
        this.deps.utilService.msgOkSave();
        window.location.reload();
      }, err => {
        console.log(err);
      });
    }
    

  }
}

