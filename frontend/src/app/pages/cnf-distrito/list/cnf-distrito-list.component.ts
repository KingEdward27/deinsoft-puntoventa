
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfDistrito } from '../cnf-distrito.model';
import { CnfDistritoService } from '../cnf-distrito.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-distrito-list',
  templateUrl: './cnf-distrito-list.component.html',
  styleUrls: ['./cnf-distrito-list.component.css']
})
export class CnfDistritoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfDistrito = new CnfDistrito();
  dataTable!:DataTables.Api;
  constructor(private cnfDistritoService: CnfDistritoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfDistritoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfDistritoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfDistrito').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfDistrito) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-distrito", { id: e.id }]);
    }

  }  eliminar(e: CnfDistrito){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfDistritoService.delete(e.id.toString()).subscribe(() => {
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
