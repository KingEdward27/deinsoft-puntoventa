
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActComprobanteDetalle } from '../act-comprobante-detalle.model';
import { ActComprobanteDetalleService } from '../act-comprobante-detalle.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-comprobante-detalle-list',
  templateUrl: './act-comprobante-detalle-list.component.html',
  styleUrls: ['./act-comprobante-detalle-list.component.css']
})
export class ActComprobanteDetalleListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActComprobanteDetalle = new ActComprobanteDetalle();
  dataTable!:DataTables.Api;
  constructor(private actComprobanteDetalleService: ActComprobanteDetalleService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actComprobanteDetalleService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actComprobanteDetalleService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActComprobanteDetalle').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActComprobanteDetalle) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-comprobante-detalle", { id: e.id }]);
    }

  }  eliminar(e: ActComprobanteDetalle){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actComprobanteDetalleService.delete(e.id.toString()).subscribe(() => {
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
