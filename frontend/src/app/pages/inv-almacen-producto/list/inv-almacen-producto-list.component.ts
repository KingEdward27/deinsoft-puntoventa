
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { InvAlmacenProducto } from '../inv-almacen-producto.model';
import { InvAlmacenProductoService } from '../inv-almacen-producto.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-inv-almacen-producto-list',
  templateUrl: './inv-almacen-producto-list.component.html',
  styleUrls: ['./inv-almacen-producto-list.component.css']
})
export class InvAlmacenProductoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : InvAlmacenProducto = new InvAlmacenProducto();
  dataTable!:DataTables.Api;
  constructor(private invAlmacenProductoService: InvAlmacenProductoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.invAlmacenProductoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.invAlmacenProductoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataInvAlmacenProducto').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: InvAlmacenProducto) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-inv-almacen-producto", { id: e.id }]);
    }

  }  eliminar(e: InvAlmacenProducto){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.invAlmacenProductoService.delete(e.id.toString()).subscribe(() => {
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
