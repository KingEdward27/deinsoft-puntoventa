import { Component, Directive, HostListener, Injectable, OnInit } from '@angular/core';
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
import { ParamBean } from './ParamReports';
import { InvAlmacenProductoService } from '@/business/service/inv-almacen-producto.service';
import { InvMovimientoProductoService } from '@/business/service/inv-movimiento-producto.service';
import { ActPagoProgramacionService } from '@/business/service/act-pago-programacion.service';
import { ActPagoService } from '@/business/service/act-pago.service';
import { CnfCategoria } from '@/business/model/cnf-categoria.model';
import { CnfCategoriaService } from '@/business/service/cnf-categoria.service';
import { InvMovAlmacenService } from '@/business/service/inv-mov-almacen.service';

@Injectable()
export class MyBaseComponentDependences {
  constructor(public actComprobanteService: ActComprobanteService,
    public router: Router,
    public utilService: UtilService,
    public cnfMaestroService: CnfMaestroService,
    public cnfFormaPagoService: CnfFormaPagoService,
    public cnfMonedaService: CnfMonedaService,
    public cnfLocalService: CnfLocalService,
    public cnfTipoComprobanteService: CnfTipoComprobanteService,
    public invAlmacenService: InvAlmacenService,
    public actComprobanteDetalleService: ActComprobanteDetalleService,
    public cnfProductoService: CnfProductoService,
    public cnfImpuestoCondicionService: CnfImpuestoCondicionService,
    public cnfNumComprobanteService: CnfNumComprobanteService,
    public commonService: CommonService,
    public dateAdapter: NgbDateAdapter<dayjs.Dayjs>,
    public ngbCalendar: NgbCalendar,
    public modalService: NgbModal,
    public route: ActivatedRoute, config: NgbModalConfig,
    public appService:AppService,
    public invAlmacenProductoService: InvAlmacenProductoService,
    public invMovimientoProductoService: InvMovimientoProductoService,
    public actPagoProgramacionService:ActPagoProgramacionService,
    public actPagoService:ActPagoService,
    public cnfCategoriaService: CnfCategoriaService,
    public invMovAlmacenService: InvMovAlmacenService
  ) { }
}
@Directive()
export class CommonReportFormComponent implements OnInit {

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
  public model: ParamBean = new ParamBean();
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

  loadingCnfImpuestoCondicion: boolean = false;
  selectDefaultImpuestoCondicion: any = { id: 0, nombre: "- Seleccione -" };
  listImpuestoCondicion: any;
  public modalRef!: NgbModalRef;
  listData:any;
  titleExport:any;
  datablesSettings:any
  indexInputs:any;
  datablesSettingsWithInputs:any;

  selectDefaultCnfCategoria: any = { id: 0, nombre: "- Seleccione -" };
  listCnfCategoria: any;
  cnfCategoria: CnfCategoria = new CnfCategoria();
  loadingCnfCategoria : boolean = false;

  constructor(public deps: MyBaseComponentDependences) {
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.loadData();
    this.model.fechaDesde = this.deps.dateAdapter.toModel(this.deps.ngbCalendar.getToday())!;
    this.model.fechaHasta = this.deps.dateAdapter.toModel(this.deps.ngbCalendar.getToday())!;
    let lang = localStorage.getItem('lang');
    console.log(lang);
    this.datablesSettings = {
      deferRender: true,
      deferLoading: 7,
      pagingType: 'full_numbers',
      searching: false,
      processing: true,
      lengthMenu: [25, 50, 100],
      order: [[0, "asc"]],
      dom: 'lBftip',
      buttons: [{
        extend: 'excel',
        title : this.titleExport,
        text: '<i class="fa fa-file-excel"></i>&nbsp; XLS',
        exportOptions: {
          columns: ':visible'
        }
      },
      {
        extend: 'pdf',
        title : this.titleExport,
        text: ' <i class="fa fa-file-pdf"></i>&nbsp; PDF',
        orientation: 'landscape'
      },
      {
        extend: 'csv',
        title : this.titleExport,
        text: '<i class="fa fa-file-excel"></i>&nbsp; CSV',
        exportOptions: {
          columns: ':visible'
        }
      },

      ],
      language: {
        url: 'assets/i18n/datatables/lang' + lang?.toUpperCase() + '.json'
        // emptyTable: '',
        // zeroRecords: 'No hay coincidencias',
        // lengthMenu: 'Mostrar _MENU_ elementos',
        // search: 'Buscar:',
        // info: 'De _START_ a _END_ de _TOTAL_ elementos',
        // infoEmpty: 'De 0 a 0 de 0 elementos',
        // infoFiltered: '(filtrados de _MAX_ elementos totales)',
        // paginate: {
        //   first: 'Prim.',
        //   last: 'Últ.',
        //   next: 'Sig.',
        //   previous: 'Ant.'
        // }
      }
    }
    this.datablesSettingsWithInputs = {
      deferRender: true,
      deferLoading: 7,
      pagingType: 'full_numbers',
      searching: false,
      processing: true,
      lengthMenu: [25, 50, 100],
      order: [[0, "asc"]],
      dom: 'lBftip',
      buttons: [{
        extend: 'excel',
        title : this.titleExport,
        text: '<i class="fa fa-file-excel"></i>&nbsp; XLS',
        exportOptions: {
          columns: ':visible'
        }
      },
      {
        extend: 'pdf',
        title : this.titleExport,
        text: ' <i class="fa fa-file-pdf"></i>&nbsp; PDF',
        orientation: 'landscape'
      },
      {
        extend: 'csv',
        title : this.titleExport,
        text: '<i class="fa fa-file-excel"></i>&nbsp; CSV',
        exportOptions: {
          columns: ':visible'
        }
      },
      
      ],
      columnDefs: [
        {
            orderable: false,
            targets: this.indexInputs,
        },
      ],
      language: {
        url: 'assets/i18n/datatables/lang' + lang?.toUpperCase() + '.json'
        // emptyTable: '',
        // zeroRecords: 'No hay coincidencias',
        // lengthMenu: 'Mostrar _MENU_ elementos',
        // search: 'Buscar:',
        // info: 'De _START_ a _END_ de _TOTAL_ elementos',
        // infoEmpty: 'De 0 a 0 de 0 elementos',
        // infoFiltered: '(filtrados de _MAX_ elementos totales)',
        // paginate: {
        //   first: 'Prim.',
        //   last: 'Últ.',
        //   next: 'Sig.',
        //   previous: 'Ant.'
        // }
      }
    }
  }
  // getBack() {
  //   this.router.navigate([this.redirect]);
  // }
  loadData() {
    this.getListCnfMaestro();
    this.getListCnfFormaPago();
    this.getListCnfMoneda();
    this.getListCnfLocal();
    this.getListCnfTipoComprobante();
    this.getListCnfCategoria();
    console.log(this.listCnfLocal);
    
    
  }
  getListCnfProductAsObservable(term: any): Observable<any> {

    if (term.length >= 2) {
      return this.deps.cnfProductoService.getAllDataComboTypeHead(term, this.model.invAlmacen.id)
        .pipe(
          tap(() => this.searchFailed = false),
          catchError((err: any) => {
            console.log(err);
            this.searchFailed = true;
            return of([]);
          })
        );
    } else {
      return <any>[];
    }

  }
  searchProduct = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => {
        console.log("esg");
        console.log(term);

        return this.getListCnfProductAsObservable(term);
      })
    )
  
  agregarActComprobanteDetalle(): void {
    this.deps.router.navigate(["/add-new-act-comprobante-detalle", { idParent: this.model.id }]);
  }
  editarActComprobanteDetalle(actComprobanteDetalle: any): void {
    this.deps.router.navigate(["/add-new-act-comprobante-detalle", { idParent: this.model.id, id: actComprobanteDetalle.id }]);
  }
  
  compareActComprobante(a1: ActComprobante, a2: ActComprobante): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfMaestro() {
    this.loadingCnfMaestro = true;
    let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.deps.cnfMaestroService.getAllDataCombo().subscribe(data => {
        this.listCnfMaestro = data;
        this.loadingCnfMaestro = false;
      })
    }else{
      return this.deps.cnfMaestroService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfMaestro = data;
        this.loadingCnfMaestro = false;
      })
    }
    

  }
  getListImpuestoCondicion() {
    console.log(this.chargingsb);
    this.loadingCnfImpuestoCondicion = true;
    return this.deps.cnfImpuestoCondicionService.getAllDataCombo().subscribe(data => {
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
  compareCnfMaestro(a1: CnfMaestro, a2: CnfMaestro): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfFormaPago() {
    this.loadingCnfFormaPago = true;
    console.log(this.chargingsb);
    let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.deps.cnfFormaPagoService.getAllDataCombo().subscribe(data => {
        this.listCnfFormaPago = data;
        this.loadingCnfFormaPago = false;
      })
    }else{
      return this.deps.cnfFormaPagoService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfFormaPago = data;
        this.loadingCnfFormaPago = false;
      })
    }
    

  }
  compareCnfFormaPago(a1: CnfFormaPago, a2: CnfFormaPago): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfMoneda() {
    this.loadingCnfMoneda = true;
    console.log(this.chargingsb);
    return this.deps.cnfMonedaService.getAllDataCombo().subscribe(data => {
      console.log(data);

      this.listCnfMoneda = data;
      this.loadingCnfMoneda = false;
    })

  }
  compareCnfMoneda(a1: CnfMoneda, a2: CnfMoneda): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfLocal() {
    this.loadingCnfLocal = true;
    console.log(this.chargingsb);
    let user = this.deps.appService.getProfile();
    console.log(user);
    let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.deps.cnfLocalService.getAllDataCombo().subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;
      })
    }else{
      return this.deps.cnfLocalService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;

        
        if(this.listCnfLocal.length == 1) {
          this.deps.cnfLocalService.getData(this.listCnfLocal[0].id).subscribe(data => {
            this.model.cnfLocal = data
            this.getListInvAlmacen()
            
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
  getListCnfTipoComprobante() {
    this.loadingCnfTipoComprobante = true;
    console.log(this.chargingsb);
    return this.deps.cnfTipoComprobanteService.getAllDataCombo().subscribe(data => {
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
    console.log(this.chargingsb);
    return this.deps.invAlmacenService.getAllByCnfLocalId(this.model.cnfLocal.id).subscribe(data => {
      this.listInvAlmacen = data;
      this.loadingInvAlmacen = false;
      if(this.listInvAlmacen.length == 1){
        this.deps.invAlmacenService.getData(this.listInvAlmacen[0].id).subscribe(data => {
          this.model.invAlmacen = data;
        })
      }
      
    })

  }
  compareInvAlmacen(a1: InvAlmacen, a2: InvAlmacen): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  
  ticketChild() {
    let myMap = new Map();
    myMap.set("id", this.model.id);
    myMap.set("tipo", 2);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);
    mp.map = convMapDetail;
    this.deps.commonService.updateParam = mp;
    this.deps.commonService.genericPostRequest("/api/business/getpdflocal", mp, 'blob').subscribe(data => {
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
    myMap.set("id", this.model.id);
    myMap.set("tipo", 1);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);

    mp.map = convMapDetail;
    this.deps.commonService.genericPostRequest("/api/business/getpdflocal", mp, 'blob').subscribe(data => {
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
  msgConfirmSaveWithButtons(title: string, icon: any, lines: any) {
    let html: string = "";
    console.log(lines);
    if (lines) {
      for (let items of lines) {
        html = html + '<div class="form-group row">' +

          '<div class="col-sm-12" style="padding-top: 20px;">' +
          '    <div class="row justify-content-center">';
        '<ng-container>'
        for (let index = 0; index < items.columns.length; index++) {
          const element = items.columns[index];
          console.log(element);

          html = html + '<div class="col-lg-6  col-sm-6 col-xs-5" style="margin-left: 50x;">' +
            '            <button  onclick="onBtnClicked()" id="' + element.id + '" class="btn btn-default col-12">' +
            '                <i class="' + element.icon + '"></i> ' + element.value +
            '            </button>' +
            '        </div>';
        }

        // '        <div class="col-lg-6  col-sm-6 col-xs-5" style="margin-left: 50x;">'+
        // '            <a id="ticket" onclick = '+items.action.name()+' class="btn btn-default col-12">'+
        // '                <i class="fa fa-arrow-circle-left"></i> Ticket'+
        // '            </a>'+
        // '        </div>'+
        // '        <div class="col-lg-6 col-sm-6 col-xs-5" style="margin-left: 20x;">'+
        // '            <a id="a4" class="btn btn-primary col-12">'+
        // '                <i class="fa fa-arrow-circle-left"></i> A4'+
        // '            </a>'+
        // '        </div>'+
        html = html + '    </div>' +
          '</div>' +
          '</div>';
      }
    }

    var onBtnClicked = () => {
      this.ticketChild();
    };
    return Swal.fire({
      title: title,
      icon: icon,
      html: '<div class="table-responsive">' +
        '<div class="col-sm-12" style="padding-top: 6px;">' +
        html +
        '</div>' +
        '</div>',
      showCloseButton: true,
      allowOutsideClick: false,
      showConfirmButton: true,

      confirmButtonText:
        '<div ><i class="fa fa-check" ></i> Aceptar</div>',


    })

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

  getListCnfCategoria() {
    this.loadingCnfCategoria = true;
    let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.deps.cnfCategoriaService.getAllDataCombo().subscribe(data => {
        this.listCnfCategoria = data;
        this.loadingCnfCategoria = false;
      })
    }else{
      return this.deps.cnfCategoriaService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfCategoria = data;
        this.loadingCnfCategoria = false;
      })
    }
  }
  compareCnfCategoria(a1: CnfCategoria, a2: CnfCategoria): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
}
