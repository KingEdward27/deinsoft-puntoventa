import { AfterViewInit, Component, EventEmitter, HostListener, Input, OnInit, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { HttpClient } from '@angular/common/http';
import { CommonService } from '../../services/common.service';
import { CustomAdapter, CustomDateParserFormatter } from '../../util/CustomDate';
import { UtilService } from '@services/util.service';
import { environment } from 'environments/environment';



@Component({
  selector: 'app-generic-report',
  templateUrl: './generic-report.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]
})
export class GenericReportComponent extends CommonService implements OnInit {

  //generic variables

  modelSearch: any;
  error: any;
  headers: any;
  listDetail: any;
  load: boolean = false;
  listDetail2: any[][] = [];
  dataTable!: DataTables.Api;
  @Output() result: EventEmitter<any> = new EventEmitter();
  constructor(private utilService: UtilService, private httpClient: HttpClient, private router: Router) {
    super(httpClient);
  }
  ngOnInit(): void {
    this.load = false;
    this.headers = this.properties.headers;
    //aqui cargar metadata, nullable, tamaño, etc
    this.properties.columnsForm.forEach((element: any) => {
      element.isNull = false;
    });
    this.getListData();
  }
  getListData() {

    this.baseEndpoint = environment.apiUrl;

    console.log(this.properties);
    super.getList(this.properties)
      .subscribe(data => {
        this.listDetail = data;
        console.log(this.headers);
        console.log(this.listDetail);
        setTimeout(() => {
          this.dataTable = $('#dtData'+this.properties.tableName).DataTable(this.utilService.datablesSettings);
        }, 1);
        // console.log(data);
      });
    if (this.dataTable != undefined) {
      this.dataTable?.destroy();
    }
  }
  goToForm() {
    this.properties.id = 0;
    localStorage.setItem("properties", JSON.stringify(this.properties));
    this.router.navigate(["/generic-form"]);
  }
  goToFormToEdit(id: any) {
    this.properties.id = id;
    
    
    super.getData(this.properties).subscribe(data => {
      console.log(data);
      let index = 0;
      // data.prototype.forEach((value: boolean, key: string) => {
      //     console.log(key, value);
      // });
      // var resultArray = Object.keys({}).map(function(personNamedIndex:any){
      //     let person = data[personNamedIndex];
      //     // do something with person
      //     console.log(person);
          
      // });
      let objects = [];
      for (let key in data) {
        // const convMap: any = {};
        // let key2 = key.replace("__",".");
        // convMap[key2] = data[key];
        // objects.push(convMap);
        const convMap: any = [];
        let key2 = key.replace("__",".");
        convMap[0] = key2;
        convMap[1] = data[key];
        objects.push(convMap);
      }
      console.log(objects);
      for (const element of objects) {
        let tablaAndColumndArr = element[0].split(".");
        for (const element2 of this.properties.columnsForm) {
          let tableAndColumn = element2.tableName + "." +element2.columnName;
          if((element[0] == tableAndColumn && (element2.type == 'input' || element2.type == 'date'))){
            element2.value = element[1];
            console.log(element[1]);
            break;
          }
          if(tablaAndColumndArr[0] == element2.tableName && element2.type == 'select' &&  
          (element2.relatedBy == tablaAndColumndArr[1] || element2.load?.loadBy == tablaAndColumndArr[1])){
            element2.value = element[1];
            break;
          }
        }
      }
      localStorage.setItem("properties", JSON.stringify(this.properties));
      console.log(this.properties);
      this.router.navigate(["/generic-form"]);
    });
    
  }
  delete(id: any) {
    this.utilService.confirmDelete(id).then((result) => { 
      if(result){
        this.remove(this.properties.tableName, id).subscribe(() => {
          this.utilService.msgOkDelete();
          this.getListData();
        });
      }
      
    });
  }
  getListWithParams() {
    // let filters:any[] = [];
    let myMap = new Map();
    this.properties.columnsList.forEach((element: any) => {
      console.log(element)
      if (element.filterType != 'none') {
        let inputFilter = (<HTMLInputElement>document.getElementById(element.tableName + '.' + element.columnName)).value;
        if (inputFilter) {
          myMap.set(element.tableName + '.' + element.columnName, inputFilter);
        }
      }

    });
    const convMap: any = {};
    myMap.forEach((val: string, key: string) => {
      convMap[key] = val;
    });
    console.log(convMap);
    this.properties.filters = convMap;
    console.log(this.properties);

    super.getListWithFilters(this.properties).subscribe(data => {
      this.listDetail = data;
      console.log(this.listDetail);
      setTimeout(() => {
        this.dataTable = $('#dtData').DataTable(this.utilService.datablesSettings);
      }, 1);
      // console.log(data);
    });
    if (this.dataTable != undefined) {
      this.dataTable?.destroy();
    }
  }
}

