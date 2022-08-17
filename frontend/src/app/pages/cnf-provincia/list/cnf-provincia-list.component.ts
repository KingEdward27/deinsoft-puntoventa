
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfProvincia } from '../cnf-provincia.model';
import { CnfProvinciaService } from '../cnf-provincia.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-provincia-list',
  templateUrl: './cnf-provincia-list.component.html',
  styleUrls: ['./cnf-provincia-list.component.css']
})
export class CnfProvinciaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfProvincia = new CnfProvincia();
  dataTable!:DataTables.Api;
  constructor(private cnfProvinciaService: CnfProvinciaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfProvinciaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfProvinciaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfProvincia').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfProvincia) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-provincia", { id: e.id }]);
    }

  }  eliminar(e: CnfProvincia){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfProvinciaService.delete(e.id.toString()).subscribe(() => {
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
