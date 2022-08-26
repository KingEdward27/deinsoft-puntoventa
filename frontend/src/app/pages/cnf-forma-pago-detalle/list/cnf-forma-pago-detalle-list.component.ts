
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfFormaPagoDetalle } from '../cnf-forma-pago-detalle.model';
import { CnfFormaPagoDetalleService } from '../cnf-forma-pago-detalle.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-forma-pago-detalle-list',
  templateUrl: './cnf-forma-pago-detalle-list.component.html',
  styleUrls: ['./cnf-forma-pago-detalle-list.component.css']
})
export class CnfFormaPagoDetalleListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfFormaPagoDetalle = new CnfFormaPagoDetalle();
  dataTable!:DataTables.Api;
  constructor(private cnfFormaPagoDetalleService: CnfFormaPagoDetalleService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfFormaPagoDetalleService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfFormaPagoDetalleService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfFormaPagoDetalle').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfFormaPagoDetalle) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-forma-pago-detalle", { id: e.id }]);
    }

  }  eliminar(e: CnfFormaPagoDetalle){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfFormaPagoDetalleService.delete(e.id.toString()).subscribe(() => {
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
