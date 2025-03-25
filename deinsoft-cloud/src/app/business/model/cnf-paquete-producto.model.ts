import dayjs from "dayjs";
import { CnfEmpresa } from "./cnf-empresa.model";
import { CnfMarca } from "./cnf-marca.model";
import { CnfSubCategoria } from "./cnf-sub-categoria.model";
import { CnfUnidadMedida } from "./cnf-unidad-medida.model";
import { CnfProducto } from "./cnf-producto.model";

export class CnfPaqueteProducto {
	id: string = "";
	cnfProducto: CnfProducto = new CnfProducto();
	descripcion:string;
	cnfProductoContenido: CnfProducto = new CnfProducto();
	cantidad:number;
	index:number=0;
};
