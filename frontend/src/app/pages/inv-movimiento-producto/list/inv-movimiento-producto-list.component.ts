
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { InvMovimientoProducto } from '../inv-movimiento-producto.model';
import { InvMovimientoProductoService } from '../inv-movimiento-producto.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-inv-movimiento-producto-list',
  templateUrl: './inv-movimiento-producto-list.component.html',
  styleUrls: ['./inv-movimiento-producto-list.component.css']
})
export class InvMovimientoProductoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : InvMovimientoProducto = new InvMovimientoProducto();
  dataTable!:DataTables.Api;
  constructor(private invMovimientoProductoService: InvMovimientoProductoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.invMovimientoProductoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.invMovimientoProductoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataInvMovimientoProducto').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: InvMovimientoProducto) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-inv-movimiento-producto", { id: e.id }]);
    }

  }  eliminar(e: InvMovimientoProducto){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.invMovimientoProductoService.delete(e.id.toString()).subscribe(() => {
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
