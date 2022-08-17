import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActComprobante } from '../act-comprobante.model';
import { ActComprobanteDetalle } from '../../act-comprobante-detalle/act-comprobante-detalle.model';
import { ActComprobanteDetalleService } from '../../act-comprobante-detalle/act-comprobante-detalle.service';
import {Location} from '@angular/common';
import Swal from 'sweetalert2';
import { UtilService } from 'src/app/services/util/util.service';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CnfProducto } from '../../cnf-producto/cnf-producto.model';
import { CnfProductoService } from '../../cnf-producto/cnf-producto.service';
@Component({
  selector: 'app-add-new-act-comprobante-detalle',
  templateUrl: './add-new-act-comprobante-detalle.component.html'
,
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class AddNewActComprobanteDetalleComponent implements OnInit {
  public selectedOptionActComprobanteDetalle:any;
  public data!: Array<any>;
  id: string = "";
  error: any;
  model:ActComprobanteDetalle = new ActComprobanteDetalle();
  chargingsb:boolean = true;
  isDataLoaded :Boolean = false;
  actComprobante:ActComprobante = new ActComprobante();
  listActComprobanteDetalle:ActComprobanteDetalle[] = [];
  selectDefaultCnfProducto: any = { id: 0, name: "- Seleccione -" };   cnfProducto: CnfProducto = new CnfProducto();
  loadingCnfProducto: boolean = false;
  listCnfProducto:any;
  constructor(private actComprobanteDetalleService : ActComprobanteDetalleService,private router: Router,private _location: Location,private route: ActivatedRoute,private cnfProductoService: CnfProductoService,private utilService:UtilService ) {}
  
  ngOnInit(): void {
    this.loadData();
  }
  loadData(){
    this.getListCnfProducto();
    return this.route.paramMap.subscribe(params => {
      this.model.actComprobante.id = Number(params.get('idParent'));
      this.id = params.get('id')!;
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actComprobanteDetalleService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
        });
      }
      
    })
  }
  back(){
    this._location.back();
  }
  public save(): void {
    this.actComprobanteDetalleService.save(this.model).subscribe(m => {
      console.log(m);
      this.utilService.msgOkSave();
      this._location.back();
    }, err => {
      if(err.status === 400){
        this.error = err.error;
        console.log(this.error);
      }
    });
  }
getListCnfProducto() {
    this.loadingCnfProducto = true;
    console.log(this.chargingsb);
    return this.cnfProductoService.getAllDataCombo().subscribe(data => {
      this.listCnfProducto = data;
      this.loadingCnfProducto = false;
    })

  }
  compareCnfProducto(a1: CnfProducto, a2: CnfProducto): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  
}

