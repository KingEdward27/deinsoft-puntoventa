
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActCajaOperacion } from '../act-caja-operacion.model';
import { ActCajaOperacionService } from '../act-caja-operacion.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-caja-operacion-list',
  templateUrl: './act-caja-operacion-list.component.html',
  styleUrls: ['./act-caja-operacion-list.component.css']
})
export class ActCajaOperacionListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActCajaOperacion = new ActCajaOperacion();
  dataTable!:DataTables.Api;
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
    this.actCajaOperacionService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActCajaOperacion').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActCajaOperacion) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-caja-operacion", { id: e.id }]);
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
