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
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { ActComprobante } from '@pages/act-comprobante/act-comprobante.model';
import { MessageModalComponent } from '@pages/act-comprobante/modal/message-modal.component';
import { CommonReportFormComponent, MyBaseComponentDependences } from '@pages/reports/base/common-report.component';
import { ActPagoModalComponent } from '../act-pago/act-pago-modal/act-pago-modal.component';
import { ActPagoDetalle } from '../../business/model/act-pago-detalle.model';


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
    

    this.selectThisMonth();
    // this.model.fechaVencimiento 
    // = this.deps.dateAdapter.toModel(this.deps.ngbCalendar.getToday()).toString();
    super.ngOnInit();
    //this.getListData();
  }
  selectThisMonth() {
    let year = new Date().getFullYear();
    let month = new Date().getMonth()+1;
    let day = new Date(year, month, 0).getDate();
    
    this.model.fechaVencimiento  = this.deps.dateAdapter
    .toModel({year: year, month: month, day: day}).toString();
}
  getListData() {
    if (!this.model.cnfMaestro.id) {
      this.deps.utilService.msgWarning("No puede continuar","Debe seleccionar al cliente o proveedor")
      return
    }
    this.model.flagIsventa = '1';
    this.totalMontos = 0
    this.totalPendiente = 0
    this.indexInputs = [8]
    this.model.fechaVencimiento = this.model.fechaVencimiento?this.model.fechaVencimiento:''
    return this.deps.actPagoProgramacionService
    .getAllByCnfMaestroId(this.model.cnfMaestro.id,this.model.fechaVencimiento,this.model.cnfLocal.id, true).subscribe(data => {
      this.listData = data;
      this.loadingCnfMaestro = false;
      // setTimeout(() => {
      //   this.dataTable = $('#dtDataListActPagoProgramacion').DataTable(this.datablesSettingsWithInputs);
      // }, 1);
      // this.dataTable?.destroy();
      this.listData.forEach(element => {
        console.log(element);
        
        this.totalMontos = this.totalMontos + element.monto
        this.totalPendiente = this.totalPendiente + element.montoPendiente
      });
    })
  }
  // print(item: any) {
  //   this.modalRef = this.deps.modalService.open(MessageModalComponent);
  //   this.modalRef.componentInstance.message = "Impresión de comprobante";
  //   this.modalRef.componentInstance.id = item.id;

  // }
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
      this.generateAttachment("Cuentas por cobrar", blob, extension);
    })
    
  }
  calculateFromTotal(){
    console.log(this.total);
    
    let total = this.total;
    this.listData.forEach((element: any) => {
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
  async preSave() {
    if(!this.listData || this.listData?.length == 0){
      this.deps.utilService.msgProblemNoItems();
      return;
    }
    // if(!this.total || this.total == 0){
    //   Swal.fire('Problema para continuar', `Debe ingresar un monto a pagar`, 'error');
    //   return;
    // }
    let error;
    // this.listData.forEach((element: any) => {
    //   if(element.amtToPay == 0){
    //     this.deps.utilService.msgProblemItemsCero();
    //     error = true;
    //   }
    // });
    let items = this.listData.filter(data => data.amtToPay > 0);

    let itemsActComprobante = this.listData.filter(data => data.amtToPay > 0 && data.actComprobante);

    let itemsActContrato = this.listData.filter(data => data.amtToPay > 0 && data.actContrato);
    if (items.length == 0) {
      this.deps.utilService.msgProblemNoItems();
      return;
    }
    if (itemsActComprobante.length > 0 && itemsActContrato.length > 0) {
      this.deps.utilService.msgHTTP400WithMessage("No puede hacer el pago de contratos y ventas en un solo comprobante");
      return;
    }
    if(!error){
      this.modalRef = this.deps.modalService.open(ActPagoModalComponent, {
        size: 'lg',
        });
        let list : any[] = [];
        this.total = 0;
        await this.listData.forEach(element => {
          if (element.amtToPay > 0) {
             let actPagoDetalle = new ActPagoDetalle();
             actPagoDetalle.montoDeuda = element.monto;
             actPagoDetalle.monto = (element.amtToPay > actPagoDetalle.montoDeuda? actPagoDetalle.montoDeuda: element.amtToPay);
             actPagoDetalle.actPagoProgramacion = element;
             this.modalRef.componentInstance.model.cnfMaestro = element.actContrato?element.actContrato.cnfMaestro : element.actComprobante.cnfMaestro;
             this.total = this.total + actPagoDetalle.monto;
             
             list.push(actPagoDetalle);
          }
        });
        this.modalRef.componentInstance.model.listActPagoDetalle = list;
        this.modalRef.componentInstance.model.subtotal = this.total / 1.18;
        this.modalRef.componentInstance.model.igv = this.total - this.modalRef.componentInstance.model.subtotal;
        this.modalRef.componentInstance.model.total = this.total;
      ;
      // this.modalRef.componentInstance.id = m.id;
      this.modalRef.closed.subscribe(result => {
        this.deps.router.navigate(["/cuentas-cobrar"]);
        // this.model.cnfBpartner = 
      })
      // this.deps.actPagoService.saveFromList(this.listData).subscribe(m => {
      //   console.log(m);
      //   this.isOk = true;
      //   this.deps.utilService.msgOkSave();
      //   window.location.reload();
      // }, err => {
      //   console.log(err);
      // });
    }
    

  }
  onKeyAmtToPay (event: any) {
    if (event.key == "Enter") { 
      this.preSave();
    }
  }
  onSelectRow ($event:any, item: any) {
    if ($event.srcElement.cellIndex) {
      if (item.amtToPay == 0 || !item.amtToPay) {
        item.amtToPay = item.montoPendiente;
      } else {
        item.amtToPay = 0;
      }
    } else {
      $event.target.select();
    }
    
     
  }
  
}

