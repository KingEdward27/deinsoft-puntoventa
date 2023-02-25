
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfPlanContrato } from '../cnf-plan-contrato.model';
import { CnfPlanContratoService } from '../cnf-plan-contrato.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-plan-contrato-list',
  templateUrl: './cnf-plan-contrato-list.component.html',
  styleUrls: ['./cnf-plan-contrato-list.component.css']
})
export class CnfPlanContratoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfPlanContrato = new CnfPlanContrato();
  dataTable!:DataTables.Api;
  constructor(private cnfPlanContratoService: CnfPlanContratoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfPlanContratoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfPlanContratoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfPlanContrato').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfPlanContrato) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-plan-contrato", { id: e.id }]);
    }

  }  eliminar(e: CnfPlanContrato){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfPlanContratoService.delete(e.id.toString()).subscribe(() => {
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
