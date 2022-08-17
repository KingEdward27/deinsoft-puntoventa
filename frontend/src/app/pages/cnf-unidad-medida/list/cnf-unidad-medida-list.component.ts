
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfUnidadMedida } from '../cnf-unidad-medida.model';
import { CnfUnidadMedidaService } from '../cnf-unidad-medida.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-unidad-medida-list',
  templateUrl: './cnf-unidad-medida-list.component.html',
  styleUrls: ['./cnf-unidad-medida-list.component.css']
})
export class CnfUnidadMedidaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfUnidadMedida = new CnfUnidadMedida();
  dataTable!:DataTables.Api;
  constructor(private cnfUnidadMedidaService: CnfUnidadMedidaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfUnidadMedidaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfUnidadMedidaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfUnidadMedida').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfUnidadMedida) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-unidad-medida", { id: e.id }]);
    }

  }  eliminar(e: CnfUnidadMedida){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfUnidadMedidaService.delete(e.id.toString()).subscribe(() => {
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
