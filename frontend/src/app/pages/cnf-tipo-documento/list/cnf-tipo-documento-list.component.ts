
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfTipoDocumento } from '../cnf-tipo-documento.model';
import { CnfTipoDocumentoService } from '../cnf-tipo-documento.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-tipo-documento-list',
  templateUrl: './cnf-tipo-documento-list.component.html',
  styleUrls: ['./cnf-tipo-documento-list.component.css']
})
export class CnfTipoDocumentoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfTipoDocumento = new CnfTipoDocumento();
  dataTable!:DataTables.Api;
  constructor(private cnfTipoDocumentoService: CnfTipoDocumentoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfTipoDocumentoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfTipoDocumentoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfTipoDocumento').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfTipoDocumento) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-tipo-documento", { id: e.id }]);
    }

  }  eliminar(e: CnfTipoDocumento){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfTipoDocumentoService.delete(e.id.toString()).subscribe(() => {
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
