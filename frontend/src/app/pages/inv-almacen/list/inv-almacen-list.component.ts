
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { InvAlmacen } from '../inv-almacen.model';
import { InvAlmacenService } from '../inv-almacen.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-inv-almacen-list',
  templateUrl: './inv-almacen-list.component.html',
  styleUrls: ['./inv-almacen-list.component.css']
})
export class InvAlmacenListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : InvAlmacen = new InvAlmacen();
  dataTable!:DataTables.Api;
  constructor(private invAlmacenService: InvAlmacenService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.invAlmacenService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.invAlmacenService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataInvAlmacen').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: InvAlmacen) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-inv-almacen", { id: e.id }]);
    }

  }  eliminar(e: InvAlmacen){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.invAlmacenService.delete(e.id.toString()).subscribe(() => {
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
