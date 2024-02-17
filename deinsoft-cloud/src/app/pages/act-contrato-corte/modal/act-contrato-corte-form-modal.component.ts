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
import { ActContratoCorteService } from '@/business/service/act-contrato-corte.service';
import { ActContratoCorte } from '@/business/model/act-contrato-corte.model';
import dayjs from 'dayjs';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: './act-contrato-corte-form-modal.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class ActContratoCorteFormModalComponent implements OnInit {

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
  public model: ActContratoCorte = new ActContratoCorte();

  protected redirect: string = "/cnf-maestro";
  selectedOption: any;
  passwordRepeat: any;
  @Output() cnfMaestro: EventEmitter<CnfMaestro> = new EventEmitter();
  constructor(
    private router: Router,
    private utilService: UtilService,
    private actContratoCorteService: ActContratoCorteService,
    private route: ActivatedRoute, public activeModal: NgbActiveModal,
    private appService: AppService, 
    private dateAdapter: NgbDateAdapter<dayjs.Dayjs>,
    private ngbCalendar: NgbCalendar) {
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    
    this.model.fecha = this.dateAdapter.toModel(this.ngbCalendar.getToday())!;
  }
  getBack() {
    this.router.navigate([this.redirect]);
  }
  public save(): void {
    this.model.flgEstado = "1";
    this.utilService.confirmProcessWithMessage("Se procederá a cambiar el estado del contrato. Desea proceder?").then((result) => {
      if (result) {
        this.actContratoCorteService.save(this.model).subscribe(m => {
          console.log(m);
          this.isOk = true;
          this.utilService.msgOkSave();
          // this.cnfMaestro.emit(m);
          this.activeModal.close();
        }, err => {
          if (err.status === 422) {
            this.error = err.error;
            console.log(this.error);
          }
        });
    
      }
    });
    
  }
}

