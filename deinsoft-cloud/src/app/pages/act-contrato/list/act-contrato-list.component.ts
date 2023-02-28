
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActContrato } from '../act-contrato.model';
import { ActContratoService } from '../act-contrato.service';
import { Router } from '@angular/router';
import { UtilService } from '../../../services/util.service';
@Component({
  selector: 'app-act-contrato-list',
  templateUrl: './act-contrato-list.component.html'
})
export class ActContratoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActContrato = new ActContrato();
  dataTable!:DataTables.Api;
  constructor(private actContratoService: ActContratoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actContratoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actContratoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActContrato').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActContrato) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-contrato", { id: e.id }]);
    }

  }  eliminar(e: ActContrato){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actContratoService.delete(e.id.toString()).subscribe(() => {
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
