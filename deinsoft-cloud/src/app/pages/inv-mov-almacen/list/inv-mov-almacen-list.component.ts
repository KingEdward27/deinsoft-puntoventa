
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Router } from '@angular/router';
import { InvMovAlmacen } from '@/business/model/inv-mov-almacen.model';
import { InvMovAlmacenService } from '@/business/service/inv-mov-almacen.service';
import { UtilService } from '@services/util.service';
import { CommonReportFormComponent, MyBaseComponentDependences } from '@pages/reports/base/common-report.component';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
@Component({
  selector: 'app-inv-mov-almacen-list',
  templateUrl: './inv-mov-almacen-list.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class InvMovAlmacenListComponent extends CommonReportFormComponent implements OnInit {
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch: string = "";
  // modelSearch: InvMovAlmacen = new InvMovAlmacen();
  dataTable!: DataTables.Api;
  // constructor(private invMovAlmacenService: InvMovAlmacenService,
  //   private utilService:UtilService, private router: Router) { }

  constructor(public deps: MyBaseComponentDependences) {
    super(deps);
  }
  ngOnInit(): void {
    
    this.isDataLoaded = false;
    this.titleExport = "Reporte de Movimentos de Almacen"
    super.ngOnInit();
  }
  // public getAllDataByFilters() {
  //   this.deps.invMovAlmacenService.getAllData(this.modelSearch)
  //     .subscribe(data => {
  //       this.lista = data;
  //     });
  // }
  public getListData() {
    this.deps.invMovAlmacenService.getReport(this.model).subscribe(data => {
      this.lista = data;
      setTimeout(() => {
        this.dataTable = $('#dtDataInvMovAlmacen').DataTable(this.deps.utilService.datablesSettings);
      }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
  editar(e: InvMovAlmacen) {
    if (this.deps.utilService.validateDeactivate(e)) {
      this.deps.router.navigate(["/new-mov-almacen", { id: e.id }]);
    }

  }
  eliminar(e: InvMovAlmacen) {
    this.deps.utilService.confirmOperation(e).then((result) => {
      if (result) {
        this.deps.invMovAlmacenService.delete(e.id.toString()).subscribe(() => {
          this.deps.utilService.msgOkOperation();
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
