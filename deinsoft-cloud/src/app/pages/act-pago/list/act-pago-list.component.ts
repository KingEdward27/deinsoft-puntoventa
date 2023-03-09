
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActPagoService } from '../../../business/service/act-pago.service';
import { ActPago } from '../../../business/model/act-pago.model';
import { CommonReportFormComponent, MyBaseComponentDependences } from '../../reports/base/common-report.component';
import { NgbDateParserFormatter, NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '../../../base/util/CustomDate';
@Component({
  selector: 'app-act-pago-list',
  templateUrl: './act-pago-list.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class ActPagoListComponent extends CommonReportFormComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActPago = new ActPago();
  dataTable!:DataTables.Api;
  
  totalIgv = 0;
  totalSubTotal = 0;
  totalTotal = 0;
  constructor(private actPagoService: ActPagoService, public deps: MyBaseComponentDependences) { 
    super(deps);
  }

  ngOnInit(): void {
    super.ngOnInit();
  }
  // public getAllDataByFilters() {
  //   this.actPagoService.getAllData(this.modelSearch).subscribe(data => {
  //     this.lista = data;
  //   });
  // }
  // public getAllData() {
  //   this.actPagoService.getAllData(this.modelSearch).subscribe(data => {
  //     this.lista = data;
  //     setTimeout(()=>{  
  //       this.dataTable = $('#dtDataActPago').DataTable(this.utilService.datablesSettingsNoOrderable); 
  //       }, 0);
  //     console.log(data);
  //     this.dataTable?.destroy();
  //   });
  // }
  getListData() {
    this.model.flagIsventa = '1';
    
    this.totalIgv = 0;
    this.totalSubTotal = 0;
    this.totalTotal = 0;
    return this.deps.actPagoService.getReport(this.model).subscribe(data => {
      this.listData = data;
      // this.listData.forEach(element => {
      //   if (element.flagIngreso != '1') {
      //     this.totalSalida = this.totalSalida + element.monto
      //   } else {
      //     this.totalIngreso = this.totalIngreso + element.monto
      //   }
      // });
      setTimeout(() => {
        this.dataTable = $('#dtDataActPago').DataTable(this.deps.utilService.datablesSettingsNoOrderable);
      }, 1);
      this.dataTable?.destroy();
      this.listData.forEach(element => {
        this.totalIgv = this.totalIgv + element.igv
        this.totalSubTotal = this.totalSubTotal + element.subtotal
        this.totalTotal = this.totalTotal + element.total
      });
      console.log(data);
    })
  }
editar(e: ActPago) {
    if (this.deps.utilService.validateDeactivate(e)) {
      this.deps.router.navigate(["/new-act-pago", { id: e.id }]);
    }

  }  
  eliminar(e: ActPago){
	 this.deps.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actPagoService.delete(e.id.toString()).subscribe(() => {
          this.deps.utilService.msgOkDelete();
          this.getListData();
        },err => {
          if(err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")){
            this.deps.utilService.msgProblemDelete();
          }
        });
      }
      
    });
  }
}
