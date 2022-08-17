
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfFormaPago } from '../cnf-forma-pago.model';
import { CnfFormaPagoService } from '../cnf-forma-pago.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-forma-pago-list',
  templateUrl: './cnf-forma-pago-list.component.html',
  styleUrls: ['./cnf-forma-pago-list.component.css']
})
export class CnfFormaPagoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfFormaPago = new CnfFormaPago();
  dataTable!:DataTables.Api;
  constructor(private cnfFormaPagoService: CnfFormaPagoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfFormaPagoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfFormaPagoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfFormaPago').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfFormaPago) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-forma-pago", { id: e.id }]);
    }

  }  eliminar(e: CnfFormaPago){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfFormaPagoService.delete(e.id.toString()).subscribe(() => {
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
