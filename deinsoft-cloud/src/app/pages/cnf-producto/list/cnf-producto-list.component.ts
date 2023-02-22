import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfProducto } from '../cnf-producto.model';
import { CnfProductoService } from '../cnf-producto.service';
import { Router } from '@angular/router';
import { UtilService } from '../../../services/util.service';
@Component({
  selector: 'app-cnf-producto-list',
  templateUrl: './cnf-producto-list.component.html'
})
export class CnfProductoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfProducto = new CnfProducto();
  dataTable!:DataTables.Api;
  constructor(private cnfProductoService: CnfProductoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfProductoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfProductoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfProducto').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfProducto) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-producto", { id: e.id }]);
    }

  }  eliminar(e: CnfProducto){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfProductoService.delete(e.id.toString()).subscribe(() => {
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
