import { Component, HostListener, OnInit } from '@angular/core';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter, NgbModal, NgbModalConfig, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

import { CnfLocal } from '@/business/model/cnf-local.model';
import { CnfMoneda } from '@/business/model/cnf-moneda.model';
import { CnfFormaPago } from '@/business/model/cnf-forma-pago.model';
import { CnfMaestro } from '@/business/model/cnf-maestro.model';
import { CnfTipoComprobante } from '@/business/model/cnf-tipo-comprobante.model';
import { InvAlmacen } from '@/business/model/inv-almacen.model';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { ActComprobante } from '@pages/act-comprobante/act-comprobante.model';
import { ActComprobanteService } from '@pages/act-comprobante/act-comprobante.service';
import { MessageModalComponent } from '@pages/act-comprobante/modal/message-modal.component';
import { CommonReportFormComponent, MyBaseComponentDependences } from '@pages/reports/base/common-report.component';



@Component({
  selector: 'app-act-comprobante-report-contable',
  templateUrl: './act-comprobante-report-contable.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class ActComprobanteReportContableFormComponent extends CommonReportFormComponent implements OnInit {

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
  total:number;
  constructor(public deps: MyBaseComponentDependences) {
    super(deps);
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.titleExport = "Reporte de Ventas"
    super.ngOnInit();
    this.getListData();
  }
  getListData() {
    this.model.flagIsventa = '1';
    this.total = 0
    return this.deps.actComprobanteService.getReportContable(this.model.cnfLocal.id).subscribe(data => {
      console.log(data);
      
      this.listData = data;
      // this.loadingCnfMaestro = false;
      // setTimeout(() => {
      //   this.dataTable = $('#dtData').DataTable(this.datablesSettings);
      // }, 1);
      // this.dataTable?.destroy();
      // console.log(data);
      // this.listData.forEach(element => {
      //   this.total = this.total + element.total
      // });
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
  generateTxt(item : any, isVenta: string){
    this.model.periodo = item.periodo;
    this.model.flagIsventa = isVenta;
    this.deps.actComprobanteService.getTxtSire(this.model, 'blob').subscribe(data => {
      console.log(data);
      console.log(data.headers);
      let contentType = data.body.type;
      const blob = new Blob([data.body], { type: contentType });
      this.generateAttachment2(item.fileName, blob);
    })
  }
}

