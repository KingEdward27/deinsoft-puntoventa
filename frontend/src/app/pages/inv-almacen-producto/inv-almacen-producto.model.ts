import { InvAlmacen } from "../inv-almacen/inv-almacen.model";
import { CnfProducto } from "../cnf-producto/cnf-producto.model";

export class InvAlmacenProducto {
	id: int = "";
	cantidad!: number;
	invAlmacen: InvAlmacen = new InvAlmacen();
	cnfProducto: CnfProducto = new CnfProducto();
	token?: string = "";
};
