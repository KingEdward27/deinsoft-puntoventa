import { Component, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { Observable } from 'rxjs';
import { NgbActiveModal, NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CnfMaestro } from '@/business/model/cnf-maestro.model';
import { CnfTipoDocumento } from '@/business/model/cnf-tipo-documento.model';
import { CnfEmpresa } from '@/business/model/cnf-empresa.model';
import { CnfDistrito } from '@/business/model/cnf-distrito.model';
import { CnfMaestroService } from '@/business/service/cnf-maestro.service';
import { UtilService } from '@services/util.service';
import { CnfEmpresaService } from '@/business/service/cnf-empresa.service';
import { CnfTipoDocumentoService } from '@/business/service/cnf-tipo-documento.service';
import { CnfDistritoService } from '@/business/service/cnf-distrito.service';
import { AppService } from '@services/app.service';
import { CnfRegionService } from '@/business/service/cnf-region.service';
import { CnfProvinciaService } from '@/business/service/cnf-provincia.service';
import { CnfRegion } from '@/business/model/cnf-region.model';
import { CnfProvincia } from '@/business/model/cnf-provincia.model';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './cnf-maestro-form-modal.component.html'
})
export class CnfMaestroFormModalComponent implements OnInit {

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
  public model: CnfMaestro = new CnfMaestro();
  selectDefaultCnfTipoDocumento: any = { id: 0, name: "- Seleccione -" }; listCnfTipoDocumento: any;
  cnfTipoDocumento: CnfTipoDocumento = new CnfTipoDocumento();
  loadingCnfTipoDocumento: boolean = false;
  selectDefaultCnfEmpresa: any = { id: 0, name: "- Seleccione -" }; listCnfEmpresa: any;
  cnfEmpresa: CnfEmpresa = new CnfEmpresa();
  loadingCnfEmpresa: boolean = false;

  selectDefaultCnfRegion: any = { id: 0, name: "- Seleccione -" }; 
  listCnfRegion: any;
  cnfRegion: CnfRegion = new CnfRegion();
  loadingCnfRegion: boolean = false;

  selectDefaultCnfProvincia: any = { id: 0, name: "- Seleccione -" }; 
  listCnfProvincia: any;
  cnfProvincia: CnfProvincia = new CnfProvincia();
  loadingCnfProvincia: boolean = false;

  selectDefaultCnfDistrito: any = { id: 0, name: "- Seleccione -" }; 
  listCnfDistrito: any;
  cnfDistrito: CnfDistrito = new CnfDistrito();
  loadingCnfDistrito: boolean = false;
  selectedCnfRegion: CnfRegion = new CnfRegion();
  selectedCnfProvincia: CnfProvincia = new CnfProvincia();
  protected redirect: string = "/cnf-maestro";
  selectedOption: any;
  passwordRepeat: any;
  @Output() cnfMaestro: EventEmitter<CnfMaestro> = new EventEmitter();
  constructor(private cnfMaestroService: CnfMaestroService,
    private router: Router,
    private utilService: UtilService,
    private cnfTipoDocumentoService: CnfTipoDocumentoService,
    private cnfEmpresaService: CnfEmpresaService,
    private cnfDistritoService: CnfDistritoService,
    private route: ActivatedRoute,public activeModal: NgbActiveModal,
    private appService:AppService,private cnfRegionService:CnfRegionService,
    private cnfProvinciaService:CnfProvinciaService) {
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.loadData();
  }
  getBack() {
    this.router.navigate([this.redirect]);
  }
  loadData() {
    this.getListCnfTipoDocumento();
    this.getListCnfEmpresa();
    this.getListCnfRegion();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if (!this.id) {
        this.isDataLoaded = true;
      }
      if (this.id) {
        this.cnfMaestroService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }

    })
  }
  public save(): void {
    if (this.model.cnfTipoDocumento.codigoSunat == '6'){
      this.model.nombres = this.model.razonSocial
    }
    this.model.flagEstado = "1";
    console.log(this.model);
    this.cnfEmpresaService.getData(this.appService.getProfile().profile.split("|")[1]).subscribe(data =>{
      this.model.cnfEmpresa = data
      this.cnfMaestroService.save(this.model).subscribe(m => {
        console.log(m);
        this.isOk = true;
        this.utilService.msgOkSave();
        this.cnfMaestro.emit(m);
        this.activeModal.close();
      }, err => {
        if (err.status === 422) {
          this.error = err.error;
          console.log(this.error);
        }
      });
    })
    
  }
  getListCnfTipoDocumento() {
    this.loadingCnfTipoDocumento = true;
    console.log(this.chargingsb);
    return this.cnfTipoDocumentoService.getAllDataCombo().subscribe(data => {
      this.listCnfTipoDocumento = data;
      this.loadingCnfTipoDocumento = false;
    })

  }
  compareCnfTipoDocumento(a1: CnfTipoDocumento, a2: CnfTipoDocumento): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfEmpresa() {
    this.loadingCnfEmpresa = true;
    console.log(this.chargingsb);
    return this.cnfEmpresaService.getAllDataCombo().subscribe(data => {
      this.listCnfEmpresa = data;
      this.loadingCnfEmpresa = false;
    })

  }
  compareCnfEmpresa(a1: CnfEmpresa, a2: CnfEmpresa): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfDistrito() {
    this.loadingCnfDistrito = true;
    console.log(this.chargingsb);
    return this.cnfDistritoService.getAllDataCombo().subscribe(data => {
      this.listCnfDistrito = data;
      this.loadingCnfDistrito = false;
    })

  }
  compareCnfDistrito(a1: CnfDistrito, a2: CnfDistrito): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfRegion() {
    this.loadingCnfRegion = true;
    return this.cnfRegionService.getAllDataCombo().subscribe(data => {
      console.log(data);
      
      this.listCnfRegion = data;
      this.loadingCnfRegion = false;
    })

  }
  loadCnfProvincia(selectedCnfRegion: CnfRegion) {
    this.chargingsb = true;
    return this.cnfProvinciaService.getAllByCnfRegionId(selectedCnfRegion.id).subscribe(data => {
      this.listCnfProvincia = data;
      this.listCnfProvincia.id = 0;
    })
  }
  loadCnfDistrito(selectedCnfProvince: CnfProvincia) {
    this.chargingsb = true;
    console.log(selectedCnfProvince);
    return this.cnfDistritoService.getAllByCnfProvinciaId(selectedCnfProvince.id).subscribe(data => {
      this.listCnfDistrito = data;
      this.listCnfDistrito.id = 0;
    })
  }
  compareCnfRegion(a1: CnfRegion, a2: CnfRegion): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  compareCnfProvincia(a1: CnfProvincia, a2: CnfProvincia): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
}

