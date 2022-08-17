import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-seg-usuario',
  templateUrl: '../../../base/components/generic-list/generic-list.component.html'
})

export class SegUsuarioComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "seg_usuario",
    "title": "Usuarios",
    "api":"/api/framework/save-user",
    "columnsList":[{tableName: "seg_usuario", columnName:"nombre",filterType:"text"},
                   {tableName: "seg_usuario", columnName:"email",filterType:"text"}
                ],
    childTables:[
                  {tableName: "seg_rol",tableNameDetail: "seg_rol_usuario",
                    idValue:"seg_rol_id"
                    ,columnsForm: [
                                    {tableName:"seg_rol", columnName:"nombre",type:"select",loadState:1,relatedBy:"seg_rol_id"},
                                    {tableName:"cnf_empresa", columnName:"nombre",type:"select",loadState:1,relatedBy:"cnf_empresa_id"},
                                    {tableName:"cnf_local", columnName:"nombre",type:"select",loadState:1,relatedBy:"cnf_local_id"}
                                  ]
                  }
    ],
    "columnsForm":[{tableName: "seg_usuario", "columnName":"nombre","type":"input"},
                   {tableName: "seg_usuario", columnName:"email",type:"input"},
                   {tableName: "seg_usuario", columnName:"password",type:"password"}
           ],
    "preSave" : [
            {columnForm:"estado",value:"1"}
          ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["nombre"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,private routers: Router,public _commonService:CommonService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    console.log("test desde cnf-org");
  }
}

