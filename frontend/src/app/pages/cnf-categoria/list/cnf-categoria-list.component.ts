
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfCategoria } from '../cnf-categoria.model';
import { CnfCategoriaService } from '../cnf-categoria.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-categoria-list',
  templateUrl: './cnf-categoria-list.component.html',
  styleUrls: ['./cnf-categoria-list.component.css']
})
export class CnfCategoriaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfCategoria = new CnfCategoria();
  dataTable!:DataTables.Api;
  constructor(private cnfCategoriaService: CnfCategoriaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfCategoriaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfCategoriaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfCategoria').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfCategoria) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-categoria", { id: e.id }]);
    }

  }  eliminar(e: CnfCategoria){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfCategoriaService.delete(e.id.toString()).subscribe(() => {
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
