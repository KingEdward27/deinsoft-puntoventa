
import { CnfUnidadMedida } from '@/business/model/cnf-unidad-medida.model';
import * as dayjs from 'dayjs';
import { CnfEmpresa } from '../../business/model/cnf-empresa.model';
import { CnfSubCategoria } from '../../business/model/cnf-sub-categoria.model';
import { CnfMarca } from '../../business/model/cnf-marca.model';
import { CnfPaqueteProducto } from '@/business/model/cnf-paquete-producto.model';

export class CnfProductosdd {
	id: number = 0;
	codigo: string = "";
	nombre: string = "";
	costo: number = 0;
	precio: number = 0;
	existencia!: number;
	rutaImagen: string = "";
	flagEstado: string = "";
	barcode: string = "";
	cnfUnidadMedida: CnfUnidadMedida = new CnfUnidadMedida();
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	cnfSubCategoria: CnfSubCategoria = new CnfSubCategoria();
	cnfMarca: CnfMarca = new CnfMarca();
	porcentajeGanancia: number = 0;
	stockMinimo!: number;
	token?: string = "";
	file:any;

	listCnfPaqueteDet:CnfPaqueteProducto[] = [];
};
