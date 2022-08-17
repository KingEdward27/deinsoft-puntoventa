
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfEmpresa } from '../cnf-empresa.model';
import { CnfEmpresaService } from '../cnf-empresa.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-empresa-list',
  templateUrl: './cnf-empresa-list.component.html',
  styleUrls: ['./cnf-empresa-list.component.css']
})
export class CnfEmpresaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfEmpresa = new CnfEmpresa();
  dataTable!:DataTables.Api;
  constructor(private cnfEmpresaService: CnfEmpresaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfEmpresaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfEmpresaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfEmpresa').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfEmpresa) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-empresa", { id: e.id }]);
    }

  }  eliminar(e: CnfEmpresa){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfEmpresaService.delete(e.id.toString()).subscribe(() => {
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
