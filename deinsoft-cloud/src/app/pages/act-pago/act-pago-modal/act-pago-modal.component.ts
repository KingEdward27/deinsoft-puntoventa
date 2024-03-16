import { UpdateParam } from '@/base/components/model/UpdateParam';
import { CommonService } from '@/base/services/common.service';
import { AfterViewInit, Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
// import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbActiveModal, NgbCalendar, NgbDateAdapter, NgbDateParserFormatter, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'environments/environment';
import { CnfImpuestoCondicionService } from '../../../business/service/cnf-impuesto-condicion.service';
import { CnfImpuestoCondicion } from '../../../business/model/cnf-impuesto-condicion.model';
import { ActComprobante } from '../../act-comprobante/act-comprobante.model';
import { CnfTipoComprobante } from '../../../business/model/cnf-tipo-comprobante.model';
import { CnfTipoComprobanteService } from '../../../business/service/cnf-tipo-comprobante.service';
import { CnfNumComprobanteService } from '../../../business/service/cnf-num-comprobante.service';
import { CnfLocal } from '../../../business/model/cnf-local.model';
import { AppService } from '../../../services/app.service';
import { CnfLocalService } from '../../../business/service/cnf-local.service';
import { ActPago } from '../../../business/model/act-pago.model';
import { ActPagoService } from '../../../business/service/act-pago.service';
import { UtilService } from '../../../services/util.service';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import * as dayjs from 'dayjs';
import { MessageModalComponent } from '@pages/act-comprobante/modal/message-modal.component';




@Component({
  selector: 'act-pago-modal',
  templateUrl: './act-pago-modal.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class ActPagoModalComponent implements OnInit {

  //generic variables
  error: any;
  headers :any;
  listDetail: any;
  load:boolean = false;
  listDetail2: any[][] = [];
  message:any;
  public model: ActPago = new ActPago();
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
  option: string = "1";
  @Output() result: EventEmitter<any> = new EventEmitter();
  public id = 0;
  public modalRef!: NgbModalRef;

  constructor(public activeModal: NgbActiveModal,
    private cnfImpuestoCondicionService: CnfImpuestoCondicionService,
    private commonService: CommonService,
    private cnfTipoComprobanteService: CnfTipoComprobanteService,
    private cnfNumComprobanteService: CnfNumComprobanteService,
    private cnfLocalService: CnfLocalService,
    private appService:AppService, 
    private dateAdapter: NgbDateAdapter<dayjs.Dayjs>,
    private ngbCalendar: NgbCalendar,
    private actPagoService: ActPagoService,
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
    this.model.fecha = this.dateAdapter.toModel(this.ngbCalendar.getToday())!;
    // this.listDetail.forEach((element:any) => {
    //   let arr:any = [];  
    //   Object.keys(element).map(function(key){  
    //       arr.push({[key]:element[key]});
    //       console.log(element);
    //   });
    //   this.listDetail2.push(arr);
    // });
    // for( var i=0; i<this.listDetail.length; i++ ) {
    //   this.listDetail2.push( [] );
    // }
    
    // for (let index = 0; index < this.listDetail.length; index++) {
    //   const element = this.listDetail[index];
    //   let arr:any = [];  
    //   Object.keys(element).map(function(key){  
    //       arr.push(element[key]);
    //       console.log(element);
    //   });
    //   this.listDetail2[index].push(arr);
      
    // }
    this.load = true;
    console.log(this.listDetail2);
  }
  save(){
    // this.result.emit(this.listDetail);
    // this.activeModal.close();
    this.model.numero = "1"
    this.actPagoService.save(this.model).subscribe(m => {
        this.modalRef = this.modalService.open(MessageModalComponent);
        this.modalRef.componentInstance.message = "Documento " + m.serie + " - " + m.numero + " generado correctamente";
        this.modalRef.componentInstance.id = m.id;
        this.modalRef.componentInstance.business='act-pago'
        this.modalRef.closed.subscribe(result => {
          this.activeModal.close();
          this.router.navigate(["/cuentas-cobrar"]);
          // this.model.cnfBpartner = 
        })
        // this.utilService.msgOkSave();
        // window.location.reload();
      }, err => {
        console.log(err);
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
  getListImpuestoCondicion() {
    this.loadingCnfImpuestoCondicion = true;
    return this.cnfImpuestoCondicionService.getAllDataCombo().subscribe(data => {
      this.listImpuestoCondicion = data;
      this.loadingCnfImpuestoCondicion = false;
    })

  }
  compareCnfImpuestoCondicion(a1: CnfImpuestoCondicion, a2: CnfImpuestoCondicion): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
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
}

