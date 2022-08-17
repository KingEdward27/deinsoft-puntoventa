
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfMarca } from '../cnf-marca.model';
import { CnfMarcaService } from '../cnf-marca.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-marca-list',
  templateUrl: './cnf-marca-list.component.html',
  styleUrls: ['./cnf-marca-list.component.css']
})
export class CnfMarcaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfMarca = new CnfMarca();
  dataTable!:DataTables.Api;
  constructor(private cnfMarcaService: CnfMarcaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfMarcaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfMarcaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfMarca').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfMarca) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-marca", { id: e.id }]);
    }

  }  eliminar(e: CnfMarca){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfMarcaService.delete(e.id.toString()).subscribe(() => {
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
