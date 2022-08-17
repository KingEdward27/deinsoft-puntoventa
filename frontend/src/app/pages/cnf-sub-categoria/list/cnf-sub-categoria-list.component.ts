
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfSubCategoria } from '../cnf-sub-categoria.model';
import { CnfSubCategoriaService } from '../cnf-sub-categoria.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-sub-categoria-list',
  templateUrl: './cnf-sub-categoria-list.component.html',
  styleUrls: ['./cnf-sub-categoria-list.component.css']
})
export class CnfSubCategoriaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfSubCategoria = new CnfSubCategoria();
  dataTable!:DataTables.Api;
  constructor(private cnfSubCategoriaService: CnfSubCategoriaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfSubCategoriaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfSubCategoriaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfSubCategoria').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfSubCategoria) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-sub-categoria", { id: e.id }]);
    }

  }  eliminar(e: CnfSubCategoria){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfSubCategoriaService.delete(e.id.toString()).subscribe(() => {
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
