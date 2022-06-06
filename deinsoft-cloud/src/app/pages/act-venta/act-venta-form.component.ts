import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { NgbDateAdapter, NgbDateParserFormatter, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { GenericMasterDetailFormComponent } from '@/base/components/generic-master-detail-form/generic-master-detail-form.component';
import { UtilService } from '@services/util.service';
import { HttpClient } from '@angular/common/http';
import { CommonService } from '@/base/services/common.service';
import { UpdateParam } from '@/base/components/model/UpdateParam';


@Component({
  selector: 'act-venta-form',
  templateUrl: '../../base/components/generic-master-detail-form/generic-master-detail-form.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]

})
export class ActVentaFormComponent extends GenericMasterDetailFormComponent implements OnInit {
  ticket = "";
  prop = {
    "tableName": "act_comprobante",
    "title": "Venta",
    "api": "save-sale",
    "columnsList": [{ tableName: "act_comprobante", columnName: "fecha", filterType: "text" },
                    { tableName: "cnf_local", columnName: "nombre", filterType: "text"  },
                    { tableName: "cnf_tipo_comprobante", columnName: "nombre", filterType: "none" },
                    { tableName: "act_comprobante", columnName: "numero", filterType: "text",disabled:"disabled" },
                    { tableName: "act_comprobante", columnName: "total", filterType: "text" },
                    { tableName: "act_comprobante", columnName: "observacion", filterType: "text" },
                    { tableName: "cnf_maestro", columnName: "nombres", filterType: "text" },
                    { tableName: "cnf_forma_pago", columnName: "nombre", filterType: "none" },
                    { tableName: "cnf_moneda", columnName: "codigo", filterType: "none" },
                    { tableName: "inv_almacen", columnName: "nombre", filterType: "none" }
    ],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables": [{ "tableName": "cnf_tipo_comprobante", "idValue": "cnf_tipo_comprobante_id" },
                      { "tableName": "cnf_maestro", "idValue": "cnf_maestro_id" },
                      { "tableName": "cnf_forma_pago", "idValue": "cnf_forma_pago_id" },
                      { "tableName": "cnf_moneda", "idValue": "cnf_moneda_id" },
                      { "tableName": "cnf_local", "idValue": "cnf_local_id" },
                      { "tableName": "inv_almacen", "idValue": "inv_almacen_id" }],
    "columnsForm": [{ tableName: "cnf_local", "columnName": "nombre", "type": "select", loadState: 1, relatedBy: "cnf_local_id",loadFor:"inv_almacen_id",
                      load:{tableName:"inv_almacen",columnName:"nombre",loadBy:"cnf_local_id",id:"cnf_local_id"},value:1 },
                    { tableName: "act_comprobante", columnName: "fecha", type: "date" },
                    { tableName: "cnf_tipo_comprobante", "columnName": "nombre", "type": "select", loadState: 1, relatedBy: "cnf_tipo_comprobante_id",loadFor:"serie",load:{tableName:"cnf_num_comprobante",columnName:"serie",loadBy:"cnf_tipo_comprobante_id,cnf_local_id",id:"cnf_tipo_comprobante_id",getValue:"1"} },
                    { tableName: "act_comprobante", "columnName": "serie", "type": "input" , disabled:"disabled"},
                    { tableName: "act_comprobante", columnName: "numero", type: "input" , disabled:"disabled"},
                    { tableName: "cnf_maestro", "columnName": "concat(nombres,' ',apellido_paterno,' ',apellido_materno)", "type": "select", loadState: 1, relatedBy: "cnf_maestro_id" },
                    { tableName: "cnf_forma_pago", "columnName": "nombre", "type": "select", loadState: 1, relatedBy: "cnf_forma_pago_id",value:1 },
                    { tableName: "act_comprobante", "columnName": "igv", "type": "label", value: "0.00", "subtype": "number",onchange:"total" },
                    { tableName: "act_comprobante", "columnName": "subtotal", "type": "label", value: "0.00", "subtype": "number" },
                    { tableName: "act_comprobante", "columnName": "total", "type": "label", value: "0.00", "subtype": "number" },
                    { tableName: "cnf_moneda", "columnName": "nombre", "type": "select", loadState: 1, relatedBy: "cnf_moneda_id" ,value:1},
                    { tableName: "act_comprobante", "columnName": "observacion", "type": "input-large" },
                    { tableName: "inv_almacen", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_local",relatedBy:"inv_almacen_id",value:1}

    ],
    "details": [
      {
        tableName: "act_comprobante_detalle", "relatedBy": "act_comprobante_id",
        search: {
          tableName: "cnf_producto", columnName: "cnf_producto.cnf_producto_id, cnf_producto.nombre",
           "type": "select", loadState: 1,where:"cnf_producto.nombre like concat('%',[value],'%')",
          relatedBy: "cnf_producto_id",
          onchange:{
              select: {
                  columns:"cnf_producto.cnf_producto_id,cnf_producto.nombre,'' tax,'' descripcion,'1' cantidad,cnf_producto.precio,cnf_producto.precio,'' actions",
                  from: "cnf_producto",
                  where:  "cnf_producto_id = get(0)" 
              }
          },
          "action": {}
        },
        columnsList: [
          { tableName: "cnf_producto", columnName: "cnf_producto_id", type: "hidden" },
          { tableName: "cnf_producto", columnName: "nombre", type: "none" },
          { tableName: "cnf_impuesto_condicion", "columnName": "nombre", "type": "select", loadState: 1, relatedBy: "cnf_impuesto_condicion_id" },
          { tableName: "act_comprobante_detalle", columnName: "descripcion", type: "input" },
          { tableName: "act_comprobante_detalle", columnName: "cantidad", type: "input", subtype: "number" },
          { tableName: "act_comprobante_detalle", columnName: "precio", type: "input", subtype: "number" },
          { tableName: "act_comprobante_detalle", columnName: "afectacion_igv", type: "input", disabled:"disabled"}
        ],
        "actions": []
      }
    ],
    //filters sería para filtros adicionales
    "filters": { "act_comprobante.nombre": "", "act_comprobante.direccion": "" },
    "orders": ["nombre", "direccion"],
    "preSave" : [
                {columnForm:"numero",select: {
                      columns:"ultimo_nro",
                      from: "cnf_num_comprobante",
                      where:[{"cnf_tipo_comprobante_id":"cnf_tipo_comprobante.cnf_tipo_comprobante_id"},
                             {"cnf_local_id":"cnf_local.cnf_local_id"}]
                      //where:  "cnf_num_comprobante_id = columnsForm->cnf_num_comprobante.cnf_num_comprobante_id" 
                  }
                },
                {columnForm:"flag_estado",value:"1"},
                {columnForm:"flag_isventa",value:"1"}
              ],
    "postSave" : {message:{
                    title : {message :"Documento get(0) - get(1) generado correctamente",
                            params:"columnsForm->act_comprobante.serie,columnsForm->act_comprobante.numero"},
                    icon :"success",
                    rows:[
                        {columns:[{type:"a",id:"ticket" ,value:"Ticket",icon:"fa fa-arrow-circle-left",action:""},
                                  {type:"a",id:"a4" ,value:"A4",icon:"fa fa-arrow-circle-left",action:""}]}
                              ]
                    },
                    update:{columns:"ultimo_nro = ultimo_nro + 1",tableName:"cnf_num_comprobante",
                    where:[{"cnf_tipo_comprobante_id":"cnf_tipo_comprobante.cnf_tipo_comprobante_id"},
                             {"cnf_local_id":"cnf_local.cnf_local_id"}]},
                    api:{url:"/business/sendapi",params:[
                                                  ["tableName","act_comprobante"],
                                                  ["id","this.id"]
                                                ]
                        }
                  },
    "actions": [],

  }
  igv: number = 0;
  subtotal: number = 0;
  total: number = 0;
  constructor(private utilServices: UtilService, private httpClientChild: HttpClient, private routers: Router,
    private _location0: Location,public _commonService:CommonService) {
    super(utilServices, httpClientChild, routers, _location0,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    super.properties = this.prop;

    this.properties.details[0].search.postFunction = { name: this.updateTotals, param: 0, icon: "fas fa-print2" }
    this.properties.details[0].columnsList[5].onChange = { name: this.updateTotals, param: 0, icon: "fas fa-print2" }
    this.properties.details[0].columnsList[4].onChange = { name: this.updateTotals, param: 0, icon: "fas fa-print2" }
    this.properties.details[0].actions.push({ name: this.removeItems, icon: "fas fa-trash" })
    this.properties.postSave.message.rows[0].columns[0].action = { name: this.ticketChild }
    this.properties.postSave.message.rows[0].columns[1].action = { name: this.a4 }
    // this.properties.actions.push({ name: this.print, param: 0, icon: "fas fa-print" })
    localStorage.setItem("properties", JSON.stringify(this.properties));
    super.ngOnInit();
    console.log(this._commonService);
    
    // this.updateValue();
  }

  loadData() {

  }
  // preSave(){

  // }
  public async save(){
    await super.preSave();

    this.properties.postSaveTrans = []
    this.properties.details.forEach((element:any) => {
      
    });
    this.properties.postSaveTrans.push({type:"update"});
    let result = await super.save();
    console.log(result);
  //   for(let key of Array.from( result.entries()) ) {
  //     console.log(key);
  //  }
  //   let we: string[] = Array.from( result.entries());
  //   console.log(we[1]);
  //   this.properties.id = result[0];
  //   result.forEach((value: boolean, key: string) => {
  //       console.log(key, value);
        
  //   });
    if(result){
      let res :any;
      res = await super.postSave();
      console.log(res);
      console.log(this.properties);
      console.log(super.properties);
      
      
      this.ticket = res.id;
      this.properties = this.prop;
    }
    
  }
  // updateValue() {
  //   super.updateValue("act_comprobante", "igv", 20);
  //   super.updateValue("act_comprobante", "subtotal", 20);
  //   super.updateValue("act_comprobante", "total", 20);
  // }
  print(prope:any, param:CommonService,parent:GenericMasterDetailFormComponent) {
    console.log("imprimiendo desde cnf-org...", param);
    parent.properties = this.prop;
    
    // console.log("enviando a imprimir: ",this.properties.listData);
  }
  ticketChild (prope:any, param:CommonService,parent:GenericMasterDetailFormComponent){
      //this.ticket();
      // console.log("ticket...", param);
        let myMap = new Map();
        // let ticketOperacion = JSON.parse(localStorage.getItem("ticketOperacion") || '0');
        // if(ticketOperacion != prope.ticketOperacion) return;
        myMap.set("id", prope.id);
        myMap.set("tipo",2);
        let mp = new UpdateParam();
        

        const convMapDetail: any = {};
        myMap.forEach((val: string, key: string) => {
          convMapDetail[key] = val;
        });
        console.log(convMapDetail);
        
        mp.map = convMapDetail;
        param.updateParam = mp;
        //this.properties.actions.push({ name: this.print, param: 0, icon: "fas fa-print" })
        param.genericPostRequest("/business/getpdflocal",mp,'blob').subscribe(data => {
          console.log(data);
          if(data.type != 'application/json'){
            var contentType ='application/pdf';
            var extension = "pdf";
    
            if(data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
              contentType = data.type;
              extension = "xlsx";
            }
            const blob = new Blob([data],{type: contentType});
            super.generateAttachment(blob,extension);
          }
          
        });
        // console.log("enviando a imprimir: ",this.properties.listData);
  }
  a4(prope:any, param: CommonService,parent:GenericMasterDetailFormComponent) {
    let myMap = new Map();
        myMap.set("id", prope.id);
        myMap.set("tipo",1);
        let mp = new UpdateParam();
        

        const convMapDetail: any = {};
        myMap.forEach((val: string, key: string) => {
          convMapDetail[key] = val;
        });
        console.log(convMapDetail);
        
        mp.map = convMapDetail;
        //this.properties.actions.push({ name: this.print, param: 0, icon: "fas fa-print" })
        param.genericPostRequest("/business/getpdflocal",mp,'blob').subscribe(data => {
          console.log(data);
          if(data.type != 'application/json'){
            var contentType ='application/pdf';
            var extension = "pdf";
    
            if(data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
              contentType = data.type;
              extension = "xlsx";
            }
            const blob = new Blob([data],{type: contentType});
            super.generateAttachment(blob,extension);
          }
          
        });
  }
  updateTotals = function (properties: any) {
    let total = 0;
    properties.details[0].listData.forEach((element:any) => {
      let importe = Number(element[4]) * Number(element[5]);
      total = total + Math.round((importe + Number.EPSILON) * 100) / 100;
      element[6] = Math.round((importe - (importe / 1.18)+ Number.EPSILON) * 100) / 100;
    });
    let subtotal = Math.round(((total / 1.18) + Number.EPSILON) * 100) / 100;
    let igv = Math.round((total - subtotal + Number.EPSILON) * 100) / 100;
    properties.columnsForm.forEach((element: any) => {
      if (element.columnName == "igv") {
        element.value = igv;
      }
      if (element.columnName == "subtotal") {
        element.value = subtotal;
      }
      if (element.columnName == "total") {
        element.value = total;
      }
    });
    // console.log("enviando a imprimir: ",this.properties.listData);
  }
  removeItems(properties: any, index: any) {
    // console.log(properties.details[0].listData);

    // properties.columnsForm.forEach((element: any) => {
    //   if (element.columnName == "total") {
    //     element.value = Number(element.value) - Number(properties.details[0].listData[index][5] ?
    //       properties.details[0].listData[index][5] : "0");
    //   }
    // });
    console.log(properties);
    
    properties.details[0].listData.splice(index, 1);
    let total = 0;
    properties.details[0].listData.forEach((element:any) => {
      total = total + Math.round((Number(element[4]) * Number(element[5]) + Number.EPSILON) * 100) / 100;
    });
    let subtotal = Math.round(((total / 1.18) + Number.EPSILON) * 100) / 100;
    let igv = Math.round((total - subtotal + Number.EPSILON) * 100) / 100;
    properties.columnsForm.forEach((element: any) => {
      if (element.columnName == "igv") {
        element.value = igv;
      }
      if (element.columnName == "subtotal") {
        element.value = subtotal;
      }
      if (element.columnName == "total") {
        element.value = total;
      }
    });
  }
  getProductDetail(properties: any, id: any) {
    let forSearch = {
      select: "cnf_producto.cnf_producto_id,cnf_producto.nombre,'','',cnf_producto.precio,''",
      from: "cnf_producto",
      where: { "cnf_producto_id": id }
    };
    properties.details[0].listData.onchange = forSearch;
    properties.details.forEach((element: any) => {

    });
    // let list = await super.select(forSearch.from, forSearch.select);
    //   console.log(list);
    //   properties.columnsForm.forEach((element: any) => {
    //     if (element.columnName == "total") {
    //       element.value = Number(element.value) + Number(list[5]);
    //     }
    //   });
    //   properties.details[0].listData.push(list);

    // let list = [1, "Producto 1", "", "", "1", "2000.00", ""];
    // console.log(properties);
    // properties.columnsForm.forEach((element: any) => {
    //   if (element.columnName == "total") {
    //     element.value = Number(element.value) + Number(list[5]);
    //   }
    // });
    // properties.details[0].listData.push(list);



    // this.updateTotals(2000.00);
    // super.updateValue("act_comprobante", "igv", 20);
    // this.properties.columnsForm.forEach((element: any) => {
    //   if (element.tableName == "act_comprobante" && element.columnName == "total") {
    //     element.value = 1000;
    //   }
    // });
    // return result
    // console.log("enviando a imprimir: ",this.properties.listData);
  }
  searchProduct = function (param: any) {

  }
}
