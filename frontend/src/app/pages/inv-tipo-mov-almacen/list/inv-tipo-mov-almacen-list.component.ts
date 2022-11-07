
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { InvTipoMovAlmacen } from '../inv-tipo-mov-almacen.model';
import { InvTipoMovAlmacenService } from '../inv-tipo-mov-almacen.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-inv-tipo-mov-almacen-list',
  templateUrl: './inv-tipo-mov-almacen-list.component.html',
  styleUrls: ['./inv-tipo-mov-almacen-list.component.css']
})
export class InvTipoMovAlmacenListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : InvTipoMovAlmacen = new InvTipoMovAlmacen();
  dataTable!:DataTables.Api;
  constructor(private invTipoMovAlmacenService: InvTipoMovAlmacenService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.invTipoMovAlmacenService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.invTipoMovAlmacenService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataInvTipoMovAlmacen').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: InvTipoMovAlmacen) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-inv-tipo-mov-almacen", { id: e.id }]);
    }

  }  eliminar(e: InvTipoMovAlmacen){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.invTipoMovAlmacenService.delete(e.id.toString()).subscribe(() => {
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
