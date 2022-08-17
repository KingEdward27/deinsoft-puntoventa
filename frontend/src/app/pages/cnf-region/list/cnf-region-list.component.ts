
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfRegion } from '../cnf-region.model';
import { CnfRegionService } from '../cnf-region.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-region-list',
  templateUrl: './cnf-region-list.component.html',
  styleUrls: ['./cnf-region-list.component.css']
})
export class CnfRegionListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfRegion = new CnfRegion();
  dataTable!:DataTables.Api;
  constructor(private cnfRegionService: CnfRegionService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfRegionService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfRegionService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfRegion').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfRegion) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-region", { id: e.id }]);
    }

  }  eliminar(e: CnfRegion){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfRegionService.delete(e.id.toString()).subscribe(() => {
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
