
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfTipoSistema } from '../cnf-tipo-sistema.model';
import { CnfTipoSistemaService } from '../cnf-tipo-sistema.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-tipo-sistema-list',
  templateUrl: './cnf-tipo-sistema-list.component.html',
  styleUrls: ['./cnf-tipo-sistema-list.component.css']
})
export class CnfTipoSistemaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfTipoSistema = new CnfTipoSistema();
  dataTable!:DataTables.Api;
  constructor(private cnfTipoSistemaService: CnfTipoSistemaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfTipoSistemaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfTipoSistemaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfTipoSistema').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfTipoSistema) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-tipo-sistema", { id: e.id }]);
    }

  }  eliminar(e: CnfTipoSistema){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfTipoSistemaService.delete(e.id.toString()).subscribe(() => {
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
