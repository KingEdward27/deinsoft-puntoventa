
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActCaja } from '../act-caja.model';
import { ActCajaService } from '../act-caja.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-caja-list',
  templateUrl: './act-caja-list.component.html',
  styleUrls: ['./act-caja-list.component.css']
})
export class ActCajaListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActCaja = new ActCaja();
  dataTable!:DataTables.Api;
  constructor(private actCajaService: ActCajaService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actCajaService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actCajaService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActCaja').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActCaja) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-caja", { id: e.id }]);
    }

  }  eliminar(e: ActCaja){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actCajaService.delete(e.id.toString()).subscribe(() => {
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
