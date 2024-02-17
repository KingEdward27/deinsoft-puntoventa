
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfMenuTipoSistema } from '../cnf-menu-tipo-sistema.model';
import { CnfMenuTipoSistemaService } from '../cnf-menu-tipo-sistema.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-menu-tipo-sistema-list',
  templateUrl: './cnf-menu-tipo-sistema-list.component.html',
  styleUrls: ['./cnf-menu-tipo-sistema-list.component.css']
})
export class CnfMenuTipoSistemaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfMenuTipoSistema = new CnfMenuTipoSistema();
  dataTable!:DataTables.Api;
  constructor(private cnfMenuTipoSistemaService: CnfMenuTipoSistemaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfMenuTipoSistemaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfMenuTipoSistemaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfMenuTipoSistema').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfMenuTipoSistema) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-menu-tipo-sistema", { id: e.id }]);
    }

  }  eliminar(e: CnfMenuTipoSistema){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfMenuTipoSistemaService.delete(e.id.toString()).subscribe(() => {
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
