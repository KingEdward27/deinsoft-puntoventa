
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfMoneda } from '../cnf-moneda.model';
import { CnfMonedaService } from '../cnf-moneda.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-moneda-list',
  templateUrl: './cnf-moneda-list.component.html',
  styleUrls: ['./cnf-moneda-list.component.css']
})
export class CnfMonedaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfMoneda = new CnfMoneda();
  dataTable!:DataTables.Api;
  constructor(private cnfMonedaService: CnfMonedaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfMonedaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfMonedaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfMoneda').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfMoneda) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-moneda", { id: e.id }]);
    }

  }  eliminar(e: CnfMoneda){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfMonedaService.delete(e.id.toString()).subscribe(() => {
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
