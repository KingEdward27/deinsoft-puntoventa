
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActPagoProgramacion } from '../act-pago-programacion.model';
import { ActPagoProgramacionService } from '../act-pago-programacion.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-pago-programacion-list',
  templateUrl: './act-pago-programacion-list.component.html',
  styleUrls: ['./act-pago-programacion-list.component.css']
})
export class ActPagoProgramacionListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActPagoProgramacion = new ActPagoProgramacion();
  dataTable!:DataTables.Api;
  constructor(private actPagoProgramacionService: ActPagoProgramacionService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actPagoProgramacionService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actPagoProgramacionService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActPagoProgramacion').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActPagoProgramacion) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-pago-programacion", { id: e.id }]);
    }

  }  eliminar(e: ActPagoProgramacion){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actPagoProgramacionService.delete(e.id.toString()).subscribe(() => {
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
