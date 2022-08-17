
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfLocal } from '../cnf-local.model';
import { CnfLocalService } from '../cnf-local.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-local-list',
  templateUrl: './cnf-local-list.component.html',
  styleUrls: ['./cnf-local-list.component.css']
})
export class CnfLocalListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfLocal = new CnfLocal();
  dataTable!:DataTables.Api;
  constructor(private cnfLocalService: CnfLocalService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfLocalService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfLocalService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfLocal').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfLocal) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-local", { id: e.id }]);
    }

  }  eliminar(e: CnfLocal){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfLocalService.delete(e.id.toString()).subscribe(() => {
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
