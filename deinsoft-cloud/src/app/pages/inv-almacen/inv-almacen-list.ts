import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-cnf-empresa',
  template: '<app-generic-list3 [route] = "this.prop.route" [newButton] = "gotoForm" [props] = prop ></app-generic-list3>'
})

export class InvAlmacenComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "inv_almacen",
    "title": "Almacenes",
    "columnsList":[{tableName: "inv_almacen", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_local",columnName:"nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_local","idValue":"cnf_local_id"}],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["inv_almacen.inv_almacen_id"] ,
    "route": "/almacen"
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    private routers: Router,public _commonService:CommonService,private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    // super.baseEndpoint = this.baseEndpoint;
    let cnfEmpresa =  this.appService.getProfile().profile.split("|")[1];
    this.prop.conditions.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    // this.prop.columnsForm[1].filters.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    // super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    
  }
  gotoForm = () => {
    
    this.routers.navigate(["/new-almacen"]);
  }
}

