
import { CnfUnidadMedida } from '@/business/model/cnf-unidad-medida.model';
import * as dayjs from 'dayjs';
import { CnfEmpresa } from '../../business/model/cnf-empresa.model';
import { CnfSubCategoria } from '../../business/model/cnf-sub-categoria.model';
import { CnfMarca } from '../../business/model/cnf-marca.model';

export class CnfProducto {
	id: number = 0;
	codigo: string = "";
	nombre: string = "";
	costo!: number;
	precio!: number;
	existencia!: number;
	rutaImagen: string = "";
	flagEstado: string = "";
	barcode: string = "";
	cnfUnidadMedida: CnfUnidadMedida = new CnfUnidadMedida();
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	cnfSubCategoria: CnfSubCategoria = new CnfSubCategoria();
	cnfMarca: CnfMarca = new CnfMarca();
	porcentajeGanancia!: number;
	token?: string = "";
	file:any;
};
