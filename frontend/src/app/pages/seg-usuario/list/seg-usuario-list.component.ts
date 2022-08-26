
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { SegUsuario } from '../seg-usuario.model';
import { SegUsuarioService } from '../seg-usuario.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-seg-usuario-list',
  templateUrl: './seg-usuario-list.component.html',
  styleUrls: ['./seg-usuario-list.component.css']
})
export class SegUsuarioListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : SegUsuario = new SegUsuario();
  dataTable!:DataTables.Api;
  constructor(private segUsuarioService: SegUsuarioService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.segUsuarioService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.segUsuarioService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataSegUsuario').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: SegUsuario) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-seg-usuario", { id: e.id }]);
    }

  }  eliminar(e: SegUsuario){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.segUsuarioService.delete(e.id.toString()).subscribe(() => {
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
