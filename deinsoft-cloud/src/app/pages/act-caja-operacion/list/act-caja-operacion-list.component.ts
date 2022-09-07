
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
import { ActCajaOperacionService } from '@/business/service/act-caja-operacion.service';
import { ActCajaOperacion } from '@/business/model/act-caja-operacion.model';

@Component({
  selector: 'app-act-caja-operacion-list',
  templateUrl: './act-caja-operacion-list.component.html'
})
export class ActCajaOperacionListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActCajaOperacion = new ActCajaOperacion();
  dataTable!:DataTables.Api;
  totalSalida:any = 0
  totalIngreso:any = 0
  constructor(private actCajaOperacionService: ActCajaOperacionService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actCajaOperacionService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.totalSalida = 0
    this.totalIngreso = 0
    this.actCajaOperacionService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      this.lista.forEach(element => {
        if (element.flagIngreso != '1') {
          this.totalSalida = this.totalSalida + element.monto
        } else {
          this.totalIngreso = this.totalIngreso + element.monto
        }
      });
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActCajaOperacion').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActCajaOperacion) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-caja-operacion", { id: e.id }]);
    }

  }  eliminar(e: ActCajaOperacion){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actCajaOperacionService.delete(e.id.toString()).subscribe(() => {
          this.utilService.msgOkDelete();
          this.getAllData();
        },err => {
          if(err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")){
            this.utilService.msgProblemDelete();
          }
        });
      }
      
    });
  }
}
