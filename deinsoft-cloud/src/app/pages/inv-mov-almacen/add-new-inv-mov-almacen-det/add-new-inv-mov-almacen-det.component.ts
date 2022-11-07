import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {Location} from '@angular/common';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { InvMovAlmacen } from '@/business/model/inv-mov-almacen.model';
import { InvMovAlmacenDet } from '@/business/model/inv-mov-almacen-det.model';
import { CnfProducto } from '@/business/model/cnf-producto.model';
import { InvMovAlmacenDetService } from '@/business/service/inv-mov-almacen-det.service';
import { CnfProductoService } from '@/business/service/cnf-producto.service';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-add-new-inv-mov-almacen-det',
  templateUrl: './add-new-inv-mov-almacen-det.component.html'
,
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class AddNewInvMovAlmacenDetComponent implements OnInit {
  public selectedOptionInvMovAlmacenDet:any;
  public data!: Array<any>;
  id: string = "";
  error: any;
  model:InvMovAlmacenDet = new InvMovAlmacenDet();
  chargingsb:boolean = true;
  isDataLoaded :Boolean = false;
  invMovAlmacen:InvMovAlmacen = new InvMovAlmacen();
  listInvMovAlmacenDet:InvMovAlmacenDet[] = [];
  selectDefaultCnfProducto: any = { id: 0, name: "- Seleccione -" };   
  cnfProducto: CnfProducto = new CnfProducto();
  loadingCnfProducto: boolean = false;
  listCnfProducto:any;
  constructor(private invMovAlmacenDetService : InvMovAlmacenDetService,
    private router: Router,private _location: Location,
    private route: ActivatedRoute,
    private cnfProductoService: CnfProductoService,private utilService:UtilService ) {}
  
  ngOnInit(): void {
    this.loadData();
  }
  loadData(){
    this.getListCnfProducto();
    return this.route.paramMap.subscribe(params => {
      this.model.invMovAlmacen.id = Number(params.get('idParent'));
      this.id = params.get('id')!;
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.invMovAlmacenDetService.getData(this.id).subscribe(data => {
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
    this.invMovAlmacenDetService.save(this.model).subscribe(m => {
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

