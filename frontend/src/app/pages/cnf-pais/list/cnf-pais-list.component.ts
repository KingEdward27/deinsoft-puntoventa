
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfPais } from '../cnf-pais.model';
import { CnfPaisService } from '../cnf-pais.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-pais-list',
  templateUrl: './cnf-pais-list.component.html',
  styleUrls: ['./cnf-pais-list.component.css']
})
export class CnfPaisListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfPais = new CnfPais();
  dataTable!:DataTables.Api;
  constructor(private cnfPaisService: CnfPaisService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfPaisService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfPaisService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfPais').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfPais) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-pais", { id: e.id }]);
    }

  }  eliminar(e: CnfPais){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfPaisService.delete(e.id.toString()).subscribe(() => {
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
