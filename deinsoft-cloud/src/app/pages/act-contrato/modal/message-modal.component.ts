import { UpdateParam } from '@/base/components/model/UpdateParam';
import { CommonService } from '@/base/services/common.service';
import { AfterViewInit, Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
// import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbActiveModal, NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'environments/environment';
import { UtilService } from '@services/util.service';
import { ActContratoService } from '../act-contrato.service';
import { MediaService } from '../media.service';
import { CnfLocal } from '@/business/model/cnf-local.model';
import { CnfLocalService } from '@/business/service/cnf-local.service';
import { AppService } from '@services/app.service';




@Component({
  selector: 'app-message-modal',
  templateUrl: './message-modal.component.html'
})
export class UploadComponent implements OnInit {

  //generic variables
  error: any;
  headers :any;
  listDetail: any;
  load:boolean = false;
  listDetail2: any[][] = [];
  message:any;
  @Output() result: EventEmitter<any> = new EventEmitter();
  public id = 0;
  business:string = 'act-comprobante'
  fileToUpload: File | null = null;

  barWidth: number = 0;
  fileSizeUnit: number = 1024;
  fileSize: any;
  fileProgessSize: any;
  uploadedMedia: Array<any> = [];
  loadingFile: boolean;
  file:any;
  selectDefaultCnfLocal: any = { id: 0, nombre: "- Seleccione -" }; listCnfLocal: any;
  cnfLocal: CnfLocal = new CnfLocal();
  loadingCnfLocal: boolean = false;
  listResult: any[];
  messageResult:string;
  constructor(public activeModal: NgbActiveModal,private commonService: CommonService,
    private mediaService: MediaService,private actContratoService:ActContratoService, private utilService: UtilService,
    private cnfLocalService: CnfLocalService, private appService: AppService) {
    this.commonService.baseEndpoint = environment.apiUrl;
  }
  ngOnInit(): void {
    this.load = false;
    this.loadData();
    // this.listDetail.forEach((element:any) => {
    //   let arr:any = [];  
    //   Object.keys(element).map(function(key){  
    //       arr.push({[key]:element[key]});
    //       console.log(element);
    //   });
    //   this.listDetail2.push(arr);
    // });
    // for( var i=0; i<this.listDetail.length; i++ ) {
    //   this.listDetail2.push( [] );
    // }
    
    // for (let index = 0; index < this.listDetail.length; index++) {
    //   const element = this.listDetail[index];
    //   let arr:any = [];  
    //   Object.keys(element).map(function(key){  
    //       arr.push(element[key]);
    //       console.log(element);
    //   });
    //   this.listDetail2[index].push(arr);
      
    // }
    this.load = true;
    console.log(this.listDetail2);
  }

  async loadData() {
    await this.getListCnfLocal();
  }
  save(){
    this.result.emit(this.listDetail);
    this.activeModal.close();
  }
  ticketChild(){
    let myMap = new Map();
    myMap.set("id", this.id);
    myMap.set("tipo", 2);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);
    mp.map = convMapDetail;
    this.commonService.updateParam = mp;
    
    this.commonService.genericPostRequest("/api/business/"+this.business+"/getpdflocal", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        this.generateAttachment(blob, extension);
      }

    });
    // console.log("enviando a imprimir: ",this.properties.listData);
  }
  a4() {
    let myMap = new Map();
    myMap.set("id", this.id);
    myMap.set("tipo", 1);
    let mp = new UpdateParam();
    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);

    mp.map = convMapDetail;
    this.commonService.genericPostRequest("/api/business/"+this.business+"/getpdflocal", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        this.generateAttachment(blob, extension);
      }

    });
  }

  getListCnfLocal() {
    this.loadingCnfLocal = true;
    let user = this.appService.getProfile();
    console.log(user);
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    if (cnfEmpresa == '*') {
      return this.cnfLocalService.getAllDataCombo().subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;
      })
    } else {
      return this.cnfLocalService.getAllByCnfEmpresaId(cnfEmpresa).subscribe(data => {
        this.listCnfLocal = data;
        this.loadingCnfLocal = false;


        if (this.listCnfLocal.length == 1) {
          this.cnfLocalService.getData(this.listCnfLocal[0].id).subscribe(data => {
            this.cnfLocal = data
            // this.getListInvAlmacen()

          })
          // this.model.cnfLocal.id = this.listCnfLocal[0].id;
          // if (this.listInvAlmacen.length == 1){
          //   this.model.invAlmacen.id = this.listInvAlmacen[0].id;
          // }
        }
      })
    }


  }

  compareCnfLocal(a1: CnfLocal, a2: CnfLocal): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }

  generateAttachment(blob: Blob, extension: string) {
    const data = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = data;
    link.download = "report." + extension;
    link.dispatchEvent(new MouseEvent('click', {
      bubbles: true, cancelable: true, view: window
    }));
    setTimeout(() => {
      window.URL.revokeObjectURL(data);
      link.remove
    }, 100);
  }

  handleFileInput($event: any) {
    const target = $event.target as HTMLInputElement;
    this.fileToUpload = $event.target.files.item(0);
    console.log(this.fileToUpload);
    // var tmppath = URL.createObjectURL($event.target.files.item(0));
    // console.log(tmppath);
    if (this.fileToUpload) {
      //console.log(this.fileToUpload.name);
      this.file = this.fileToUpload;

      let fileSize0 = this.mediaService.getFileSize(this.fileToUpload.size);
      this.fileSize = this.mediaService.getFileSize(this.fileToUpload.size) +
        ' ' +
        this.mediaService.getFileSizeUnit(this.fileToUpload.size);
      let fileSizeInWords = this.mediaService.getFileSizeUnit(this.fileToUpload.size);
      this.actContratoService.getVideoPathFromResources(this.fileToUpload.name,this.fileToUpload.size.toString()).subscribe(pathToServedFile => {
        setTimeout(() => {
          
        }, 1000);
        this.actContratoService.import(this.cnfLocal,this.fileToUpload)
          .subscribe(data => {
            this.utilService.msgOkOperation();
            this.listResult = data;
            this.messageResult = "Se subieron corectamente " + this.listResult.length + " registros";
          }, err => {
            console.log(err);
            
            if (err.status === 412) {
              console.log("waaw");
              
              this.listDetail = err.error;
            }
          });
          
          
        
      })
    }
  }
}

