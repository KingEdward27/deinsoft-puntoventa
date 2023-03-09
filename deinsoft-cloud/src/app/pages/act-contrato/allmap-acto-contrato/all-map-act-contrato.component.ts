import { Component, HostListener, OnInit, ElementRef, ViewChild, NgZone } from '@angular/core';

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
import { UtilService } from '@services/util.service';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { catchError, debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';
import { CnfProductoService } from '@/business/service/cnf-producto.service';
import { CnfImpuestoCondicion } from '@/business/model/cnf-impuesto-condicion.model';
import { CnfImpuestoCondicionService } from '@/business/service/cnf-impuesto-condicion.service';
import { CnfNumComprobanteService } from '@/business/service/cnf-num-comprobante.service';
import { CommonService } from '@/base/services/common.service';
import { UpdateParam } from '@/base/components/model/UpdateParam';
import dayjs from 'dayjs';
import { AppService } from '@services/app.service';
import { CnfMaestroFormModalComponent } from '@pages/cnf-maestro/cnf-maestro-modal/cnf-maestro-form-modal.component';
import { ActContrato } from '../act-contrato.model';
import { ActContratoService } from '../act-contrato.service';
import { CnfPlanContrato } from '../../cnf-plan-contrato/cnf-plan-contrato.model';
import { CnfPlanContratoService } from '../../cnf-plan-contrato/cnf-plan-contrato.service';
import { GoogleMapsService, Maps } from '../../../business/service/googlemaps.service';
import * as turf from '@turf/turf'
import * as geolib from 'geolib'
import { ApiService } from '../../../services/api.service';
const place = null as google.maps.places.PlaceResult;
type Components = typeof place.address_components;

@Component({
  selector: 'app-all-map-act-contrato',
  templateUrl: './all-map-act-contrato.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class AllMapActContratoComponent implements OnInit {

  //generic variables
  error: any;
  selectedItemsList = [];
  checkedIDs = [];
  isDataLoaded: Boolean = false;
  isOk: boolean = false;
  isWarning: boolean = false;
  isDanger: boolean = false;
  message: any = "";
  id: string = "";
  searchFailed = false;
  //variables propias
  public model: ActContrato = new ActContrato();
  selectDefaultActContrato: any = { id: 0, nombre: "- Seleccione -" }; listActContrato: any;
  actContrato: ActContrato = new ActContrato();
  loadingActContrato: boolean = false;
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

  selectDefaultCnfPlanContrato: any = { id: 0, nombre: "- Seleccione -" };
  listCnfPlanContrato: any;
  loadingCnfPlanContrato: boolean = false;

  listActContratoDetalle: any;
  selectedOptionActContratoDetalle: any;
  protected redirect: string = "/contrato";
  selectedOption: any;
  passwordRepeat: any;
  isAdding: boolean = false;
  isSave: boolean = false;
  public modelOrig: ActContrato = new ActContrato();
  cnfProducto: any;
  formatter = (x: { nombre: string }) => x.nombre;

  formatterDireccion = (x: { formatted_address: string }) => x.formatted_address;
  formatterCnfMaestro = (x: CnfMaestro) => (x.apellidoPaterno + ' ' + x.apellidoMaterno + ' ' + x.nombres).trim();

  loadingCnfImpuestoCondicion: boolean = false;
  selectDefaultImpuestoCondicion: any = { id: 0, nombre: "- Seleccione -" };
  listImpuestoCondicion: any;
  selectedCnfMaestro: any;
  public modalRef!: NgbModalRef;

  @ViewChild('search')
  searchElementRef: ElementRef;

  @ViewChild('map')
  mapElementRef: ElementRef;

  public entries = [];

  public place: google.maps.places.PlaceResult;

  public locationFields = [
    'name',
    'cityName',
    'stateCode',
    'countryName',
    'countryCode',
  ];

  private map: google.maps.Map;

  constructor(private actContratoService: ActContratoService,
    private router: Router,
    private utilService: UtilService,
    private cnfMaestroService: CnfMaestroService,
    private cnfFormaPagoService: CnfFormaPagoService,
    private cnfMonedaService: CnfMonedaService,
    private cnfLocalService: CnfLocalService,
    private cnfTipoComprobanteService: CnfTipoComprobanteService,
    private cnfPlanContratoService: CnfPlanContratoService,
    private cnfProductoService: CnfProductoService,
    private cnfImpuestoCondicionService: CnfImpuestoCondicionService,
    private cnfNumComprobanteService: CnfNumComprobanteService,
    private commonService: CommonService,
    private dateAdapter: NgbDateAdapter<dayjs.Dayjs>,
    private ngbCalendar: NgbCalendar,
    private modalService: NgbModal,
    private route: ActivatedRoute,
    private googleMapsService: GoogleMapsService,
    config: NgbModalConfig, private appService: AppService,
    private apiService: GoogleMapsService, private ngZone: NgZone) {
    config.backdrop = 'static';
    config.keyboard = false;

  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    // $(document).on('click', '#ticket', function () {
    //   this.ticketChild();
    // });
    // $(document).on('click', '#a4', function () {
    //   this.a4();
    // });
    this.loadData();
    //location.reload();
  }
  getBack() {
    this.router.navigate([this.redirect]);
  }
  loadData() {
    this.getListCnfMaestro();
    this.getListCnfMoneda();
    this.getListCnfLocal();
    this.getListCnfTipoComprobante();
    this.getListCnfPlanContrato();
    // this.getListInvAlmacen();
    this.getListImpuestoCondicion();





    this.model.total = 0;
    this.model.subtotal = 0;
    this.model.igv = 0;
    this.model.descuento = 0;
    this.model.fecha = this.dateAdapter.toModel(this.ngbCalendar.getToday())!;
    this.model.flagEstado = "1";
    this.model.flagIsventa = '1';
    this.apiService.api.then((maps) => {
      this.initAutocomplete(maps);
      this.initMap(maps);

      return this.route.paramMap.subscribe(params => {
        this.id = params.get('id')!;
        if (!this.id) {
          this.isDataLoaded = true;
        }
        if (this.id) {
          this.actContratoService.getData(this.id).subscribe(data => {
            this.model = data;
            this.map.setCenter(new google.maps.LatLng(this.model.latitude, this.model.longitude));
  
            const color = 'red';
            const pin = this.pin(color);
  
            const marker = new google.maps.Marker({
              position: new google.maps.LatLng(this.model.latitude, this.model.longitude),
              animation: google.maps.Animation.DROP,
              map: this.map,
              icon: this.pin(color),
            });
  
            this.isDataLoaded = true;
            //this.titulo = 'Editar ' + this.nombreModel;
          });
        }
  
      })
    });
    

  }
  save() {
    if (!this.model.id) {
      this.model.numero = "1"
    }
    this.actContratoService.save(this.model).subscribe(m => {
      this.utilService.msgOkSave()
      this.router.navigate(["/contrato"]);

    }, err => {
      if (err.status === 422) {
        this.error = []

        for (var prop in err.error) {
          this.error.push(err.error[prop])
        }
      }
    });
  }
  compareActContrato(a1: ActContrato, a2: ActContrato): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfMaestro() {
    this.loadingCnfMaestro = true;
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    if (cnfEmpresa == '*') {
      return this.cnfMaestroService.getAllDataCombo().subscribe(data => {
        this.listCnfMaestro = data;
        this.loadingCnfMaestro = false;
      })
    } else {
      return this.cnfMaestroService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfMaestro = data;
        this.loadingCnfMaestro = false;
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
  getListCnfMoneda() {
    this.loadingCnfMoneda = true;
    return this.cnfMonedaService.getAllDataCombo().subscribe(data => {

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
    let user = this.appService.getProfile();
    console.log(user);
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    if (cnfEmpresa == '*') {
      return this.cnfLocalService.getAllDataCombo().subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;
      })
    } else {
      return this.cnfLocalService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;


        if (this.listCnfLocal.length == 1) {
          this.cnfLocalService.getData(this.listCnfLocal[0].id).subscribe(data => {
            this.model.cnfLocal = data
            // this.getListInvAlmacen()

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
    return this.cnfTipoComprobanteService.getAllDataCombo().subscribe(data => {
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
  getListCnfPlanContrato() {
    this.loadingCnfPlanContrato = true;
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    return this.cnfPlanContratoService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
      this.listCnfPlanContrato = data;
      this.loadingCnfPlanContrato = false;
      // if(this.listCnfPlanContrato.length == 1){
      //   this.invAlmacenService.getData(this.listInvAlmacen[0].id).subscribe(data => {
      //     this.model.invAlmacen = data;
      //   })
      // }

    })

  }
  compareCnfPlanContrato(a1: CnfPlanContrato, a2: CnfPlanContrato): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  // onChangeCantidad(item: any, value: any) {
  //   item.importe = value * item.precio;
  //   item.afectacionIgv
  //     = item.importe - item.importe / 1.18
  //   this.updateTotals()
  // }
  // onChangePrecio(item: any, value: any) {
  //   item.importe = item.cantidad * value;
  //   item.afectacionIgv
  //     = item.importe - item.importe / 1.18
  //   this.updateTotals()
  // }
  // updateTotals() {
  //   this.model.total = 0
  //   this.model.subtotal = 0
  //   this.model.igv = 0
  //   this.model.listActContratoDetalle.forEach(element => {
  //     this.model.total = Math.round((this.model.total + element.importe + Number.EPSILON) * 100) / 100;
  //     this.model.subtotal = Math.round((this.model.total / 1.18 + Number.EPSILON) * 100) / 100;
  //     this.model.descuento = 0
  //     this.model.igv = Math.round((this.model.total - this.model.subtotal + Number.EPSILON) * 100) / 100
  //   });
  // }
  loadSerie() {
    this.model.serie = "";
    return this.cnfNumComprobanteService
      .getDataByCnfTipoComprobanteIdAndCnfLocalId(this.model.cnfTipoComprobante.id.toString()
        , this.model.cnfLocal.id.toString()).subscribe(data => {
          this.model.serie = data[0].serie
        })
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
    myMap.set("id", this.model.id);
    myMap.set("tipo", 1);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);

    mp.map = convMapDetail;
    this.commonService.genericPostRequest("/api/business/getpdflocal", mp, 'blob')
      .subscribe(data => {
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
  // onChangeinDetail(item:any,event: any){
  //   console.log(event);
  //   item.importe = item.cantidad * item.precio;
  //   this.model.total = Math.round((this.model.total + item.importe + Number.EPSILON) * 100) / 100 ;
  //   this.model.subtotal = Math.round((this.model.total / 1.18 + Number.EPSILON) * 100) / 100 ;
  //   this.model.descuento = 0
  //   this.model.igv = Math.round((this.model.total - this.model.subtotal  + Number.EPSILON) * 100) / 100
  // }
  // removeItem(index:any){
  //   this.model.listActContratoDetalle.splice(index, 1)
  //   this.updateTotals()
  // }
  addNewCnfMestro() {
    this.modalRef = this.modalService.open(CnfMaestroFormModalComponent);
    this.modalRef.closed.subscribe(result => {
      this.getListCnfMaestro();
      // this.model.cnfBpartner = 
    })
    this.modalRef.componentInstance.cnfMaestro.subscribe((receivedEntry: CnfMaestro) => {
      console.log(receivedEntry);
      this.getListCnfMaestro()
      this.model.cnfMaestro = receivedEntry
    })
  }
  getListCnfMestroAsObservable(term: any): Observable<any> {

    if (term.length >= 2) {
      let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
      return this.cnfMaestroService.getAllDataComboTypeHead(term, cnfEmpresa)
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
  searchCnfMaestro = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => {
        return this.getListCnfMestroAsObservable(term);
      })
    )
  initAutocomplete(maps: Maps) {
    let autocomplete = new maps.places.Autocomplete(
      this.searchElementRef.nativeElement
    );
    autocomplete.addListener('place_changed', () => {
      this.ngZone.run(() => {
        this.onPlaceChange(autocomplete.getPlace());
      });
    });
  }

  initMap(maps: Maps) {
    this.map = new maps.Map(this.mapElementRef.nativeElement, {
      zoom: 13,
    });
    this.map.addListener('click', (event) => {
      console.log(event);

      const ellipsePoints = toEllipse(this.entries[0].location.bounds);
      var line = turf.helpers.lineString(
        ellipsePoints.map((p) => [p.longitude, p.latitude])
      );

      const pointLatLng = event.latLng as google.maps.LatLng;
      var point = turf.helpers.point([pointLatLng.lng(), pointLatLng.lat()]);
      //point = turf.helpers.point([this.entries[0].location.coordinates.longitude, this.entries[0].location.coordinates.latitude]);
      const isInside = geolib.isPointInPolygon(
        { latitude: pointLatLng.lat(), longitude: pointLatLng.lng() },
        ellipsePoints
      );
      const distance = isInside ? 0 : turf.pointToLineDistance(point, line);
      console.log('distance', distance * 1000);
    });
  }

  onPlaceChange(place: google.maps.places.PlaceResult) {
    this.map.setCenter(place.geometry.location);

    const color = 'red';
    const pin = this.pin(color);

    const marker = new google.maps.Marker({
      position: place.geometry.location,
      animation: google.maps.Animation.DROP,
      map: this.map,
      icon: this.pin(color),
    });

    // const rectangle = new google.maps.Rectangle({
    //   strokeColor: color,
    //   strokeOpacity: 0.8,
    //   strokeWeight: 2,
    //   fillColor: color,
    //   fillOpacity: 0.35,
    //   map: this.map,
    //   bounds: place.geometry.viewport,
    // });

    // const expandedRectangle = new google.maps.Rectangle({
    //   strokeColor: color,
    //   strokeOpacity: 0.8,
    //   strokeWeight: 0.5,
    //   fillOpacity: 0.2,
    //   map: this.map,
    //   bounds: expandBounds(place.geometry.viewport.toJSON(), 5000),
    // });

    const location = this.locationFromPlace(place);

    // const ellipse = new google.maps.Polygon({
    //   paths: toEllipse(location.bounds).map(
    //     ({ latitude, longitude }) => new google.maps.LatLng(latitude, longitude)
    //   ),
    //   strokeColor: color,
    //   strokeOpacity: 1,
    //   strokeWeight: 1,
    //   fillColor: color,
    //   fillOpacity: 0.3,
    // });
    // ellipse.setMap(this.map);
    this.entries = [];
    // console.log(place);
    // console.log(location);
    this.model.direccion = place.formatted_address;
    this.model.urlMap = place.url;
    this.model.latitude = location.coordinates.latitude;
    this.model.longitude = location.coordinates.longitude;
    this.model.vicinity = place.vicinity + ", "+ location.cityName+ ", "+ location.stateCode;
    this.entries.unshift({
      place,
      marker,
      color,
      location,
    });
  }


  pin(color) {
    return {
      path: 'M 0,0 C -2,-20 -10,-22 -10,-30 A 10,10 0 1,1 10,-30 C 10,-22 2,-20 0,0 z M -2,-30 a 2,2 0 1,1 4,0 2,2 0 1,1 -4,0',
      fillColor: color,
      fillOpacity: 1,
      strokeColor: '#000',
      strokeWeight: 2,
      scale: 1,
    };
  }

  public locationFromPlace(place: google.maps.places.PlaceResult) {
    const components = place.address_components;
    if (components === undefined) {
      return null;
    }

    const areaLevel3 = getShort(components, 'administrative_area_level_3');
    const locality = getLong(components, 'locality');

    const cityName = locality || areaLevel3;
    const countryName = getLong(components, 'country');
    const countryCode = getShort(components, 'country');
    const stateCode = getShort(components, 'administrative_area_level_1');
    const name = place.name !== cityName ? place.name : null;

    const coordinates = {
      latitude: place.geometry.location.lat(),
      longitude: place.geometry.location.lng(),
    };

    const bounds = place.geometry.viewport.toJSON();

    // placeId is in place.place_id, if needed
    return {
      name,
      cityName,
      countryName,
      countryCode,
      stateCode,
      bounds,
      coordinates,
    };
  }
}

function getComponent(components: Components, name: string) {
  return components.filter((component) => component.types[0] === name)[0];
}

function getLong(components: Components, name: string) {
  const component = getComponent(components, name);
  return component && component.long_name;
}

function getShort(components: Components, name: string) {
  const component = getComponent(components, name);
  return component && component.short_name;
}

function toEllipse({ north, south, east, west }: cosmos.LatLngBoundsLiteral) {
  const latitude = (north + south) / 2;
  const longitude = (east + west) / 2;
  const r1 =
    geolib.getDistance(
      { latitude: north, longitude },
      { latitude: south, longitude }
    ) / 2;
  const r2 =
    geolib.getDistance(
      { latitude, longitude: west },
      { latitude, longitude: east }
    ) / 2;

  const center = { latitude, longitude };
  const latitudeConv =
    geolib.getDistance(center, { latitude: latitude + 0.1, longitude }) * 10;
  const longitudeCong =
    geolib.getDistance(center, { latitude, longitude: longitude + 0.1 }) * 10;

  const points: cosmos.Coordinates[] = [];
  const FULL = Math.PI * 2;
  for (let i = 0; i <= FULL + 0.0001; i += FULL / 8) {
    points.push({
      latitude: latitude + (r1 * Math.cos(i)) / latitudeConv,
      longitude: longitude + (r2 * Math.sin(i)) / longitudeCong,
    });
  }
  return points;
}

function expandBounds(bounds: cosmos.LatLngBoundsLiteral, meters: number) {
  const SQRT_2 = 1.4142135623730951;
  // console.log(geolib);

  const { longitude: west, latitude: north } = geolib.computeDestinationPoint(
    {
      latitude: bounds.north,
      longitude: bounds.west,
    },
    SQRT_2 * meters,
    315
  );
  const { longitude: east, latitude: south } = geolib.computeDestinationPoint(
    {
      latitude: bounds.south,
      longitude: bounds.east,
    },
    SQRT_2 * meters,
    135
  );
  return { west, north, east, south };
}


namespace cosmos {
  export interface Coordinates {
    /**
     * Coordinates latitude.
     * @type {number}
     */
    latitude: number;
    /**
     * Coordinates longitude.
     * @type {number}
     */
    longitude: number;
  }
  export interface LatLngBoundsLiteral {
    /**
     * LatLngBoundsLiteral east.
     * @type {number}
     */
    east: number;
    /**
     * LatLngBoundsLiteral north.
     * @type {number}
     */
    north: number;
    /**
     * LatLngBoundsLiteral south.
     * @type {number}
     */
    south: number;
    /**
     * LatLngBoundsLiteral west.
     * @type {number}
     */
    west: number;
  }
}
