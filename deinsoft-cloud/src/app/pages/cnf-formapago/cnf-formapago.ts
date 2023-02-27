import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-forma-pago',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfFormaPagoComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_forma_pago",
    "title": "Formas de Pago",
    "columnsList":[
              {tableName: "cnf_forma_pago", columnName:"nombre",filterType:"text"}
                ],
    childTables:[
                  {tableName: "cnf_forma_pago_detalle",tableNameDetail: "cnf_forma_pago_detalle",
                    idValue:"cnf_forma_pago_id"
                    ,columnsForm: [
                                    {tableName:"cnf_forma_pago_detalle", columnName:"modo_dias_intervalo",type:"number"},
                                    {tableName:"cnf_forma_pago_detalle", columnName:"modo_porcentaje",type:"number"},
                                    {tableName:"cnf_forma_pago_detalle", columnName:"modo_monto",type:"number"},
                                    {tableName:"cnf_forma_pago_detalle", columnName:"modo_dia_vencimiento",type:"number"}
                                  ]
                  }
    ],
    "columnsForm":[{tableName: "cnf_forma_pago", "columnName":"nombre","type":"input"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "preSave":[],
    "orders":["nombre"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    private routers: Router,public _commonService:CommonService,private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    // this.prop.columnsForm[1].listData.push([0, "- Seleccione -"]);
    // this.prop.columnsForm[1].listData.push([1, "Hasta el monto"]);
    // this.prop.columnsForm[1].listData.push([2, "Valor fijo repetitivo"]); 
    this.prop.conditions.push({"columnName":"cnf_forma_pago.cnf_empresa_id","value":cnfEmpresa});
    this.prop.preSave.push({columnForm:"cnf_empresa_id",value:cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    
  }
}

