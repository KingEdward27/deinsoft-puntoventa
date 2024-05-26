
import { Component, OnInit } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ActContrato } from '../act-contrato.model';
import { ActContratoService } from '../act-contrato.service';
import { Router } from '@angular/router';
import { UtilService } from '../../../services/util.service';
import { AppService } from '../../../services/app.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ActContratoCorteFormModalComponent } from '@pages/act-contrato-corte/modal/act-contrato-corte-form-modal.component';
import { UploadComponent } from '../modal/message-modal.component';

@Component({
  selector: 'app-act-contrato-list',
  templateUrl: './act-contrato-list.component.html'
})
export class ActContratoListComponent implements OnInit {
  lista: any;
  datatableElement!: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  nameSearch: string = "";
  modelSearch: ActContrato = new ActContrato();
  dataTable!: DataTables.Api;
  
  public modalRef!: NgbModalRef;
  constructor(private actContratoService: ActContratoService,
    private utilService: UtilService,
    private router: Router,
    private appService: AppService,private modalService: NgbModal) { }

  ngOnInit(): void {
    this.getAllData();
    
  }
  public getAllDataByFilters() {
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    this.modelSearch.cnfEmpresaId = cnfEmpresa
    this.actContratoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
    });
  }
  public getAllData() {
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    this.modelSearch.cnfEmpresaId = cnfEmpresa
    this.actContratoService.getAllData(this.modelSearch).subscribe(data => {
      this.lista = data;
      setTimeout(() => {
        this.dataTable = $('#dtDataActContrato').DataTable(this.utilService.datablesSettings);
      }, 0);
      console.log(data);
      this.dataTable?.destroy();
    });
  }
  editar(e: ActContrato) {
    if (this.utilService.validateDeactivate(e)) {
      this.router.navigate(["/new-contrato", { id: e.id }]);
    }

  } 
  eliminar(e: ActContrato) {
    this.utilService.confirmDelete(e).then((result) => {
      if (result) {
        this.actContratoService.delete(e.id.toString()).subscribe(() => {
          this.utilService.msgOkDelete();
          this.getAllData();
        }, err => {
          if (err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")) {
            this.utilService.msgProblemDelete();
          }
        });
      }

    });
  }
  cortarLinea (item:any){
    
    this.modalRef = this.modalService.open(ActContratoCorteFormModalComponent);
    
    this.modalRef.componentInstance.model.actContrato = item;
    // this.modalRef.closed.subscribe(result => {
    //   this.getListCnfMaestro();
    //   // this.model.cnfBpartner = 
    // })
    this.modalRef.componentInstance.cnfMaestro.subscribe((receivedEntry:any) => {
      console.log(receivedEntry);
    })
  }
  exportTemplate() {
    this.actContratoService.export().subscribe(data => {

      console.log(data.body);
      if (data.body.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.body.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ||
          data.body.type == "application/octet-stream") {
          contentType = data.body.type;
          extension = "xlsx";
        }
        const blob = new Blob([data.body], { type: contentType });
        this.generateAttachment("Plantilla subida de videos", blob, extension);
      }
    });
  }

  import() {
    this.modalRef = this.modalService.open(UploadComponent, { size: 'lg' });
      this.modalRef.componentInstance.message = "Importar registros de videos";
      // this.modalRef.closed.subscribe(result => {
      //   this.router.navigate(["/venta"]);
      // })
  }
  
  generateAttachment(fileName: string, blob: Blob, extension: string) {
    const data = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = data;
    link.download = fileName+"." + extension;
    link.dispatchEvent(new MouseEvent('click', {
      bubbles: true, cancelable: true, view: window
    }));
    setTimeout(() => {
      window.URL.revokeObjectURL(data);
      link.remove
    }, 100);
  }
}
