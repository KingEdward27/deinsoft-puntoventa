import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-tipo-sistema-list',
  template: '<app-generic-list3 [route] = "this.prop.route" [newButton] = "gotoForm" [editButton] = "editButton" [props] = prop [onPreLoadForm]="wa" [onPreSave]="onPreSave"></app-generic-list3>'
})

export class CnfTipoSistemaListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_tipo_sistema",
    "title": "cnf_tipo_sistema",
    "columnsList":[{tableName: "cnf_tipo_sistema",columnName:"nombre",filterType:"text"}
                  ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["nombre"],
    "preSave" : [],
    "functions": [],
    "route": "/tipo-sistema"
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    public routers: Router,public _commonService:CommonService,private appService:AppService) { 
    //super(utilServices,httpClients,routers,_commonService);
  }
  gotoForm = () => {
    
    this.routers.navigate(["/new-tipo-sistema"]);
  }
  editButton= () => {
    
  }
  wa  = (): boolean => {
    console.log("XD");
    this.otroLog();
    return true;
    
  }
  w2 () {
    console.log("w2");
    this.otroLog();
    return true;
    
  }
  onPreSave = (): boolean => {
    console.log("XDDDDD");
    return false;
  }
  ngOnInit(): void {
    this.prop.functions.push({func : this.wa});
    
    console.log(this.prop);
    //super.ngOnInit();
  }
  otroLog () {
    
    console.log("ohhh");
  }
  // async save(){
    
  //   let res = await super.preSave();
  //   
  // }
}

