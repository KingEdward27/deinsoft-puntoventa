import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
import { ActCajaTurno } from '@/business/model/act-caja-turno.model';
import { ActCajaTurnoService } from '@/business/service/act-caja-turno.service';
@Component({
  selector: 'app-act-caja-turno-list',
  templateUrl: './act-caja-turno-list.component.html'
})
export class ActCajaTurnoListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActCajaTurno = new ActCajaTurno();
  dataTable!:DataTables.Api;
  constructor(private actCajaTurnoService: ActCajaTurnoService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actCajaTurnoService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actCajaTurnoService.getAllData(this.modelSearch).subscribe(data => {
      console.log(data);
      
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActCajaTurno').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActCajaTurno) {
    if (this.utilService.validateDeactivate(e)) {
      if (e.estado == '0') {
        this.utilService.msgWarning("No puede continuar","La caja ya se encuentra cerrada")
        return
      }
      this.router.navigate(["/new-act-caja-turno", { id: e.id }]);
    }
    
  }  
  eliminar(e: ActCajaTurno){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actCajaTurnoService.delete(e.id.toString()).subscribe(() => {
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
