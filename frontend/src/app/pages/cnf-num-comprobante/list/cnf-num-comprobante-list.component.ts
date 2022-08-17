
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfNumComprobante } from '../cnf-num-comprobante.model';
import { CnfNumComprobanteService } from '../cnf-num-comprobante.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-num-comprobante-list',
  templateUrl: './cnf-num-comprobante-list.component.html',
  styleUrls: ['./cnf-num-comprobante-list.component.css']
})
export class CnfNumComprobanteListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfNumComprobante = new CnfNumComprobante();
  dataTable!:DataTables.Api;
  constructor(private cnfNumComprobanteService: CnfNumComprobanteService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfNumComprobanteService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfNumComprobanteService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfNumComprobante').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfNumComprobante) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-num-comprobante", { id: e.id }]);
    }

  }  eliminar(e: CnfNumComprobante){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfNumComprobanteService.delete(e.id.toString()).subscribe(() => {
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
