
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActCajaOperacionService } from '@/business/service/act-caja-operacion.service';
import { ActCajaOperacion } from '@/business/model/act-caja-operacion.model';
import { CnfMaestro } from '../../../business/model/cnf-maestro.model';
import { CommonReportFormComponent, MyBaseComponentDependences } from '../../reports/base/common-report.component';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '../../../base/util/CustomDate';

@Component({
  selector: 'app-act-caja-operacion-list',
  templateUrl: './act-caja-operacion-list.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
    MyBaseComponentDependences
  ]
})
export class ActCajaOperacionListComponent extends CommonReportFormComponent implements OnInit {
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch: string = "";
  modelSearch: ActCajaOperacion = new ActCajaOperacion();
  dataTable!: DataTables.Api;
  totalSalida: any = 0
  totalIngreso: any = 0

  selectDefaultCnfMaestro: any = { id: 0, nombre: "- Seleccione -" };
  listCnfMaestro: any;
  cnfMaestro: CnfMaestro = new CnfMaestro();
  loadingCnfMaestro: boolean = false;

  listCnfLocal: any;
  loadingCnfLocal: boolean = false;
  selectDefaultCnfLocal: any = { id: 0, nombre: "- Seleccione -" };
  listCnfTipoComprobante: any;

  constructor(private actCajaOperacionService: ActCajaOperacionService, public deps: MyBaseComponentDependences) {
    super(deps);
  }

  ngOnInit(): void {
    super.ngOnInit();
    // this.getAllData();
  }
  // public getAllDataByFilters() {
  //   this.actCajaOperacionService.getAllData(this.modelSearch)
  //     .subscribe(data => {
  //       this.lista = data;
  //     });
  // }
  getListData() {
    this.model.flagIsventa = '1';
    console.log(this.model);
    this.totalSalida = 0;
    this.totalIngreso = 0;
    return this.deps.actCajaOperacionService.getReport(this.model).subscribe(data => {
      this.listData = data;
      this.listData.forEach(element => {
        if (element.flagIngreso != '1') {
          this.totalSalida = this.totalSalida + element.monto
        } else {
          this.totalIngreso = this.totalIngreso + element.monto
        }
      });
      setTimeout(() => {
        this.dataTable = $('#dtDataActCajaOperacion').DataTable(this.datablesSettings);
      }, 1);
      this.dataTable?.destroy();
      console.log(data);
    })
  }
  // public getAllData() {
  //   this.totalSalida = 0
  //   this.totalIngreso = 0
  //   this.actCajaOperacionService.getAllData(this.modelSearch).subscribe(data => {
  //     this.lista = data;
  //     this.lista.forEach(element => {
  //       if (element.flagIngreso != '1') {
  //         this.totalSalida = this.totalSalida + element.monto
  //       } else {
  //         this.totalIngreso = this.totalIngreso + element.monto
  //       }
  //     });
  //     setTimeout(() => {
  //       this.dataTable = $('#dtDataActCajaOperacion').DataTable(this.deps.utilService.datablesSettings);
  //     }, 0);
  //     console.log(data);
  //     this.dataTable?.destroy();
  //   });
  // }
  editar(e: ActCajaOperacion) {
    if (this.deps.utilService.validateDeactivate(e)) {
      this.deps.router.navigate(["/new-caja-operacion", { id: e.id }]);
    }

  }
  eliminar(e: ActCajaOperacion) {
    this.deps.utilService.confirmDelete(e).then((result) => {
      if (result) {
        this.actCajaOperacionService.delete(e.id.toString()).subscribe(() => {
          this.deps.utilService.msgOkDelete();
        }, err => {
          if (err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")) {
            this.deps.utilService.msgProblemDelete();
          }
        });
      }

    });
  }
  // getListCnfMaestro() {
  //   this.loadingCnfMaestro = true;
  //   let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
  //   if(cnfEmpresa == '*') {
  //     return this.cnfMaestroService.getAllDataCombo().subscribe(data => {
  //       this.listCnfMaestro = data;
  //       this.loadingCnfMaestro = false;
  //     })
  //   }else{
  //     return this.cnfMaestroService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
  //       this.listCnfMaestro = data;
  //       this.loadingCnfMaestro = false;
  //     })
  //   }


  // }
  // compareCnfMaestro(a1: CnfMaestro, a2: CnfMaestro): boolean {
  //   if (a1 === undefined && a2 === undefined) {
  //     return true;
  //   }

  //   return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
  //     ? false : a1.id === a2.id;
  // }
  // getListCnfLocal() {
  //   this.loadingCnfLocal = true;
  //   let user = this.appService.getProfile();
  //   console.log(user);
  //   let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
  //   if(cnfEmpresa == '*') {
  //     return this.cnfLocalService.getAllDataCombo().subscribe(data => {
  //       this.listCnfLocal = data;
  //       this.loadingCnfLocal = false;
  //     })
  //   }else{
  //     return this.cnfLocalService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
  //       this.listCnfLocal = data;
  //       this.loadingCnfLocal = false;


  //       if(this.listCnfLocal.length == 1) {
  //         this.cnfLocalService.getData(this.listCnfLocal[0].id).subscribe(data => {
  //           this.modelSearch.cnfLocal = data;

  //         })
  //         // this.model.cnfLocal.id = this.listCnfLocal[0].id;
  //         // if (this.listInvAlmacen.length == 1){
  //         //   this.model.invAlmacen.id = this.listInvAlmacen[0].id;
  //         // }
  //       }
  //     })
  //   }


  // }
  // compareCnfLocal(a1: CnfLocal, a2: CnfLocal): boolean {
  //   if (a1 === undefined && a2 === undefined) {
  //     return true;
  //   }

  //   return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
  //     ? false : a1.id === a2.id;
  // }
}
