
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfImpuestoCondicion } from '../cnf-impuesto-condicion.model';
import { CnfImpuestoCondicionService } from '../cnf-impuesto-condicion.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-impuesto-condicion-list',
  templateUrl: './cnf-impuesto-condicion-list.component.html',
  styleUrls: ['./cnf-impuesto-condicion-list.component.css']
})
export class CnfImpuestoCondicionListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfImpuestoCondicion = new CnfImpuestoCondicion();
  dataTable!:DataTables.Api;
  constructor(private cnfImpuestoCondicionService: CnfImpuestoCondicionService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfImpuestoCondicionService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfImpuestoCondicionService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfImpuestoCondicion').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfImpuestoCondicion) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-impuesto-condicion", { id: e.id }]);
    }

  }  eliminar(e: CnfImpuestoCondicion){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfImpuestoCondicionService.delete(e.id.toString()).subscribe(() => {
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
