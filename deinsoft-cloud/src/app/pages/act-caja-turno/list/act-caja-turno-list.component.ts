import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
import { ActCajaTurno } from '@/business/model/act-caja-turno.model';
import { ActCajaTurnoService } from '@/business/service/act-caja-turno.service';
import { CommonReportFormComponent, MyBaseComponentDependences } from '../../reports/base/common-report.component';
import { NgbDateParserFormatter, NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '../../../base/util/CustomDate';
import { data } from 'jquery';
import { CommonService } from '@/base/services/common.service';
import { UpdateParam } from '@/base/components/model/UpdateParam';
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
  business:string = 'act-caja-turno'
  constructor(private actCajaTurnoService: ActCajaTurnoService, public deps: MyBaseComponentDependences,private commonService: CommonService) {
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
  // print(item: any) {
  //   console.log(item);
    
  //   this.actCajaTurnoService.getReportCierre(item.id).subscribe(data => {
      
  //     console.log(data);
      
  //   });
  // }
  printTicketChild(item: any){
    let myMap = new Map();
    myMap.set("id", item.id);
    myMap.set("tipo", 2);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);
    mp.map = convMapDetail;
    this.commonService.updateParam = mp;
    
    this.commonService.genericPostRequest("/api/business/"+this.business+"/get-cierre-act-caja-turno", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        this.generateAttachment("cierre_caja",blob, extension);
      }

    });
    // console.log("enviando a imprimir: ",this.properties.listData);
  }

  // generateAttachment(blob: Blob, extension: string) {
  //   const data = window.URL.createObjectURL(blob);
  //   const link = document.createElement('a');
  //   link.href = data;
  //   link.download = "report." + extension;
  //   link.dispatchEvent(new MouseEvent('click', {
  //     bubbles: true, cancelable: true, view: window
  //   }));
  //   setTimeout(() => {
  //     window.URL.revokeObjectURL(data);
  //     link.remove
  //   }, 100);
  // }
}
