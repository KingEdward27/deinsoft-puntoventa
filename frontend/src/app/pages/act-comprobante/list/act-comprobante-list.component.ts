
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActComprobante } from '../act-comprobante.model';
import { ActComprobanteService } from '../act-comprobante.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-comprobante-list',
  templateUrl: './act-comprobante-list.component.html',
  styleUrls: ['./act-comprobante-list.component.css']
})
export class ActComprobanteListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActComprobante = new ActComprobante();
  dataTable!:DataTables.Api;
  constructor(private actComprobanteService: ActComprobanteService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actComprobanteService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actComprobanteService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActComprobante').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActComprobante) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-comprobante", { id: e.id }]);
    }

  }  eliminar(e: ActComprobante){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actComprobanteService.delete(e.id.toString()).subscribe(() => {
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
