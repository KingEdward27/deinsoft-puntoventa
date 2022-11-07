
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { InvMovAlmacenDet } from '../inv-mov-almacen-det.model';
import { InvMovAlmacenDetService } from '../inv-mov-almacen-det.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-inv-mov-almacen-det-list',
  templateUrl: './inv-mov-almacen-det-list.component.html',
  styleUrls: ['./inv-mov-almacen-det-list.component.css']
})
export class InvMovAlmacenDetListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : InvMovAlmacenDet = new InvMovAlmacenDet();
  dataTable!:DataTables.Api;
  constructor(private invMovAlmacenDetService: InvMovAlmacenDetService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.invMovAlmacenDetService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.invMovAlmacenDetService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataInvMovAlmacenDet').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: InvMovAlmacenDet) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-inv-mov-almacen-det", { id: e.id }]);
    }

  }  eliminar(e: InvMovAlmacenDet){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.invMovAlmacenDetService.delete(e.id.toString()).subscribe(() => {
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
