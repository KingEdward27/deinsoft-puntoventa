
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { CnfTipoComprobante } from '../cnf-tipo-comprobante.model';
import { CnfTipoComprobanteService } from '../cnf-tipo-comprobante.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-cnf-tipo-comprobante-list',
  templateUrl: './cnf-tipo-comprobante-list.component.html',
  styleUrls: ['./cnf-tipo-comprobante-list.component.css']
})
export class CnfTipoComprobanteListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : CnfTipoComprobante = new CnfTipoComprobante();
  dataTable!:DataTables.Api;
  constructor(private cnfTipoComprobanteService: CnfTipoComprobanteService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.cnfTipoComprobanteService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.cnfTipoComprobanteService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataCnfTipoComprobante').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: CnfTipoComprobante) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-cnf-tipo-comprobante", { id: e.id }]);
    }

  }  eliminar(e: CnfTipoComprobante){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.cnfTipoComprobanteService.delete(e.id.toString()).subscribe(() => {
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
