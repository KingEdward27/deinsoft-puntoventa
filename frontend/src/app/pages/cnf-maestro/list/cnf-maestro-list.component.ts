
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfMaestro } from '../cnf-maestro.model';
import { CnfMaestroService } from '../cnf-maestro.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-maestro-list',
  templateUrl: './cnf-maestro-list.component.html',
  styleUrls: ['./cnf-maestro-list.component.css']
})
export class CnfMaestroListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfMaestro = new CnfMaestro();
  dataTable!:DataTables.Api;
  constructor(private cnfMaestroService: CnfMaestroService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfMaestroService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfMaestroService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfMaestro').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfMaestro) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-maestro", { id: e.id }]);
    }

  }  eliminar(e: CnfMaestro){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfMaestroService.delete(e.id.toString()).subscribe(() => {
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
