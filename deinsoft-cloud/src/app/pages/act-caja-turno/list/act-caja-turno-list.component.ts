import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
import { ActCajaTurno } from '@/business/model/act-caja-turno.model';
import { ActCajaTurnoService } from '@/business/service/act-caja-turno.service';
import { CommonReportFormComponent, MyBaseComponentDependences } from '../../reports/base/common-report.component';
import { NgbDateParserFormatter, NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '../../../base/util/CustomDate';
@Component({
  selector: 'app-act-caja-turno-list',
  templateUrl: './act-caja-turno-list.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class ActCajaTurnoListComponent extends CommonReportFormComponent implements OnInit {
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch: string = "";
  modelSearch: ActCajaTurno = new ActCajaTurno();
  dataTable!: DataTables.Api;
  constructor(private actCajaTurnoService: ActCajaTurnoService, public deps: MyBaseComponentDependences) {
    super(deps);
  }
  ngOnInit(): void {
    super.ngOnInit();
    // this.getAllData();
  }
  // public getAllDataByFilters() {
  //   this.actCajaTurnoService.getAllData(this.modelSearch)
  //     .subscribe(data => {
  //       this.lista = data;
  //     });
  // }
  // public getAllData() {
  //   this.actCajaTurnoService.getAllData(this.modelSearch).subscribe(data => {
  //     console.log(data);

  //     this.lista = data;
  //     setTimeout(() => {
  //       this.dataTable = $('#dtDataActCajaTurno').DataTable(this.utilService.datablesSettings);
  //     }, 0);
  //     console.log(data);
  //     this.dataTable?.destroy();
  //   });
  // }
  getListData() {
    console.log(this.model);
    
    return this.actCajaTurnoService.getReport(this.model).subscribe(data => {
      this.listData = data;
      setTimeout(() => {
        this.dataTable = $('#dtDataActCajaTurno').DataTable(this.datablesSettings);
      }, 1);
      this.dataTable?.destroy();
      console.log(data);
    })
  }
  editar(e: ActCajaTurno) {
    if (this.deps.utilService.validateDeactivate(e)) {
      if (e.estado == '0') {
        this.deps.utilService.msgWarning("No puede continuar", "La caja ya se encuentra cerrada")
        return
      }
      this.deps.router.navigate(["/new-act-caja-turno", { id: e.id }]);
    }

  }
  eliminar(e: ActCajaTurno) {
    this.deps.utilService.confirmDelete(e).then((result) => {
      if (result) {
        this.actCajaTurnoService.delete(e.id.toString()).subscribe(() => {
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
