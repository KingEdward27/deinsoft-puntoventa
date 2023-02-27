
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActPagoDetalle } from '../act-pago-detalle.model';
import { ActPagoDetalleService } from '../act-pago-detalle.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-pago-detalle-list',
  templateUrl: './act-pago-detalle-list.component.html',
  styleUrls: ['./act-pago-detalle-list.component.css']
})
export class ActPagoDetalleListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActPagoDetalle = new ActPagoDetalle();
  dataTable!:DataTables.Api;
  constructor(private actPagoDetalleService: ActPagoDetalleService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actPagoDetalleService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actPagoDetalleService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActPagoDetalle').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActPagoDetalle) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-pago-detalle", { id: e.id }]);
    }

  }  eliminar(e: ActPagoDetalle){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actPagoDetalleService.delete(e.id.toString()).subscribe(() => {
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
