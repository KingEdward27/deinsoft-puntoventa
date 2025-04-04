import { UpdateParam } from '@/base/components/model/UpdateParam';
import { CommonService } from '@/base/services/common.service';
import { AfterViewInit, Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
// import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbActiveModal, NgbCalendar, NgbDateAdapter, NgbDateParserFormatter, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'environments/environment';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import * as dayjs from 'dayjs';
import { MessageModalComponent } from '@pages/act-comprobante/modal/message-modal.component';
import { AppService } from '@services/app.service';
import { CnfLocalService } from '@/business/service/cnf-local.service';
import { CnfNumComprobanteService } from '@/business/service/cnf-num-comprobante.service';
import { CnfTipoComprobanteService } from '@/business/service/cnf-tipo-comprobante.service';
import { CnfImpuestoCondicionService } from '@/business/service/cnf-impuesto-condicion.service';
import { CnfLocal } from '@/business/model/cnf-local.model';
import { CnfTipoComprobante } from '@/business/model/cnf-tipo-comprobante.model';
import { ActComprobante } from '@pages/act-comprobante/act-comprobante.model';
import { UtilService } from '@services/util.service';
import { CnfMedioPagoService } from '../../../../business/service/cnf-medio-pago.service';
import { CnfMedioPago } from '@/business/model/cnf-medio-pago.model';
import { ActComprobanteService } from '@pages/act-comprobante/act-comprobante.service';
import { ActMedioPagoDetalle } from '@/business/model/act-medio-pago-detalle.model';




@Component({
  selector: 'act-pago-comprobante-modal',
  templateUrl: './act-pago-comprobante-modal.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class ActPagoComprobanteModalComponent implements OnInit {

  //generic variables
  error: any;
  headers :any;
  listDetail: any;
  load:boolean = false;
  listDetail2: any[][] = [];
  message:any;
  public model: ActComprobante = new ActComprobante();
  loadingCnfImpuestoCondicion: boolean = false;
  selectDefaultImpuestoCondicion: any = { id: 0, nombre: "- Seleccione -" };
  listImpuestoCondicion: any;

  selectDefaultCnfTipoComprobante: any = { id: 0, nombre: "- Seleccione -" }; 
  listCnfTipoComprobante: any;
  cnfTipoComprobante: CnfTipoComprobante = new CnfTipoComprobante();
  loadingCnfTipoComprobante: boolean = false;


  selectDefaultCnfLocal: any = { id: 0, nombre: "- Seleccione -" }; 
  listCnfLocal: any;
  cnfLocal: CnfLocal = new CnfLocal();
  loadingCnfLocal: boolean = false;
  listCnfMedioPago: any[]
  option: string = "1";
  @Output() result: EventEmitter<any> = new EventEmitter();
  public id = 0;
  public modalRef!: NgbModalRef;
  total: number = 0;
  vuelto: number = 0;
  constructor(public activeModal: NgbActiveModal,
    private actComprobanteService: ActComprobanteService,
    private cnfImpuestoCondicionService: CnfImpuestoCondicionService,
    private commonService: CommonService,
    private cnfTipoComprobanteService: CnfTipoComprobanteService,
    private cnfNumComprobanteService: CnfNumComprobanteService,
    private cnfLocalService: CnfLocalService,
    private cnfMedioPagoService: CnfMedioPagoService,
    private appService:AppService, 
    private dateAdapter: NgbDateAdapter<dayjs.Dayjs>,
    private ngbCalendar: NgbCalendar,
    private modalService: NgbModal, 
    private router: Router,
    private utilService:UtilService) {
    this.commonService.baseEndpoint = environment.apiUrl;
  }
  ngOnInit(): void {
    this.load = false;
    console.log(this.model);
    this.getListCnfLocal();
    this.getListCnfTipoComprobante();
    // this.getListInvAlmacen();
    this.getListImpuestoCondicion();
    this.getListCnfMedioPago();
    this.model.fecha = this.dateAdapter.toModel(this.ngbCalendar.getToday())!;
    this.total = this.model.total
    this.load = true;
    console.log(this.listDetail2);
  }
  save(){
    // this.result.emit(this.listDetail);
    // this.activeModal.close();
    this.model.numero = "1"
    let total = 0
    for(let element of this.listCnfMedioPago){
      if (element.amtToPay > 0) {
        total = total + element.amtToPay;
      }
      
    }
    if (total < this.model.total) {
      this.utilService.msgError("Monto apagar no puede ser menor que mont de la venta")
    }
    this.model.listActMedioPagoDetalle = []
    for(let element of this.listCnfMedioPago){
      if (element.amtToPay > 0) {
        let item = new ActMedioPagoDetalle();
        item.id = null
        item.cnfMedioPago = element;
        item.monto = element.amtToPay;
        this.model.listActMedioPagoDetalle.push(item);
      }
    }
    this.model.vuelto = this.vuelto
    this.model.numero = "1"
    if (this.model.listActComprobanteDetalle.length == 0) {
      this.error = []
      this.error.push("Debe agregar productos y servicios al comprobante")
      return;
    }
    this.saveComprobante();
  }

  saveComprobante() {
    console.log(this.model);
    this.actComprobanteService.save(this.model).subscribe(m => {
      console.log(m);
      this.model.id = m.id
      this.model.numero = m.numero;

      this.modalRef = this.modalService.open(MessageModalComponent);
      this.modalRef.componentInstance.message = "Documento " + m.serie + " - " + m.numero + " generado correctamente";
      this.modalRef.componentInstance.id = m.id;
      this.modalRef.closed.subscribe(result => {
        
        this.activeModal.close();
        this.router.navigate(["/venta"]);
      })

    }, err => {
      if (err.status === 422) {
        this.error = []
        console.log(err.error);

        for (var prop in err.error) {
          // console.log("Key:" + prop);
          // console.log("Value:" + err.error[prop]);
          this.error.push(err.error[prop])
        }
      }
    });
  }

  getListCnfLocal() {
    this.loadingCnfLocal = true;
    let user = this.appService.getProfile();
    console.log(user);
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
            this.model.cnfLocal = data
            
          })
          // this.model.cnfLocal.id = this.listCnfLocal[0].id;
          // if (this.listInvAlmacen.length == 1){
          //   this.model.invAlmacen.id = this.listInvAlmacen[0].id;
          // }
        }
      })
    }
    

  }
  compareCnfLocal(a1: CnfLocal, a2: CnfLocal): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }

  getListCnfMedioPago() {
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.cnfMedioPagoService.getAllData().subscribe(data => {
        this.listCnfMedioPago = data;
      })
    }else{
      return this.cnfMedioPagoService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfMedioPago = data;
      })
    }
    

  }
  getListImpuestoCondicion() {
    this.loadingCnfImpuestoCondicion = true;
    return this.cnfImpuestoCondicionService.getAllDataCombo().subscribe(data => {
      this.listImpuestoCondicion = data;
      this.loadingCnfImpuestoCondicion = false;
    })

  }
  getListCnfTipoComprobante() {
    this.loadingCnfTipoComprobante = true;
    return this.cnfTipoComprobanteService.getAllDataComboVentas().subscribe(data => {
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
  loadSerie() {
    this.model.serie = "";
    return this.cnfNumComprobanteService
      .getDataByCnfTipoComprobanteIdAndCnfLocalId(this.model.cnfTipoComprobante.id.toString()
        , this.model.cnfLocal.id.toString()).subscribe(data => {
          if (this.model.cnfTipoComprobante.id != 0 && data.length == 0) {
            this.utilService.msgWarning("Problemas de configuración","No se encontró serie configurada para el tipo de comprobante y local seleccionados")
          }
          this.model.serie = data[0].serie
        })
  }
  ticketChild(){
    let myMap = new Map();
    myMap.set("id", this.id);
    myMap.set("tipo", 2);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);
    mp.map = convMapDetail;
    this.commonService.updateParam = mp;
    
    this.commonService.genericPostRequest("/api/business/getpdflocal", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        this.generateAttachment(blob, extension);
      }

    });
    // console.log("enviando a imprimir: ",this.properties.listData);
  }
  a4() {
    let myMap = new Map();
    myMap.set("id", this.id);
    myMap.set("tipo", 1);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);

    mp.map = convMapDetail;
    this.commonService.genericPostRequest("/api/business/act-comprobante/getpdflocal", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        this.generateAttachment(blob, extension);
      }

    });
  }
  generateAttachment(blob: Blob, extension: string) {
    const data = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = data;
    link.download = "report." + extension;
    link.dispatchEvent(new MouseEvent('click', {
      bubbles: true, cancelable: true, view: window
    }));
    setTimeout(() => {
      window.URL.revokeObjectURL(data);
      link.remove
    }, 100);
  }
  onChangeAmt(item: CnfMedioPago, value: any) {
    console.log(item);
    let total = 0 //= this.model.total;
    let vuelto = 0;
    for(let element of this.listCnfMedioPago){
      if (element.amtToPay > 0) {
        total = total + element.amtToPay;
      }
      
    }
    console.log(total,this.total);
    this.vuelto = total - this.total
    
    if (total >= this.total) {
      this.total = this.model.total
    } else {
      this.total = this.model.total;
    }
    
    //this.updateTotals()
  }
}

