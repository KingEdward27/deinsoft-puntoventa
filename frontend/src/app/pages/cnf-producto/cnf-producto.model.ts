import { CnfUnidadMedida } from "../cnf-unidad-medida/cnf-unidad-medida.model";
import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";
import { CnfSubCategoria } from "../cnf-sub-categoria/cnf-sub-categoria.model";
import { CnfMarca } from "../cnf-marca/cnf-marca.model";

export class CnfProducto {
	id: int = "";
	codigo: string = "";
	nombre: string = "";
	precio!: number;
	existencia!: number;
	fechaRegistro!: localdatetime;
	rutaImagen: string = "";
	flagEstado: string = "";
	barcode: string = "";
	cnfUnidadMedida: CnfUnidadMedida = new CnfUnidadMedida();
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	cnfSubCategoria: CnfSubCategoria = new CnfSubCategoria();
	cnfMarca: CnfMarca = new CnfMarca();
	token?: string = "";
};
