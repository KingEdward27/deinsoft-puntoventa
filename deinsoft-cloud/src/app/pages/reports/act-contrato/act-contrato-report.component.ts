
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CommonReportFormComponent, MyBaseComponentDependences } from '../base/common-report.component';
import { ActContratoService } from '../../act-contrato/act-contrato.service';
import { ActContrato } from '../../act-contrato/act-contrato.model';
import { NgbDateParserFormatter, NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '../../../base/util/CustomDate';
@Component({
  selector: 'app-act-contrato-report',
  templateUrl: './act-contrato-report.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class ActContratoReportComponent extends CommonReportFormComponent implements OnInit {
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch: string = "";
  modelSearch: ActContrato = new ActContrato();
  dataTable!: DataTables.Api;
  constructor(private actContratoService: ActContratoService,public deps: MyBaseComponentDependences) {
    super(deps);
  }
  ngOnInit(): void {
    super.ngOnInit();
    
    this.selectThisMonth();
  }
  // public getAllDataByFilters() {
  //   let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
  //   this.modelSearch.cnfEmpresaId = cnfEmpresa
  //   this.actContratoService.getAllData(this.modelSearch).subscribe(data => {
  //     this.lista = data;
  //   });
  // }
  // public getAllData() {
  //   let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
  //   this.modelSearch.cnfEmpresaId = cnfEmpresa
  //   this.actContratoService.getAllData(this.modelSearch).subscribe(data => {
  //     this.lista = data;
  //     setTimeout(() => {
  //       this.dataTable = $('#dtDataActContrato').DataTable(this.deps.utilService.datablesSettings);
  //     }, 0);
  //     console.log(data);
  //     this.dataTable?.destroy();
  //   });
  // }
  selectThisMonth() {
    let year = new Date().getFullYear();
    let month = new Date().getMonth()+1;
    let day = new Date(year, month, 0).getDate();
    
    this.model.fechaVencimiento  = this.deps.dateAdapter
    .toModel({year: year, month: month, day: day}).toString();
}
  getListData() {
    this.model.flagIsventa = '1';
    this.indexInputs = [8]
    this.model.fechaVencimiento = this.model.fechaVencimiento?this.model.fechaVencimiento:''
    return this.actContratoService.getReport(this.model).subscribe(data => {
      console.log(data);
      this.listData = data;
      this.loadingCnfMaestro = false;
      this.dataTable?.destroy()
      setTimeout(() => {
        this.dataTable = $('#dtDataActContratoReport').DataTable(this.datablesSettings);
      }, 1);
      console.log(data);
    })
  }
  editar(e: ActContrato) {
    if (this.deps.utilService.validateDeactivate(e)) {
      this.deps.router.navigate(["/new-contrato", { id: e.id }]);
    }

  } 
  eliminar(e: ActContrato) {
    this.deps.utilService.confirmDelete(e).then((result) => {
      if (result) {
        this.actContratoService.delete(e.id.toString()).subscribe(() => {
          this.deps.utilService.msgOkDelete();
          this.getListData();
        }, err => {
          if (err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")) {
            this.deps.utilService.msgProblemDelete();
          }
        });
      }

    });
  }
  
}
