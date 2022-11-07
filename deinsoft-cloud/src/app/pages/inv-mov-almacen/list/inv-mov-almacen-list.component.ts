
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Router } from '@angular/router';
import { InvMovAlmacen } from '@/business/model/inv-mov-almacen.model';
import { InvMovAlmacenService } from '@/business/service/inv-mov-almacen.service';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-inv-mov-almacen-list',
  templateUrl: './inv-mov-almacen-list.component.html'
})
export class InvMovAlmacenListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : InvMovAlmacen = new InvMovAlmacen();
  dataTable!:DataTables.Api;
  constructor(private invMovAlmacenService: InvMovAlmacenService,
    private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.invMovAlmacenService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.invMovAlmacenService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataInvMovAlmacen').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: InvMovAlmacen) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-mov-almacen", { id: e.id }]);
    }

  }  eliminar(e: InvMovAlmacen){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.invMovAlmacenService.delete(e.id.toString()).subscribe(() => {
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
