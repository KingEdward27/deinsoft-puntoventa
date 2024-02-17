import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-tipo-sistema-form',
  template: '<app-generic-form2 [props] = prop [onChanges] = "changes" [route] = "this.prop.route" [onPreLoadForm]="wa" [onPreSave]="onPreSave"></app-generic-form2>'
})

export class CnfTipoSistemaFormComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_tipo_sistema",
    "title": "cnf_tipo_sistema",
    "columnsForm":[{tableName: "cnf_tipo_sistema", columnName:"nombre",type:"input"}
           ],
    "childTables":[
            {tableName: "seg_menu",tableNameDetail: "cnf_menu_tipo_sistema",
              idValue:"seg_menu_id"
              ,columnsForm: [
                              {tableName:"seg_menu", columnName:"nombre",type:"select"}
                            ]
            }
],
    //filters sería para filtros adicionales
    "conditions":[],
    "preSave" : [],
    "functions": [],
    "route": "/tipo-sistema"
  }

  changes: any;
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    public routers: Router,public _commonService:CommonService,private appService:AppService) { 
    //super(utilServices,httpClients,routers,_commonService);
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
    
    return true;
  }
  onChangeTipoDoc = () => {
    
    return true;
  }
  ngOnInit(): void {
    this.prop.functions.push({func : this.wa});
    this.changes = []
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

