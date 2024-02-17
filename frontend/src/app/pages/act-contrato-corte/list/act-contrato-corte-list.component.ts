
import { Component, OnInit} from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActContratoCorte } from '../act-contrato-corte.model';
import { ActContratoCorteService } from '../act-contrato-corte.service';
import Swal from 'sweetalert2';
import { State } from 'src/app/models/State';
import { UtilService } from 'src/app/services/util/util.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-act-contrato-corte-list',
  templateUrl: './act-contrato-corte-list.component.html',
  styleUrls: ['./act-contrato-corte-list.component.css']
})
export class ActContratoCorteListComponent implements OnInit{
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch : string = "";
  modelSearch : ActContratoCorte = new ActContratoCorte();
  dataTable!:DataTables.Api;
  constructor(private actContratoCorteService: ActContratoCorteService,private utilService:UtilService, private router: Router) { }

  ngOnInit(): void {
    this.getAllData();
  }
  public getAllDataByFilters() {
    this.actContratoCorteService.getAllData(this.modelSearch)
.subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    this.actContratoCorteService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(()=>{  
        this.dataTable = $('#dtDataActContratoCorte').DataTable(this.utilService.datablesSettings); 
        }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
editar(e: ActContratoCorte) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-act-contrato-corte", { id: e.id }]);
    }

  }  eliminar(e: ActContratoCorte){
	 this.utilService.confirmDelete(e).then((result) => { 
      if(result){
        this.actContratoCorteService.delete(e.id.toString()).subscribe(() => {
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
