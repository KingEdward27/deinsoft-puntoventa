import { Component, HostListener, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfProducto } from '../cnf-producto.model';
import { CnfProductoService } from '../cnf-producto.service';
import Swal from 'sweetalert2';
import { CnfUnidadMedida } from '../../../business/model/cnf-unidad-medida.model';
import { CnfEmpresa } from '../../../business/model/cnf-empresa.model';
import { CnfSubCategoria } from '../../../business/model/cnf-sub-categoria.model';
import { CnfMarca } from '../../../business/model/cnf-marca.model';
import { CnfUnidadMedidaService } from '../../../business/service/cnf-unidad-medida.service';
import { CnfEmpresaService } from '../../../business/service/cnf-empresa.service';
import { UtilService } from '../../../services/util.service';
import { CnfSubCategoriaService } from '../../../business/service/cnf-sub-categoria.service';
import { CnfMarcaService } from '../../../business/service/cnf-marca.service';
import { AppService } from '../../../services/app.service';
import { filter } from 'rxjs/operators';


@Component({
  selector: 'app-cnf-producto-form',
  templateUrl: './cnf-producto-form.component.html'
})
export class CnfProductoFormComponent implements OnInit {

  //generic variables
  error: any;
  selectedItemsList = [];
  checkedIDs = [];
  chargingsb: boolean = true;
  isDataLoaded: Boolean = false;
  isOk: boolean = false;
  isWarning: boolean = false;
  isDanger: boolean = false;
  message: any = "";
  id: string = "";

  //variables propias
  public model: CnfProducto = new CnfProducto();
  selectDefaultCnfUnidadMedida: any = { id: 0, nombre: "- Seleccione -" }; listCnfUnidadMedida: any;
  cnfUnidadMedida: CnfUnidadMedida = new CnfUnidadMedida();
  loadingCnfUnidadMedida: boolean = false;
  selectDefaultCnfEmpresa: any = { id: 0, nombre: "- Seleccione -" }; listCnfEmpresa: any;
  cnfEmpresa: CnfEmpresa = new CnfEmpresa();
  loadingCnfEmpresa: boolean = false;
  selectDefaultCnfSubCategoria: any = { id: 0, nombre: "- Seleccione -" }; listCnfSubCategoria: any;
  cnfSubCategoria: CnfSubCategoria = new CnfSubCategoria();
  loadingCnfSubCategoria: boolean = false;
  selectDefaultCnfMarca: any = { id: 0, nombre: "- Seleccione -" }; listCnfMarca: any;
  cnfMarca: CnfMarca = new CnfMarca();
  loadingCnfMarca: boolean = false;
  protected redirect: string = "/cnf-producto";
  selectedOption: any;
  passwordRepeat: any;

  constructor(private cnfProductoService: CnfProductoService,
    private router: Router,
    private utilService: UtilService,
    private cnfUnidadMedidaService: CnfUnidadMedidaService,
    private cnfEmpresaService: CnfEmpresaService,
    private cnfSubCategoriaService: CnfSubCategoriaService,
    private cnfMarcaService: CnfMarcaService,
    private route: ActivatedRoute,private appService:AppService) {
  }
  ngOnInit(): void {
    this.isDataLoaded = false;
    this.loadData();
  }
  getBack() {
    this.router.navigate([this.redirect]);
  }
  loadData() {
    this.getListCnfUnidadMedida();
    this.getListCnfEmpresa();
    this.getListCnfSubCategoria();
    this.getListCnfMarca();
    return this.route.paramMap.subscribe(params => {
      this.id = params.get('id')!;
      console.log(this.id);
      if (!this.id) {
        this.isDataLoaded = true;
      }
      if (this.id) {
        this.cnfProductoService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          if (this.model.cnfMarca == null) {
            this.model.cnfMarca = this.selectDefaultCnfMarca;
          }
          this.isDataLoaded = true;
          
          //this.titulo = 'Editar ' + this.nombreModel;
        });
      }

    })

  }
  public save(): void {
    this.model.flagEstado = '1';
    this.cnfProductoService.save(this.model).subscribe(m => {
      console.log(m);
      this.isOk = true;
      this.utilService.msgOkSave();
      this.router.navigate([this.redirect]);
    }, err => {
      if (err.status === 422) {
        this.error = err.error;
        console.log(this.error);
      }
    });
  }
  getListCnfUnidadMedida() {
    this.loadingCnfUnidadMedida = true;
    console.log(this.chargingsb);
    return this.cnfUnidadMedidaService.getAllDataCombo().subscribe(data => {
      this.listCnfUnidadMedida = data;
      this.loadingCnfUnidadMedida = false;
    })

  }
  compareCnfUnidadMedida(a1: CnfUnidadMedida, a2: CnfUnidadMedida): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfEmpresa() {
    this.loadingCnfEmpresa = true;
    console.log(this.chargingsb);
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    if(cnfEmpresa == '*') {
      return this.cnfEmpresaService.getAllDataCombo().subscribe(data => {
        this.listCnfEmpresa = data;
        this.loadingCnfEmpresa = false;
      })
    } else {
      return this.cnfEmpresaService.getAllDataCombo().subscribe(data => {
        this.listCnfEmpresa = data;
        this.listCnfEmpresa = this.listCnfEmpresa.filter(data => data.id == cnfEmpresa);
        this.model.cnfEmpresa = this.listCnfEmpresa[0];
        this.loadingCnfEmpresa = false;
      })
    }
    

  }
  compareCnfEmpresa(a1: CnfEmpresa, a2: CnfEmpresa): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfSubCategoria() {
    this.loadingCnfSubCategoria = true;
    console.log(this.chargingsb);
    return this.cnfSubCategoriaService.getAllDataCombo().subscribe(data => {
      this.listCnfSubCategoria = data;
      this.loadingCnfSubCategoria = false;
    })

  }
  compareCnfSubCategoria(a1: CnfSubCategoria, a2: CnfSubCategoria): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  getListCnfMarca() {
    this.loadingCnfMarca = true;
    console.log(this.chargingsb);
    return this.cnfMarcaService.getAllDataCombo().subscribe(data => {
      this.listCnfMarca = data;
      this.loadingCnfMarca = false;
    })

  }
  compareCnfMarca(a1: CnfMarca, a2: CnfMarca): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
}

