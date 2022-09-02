
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { SegRol } from '../seg-rol.model';
import { SegRolService } from '../seg-rol.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-seg-rol-list',
  templateUrl: './seg-rol-list.component.html',
  styleUrls: ['./seg-rol-list.component.css']
})
export class SegRolListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : SegRol = new SegRol();
  dataTable!:DataTables.Api;
  constructor(private segRolService: SegRolService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.segRolService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.segRolService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataSegRol').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: SegRol) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-seg-rol", { id: e.id }]);
    }

  }  eliminar(e: SegRol){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.segRolService.delete(e.id.toString()).subscribe(() => {
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
