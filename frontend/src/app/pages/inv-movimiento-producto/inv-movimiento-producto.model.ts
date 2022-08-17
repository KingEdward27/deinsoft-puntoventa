import { InvAlmacen } from "../inv-almacen/inv-almacen.model";
import { CnfProducto } from "../cnf-producto/cnf-producto.model";
import { ActComprobante } from "../act-comprobante/act-comprobante.model";
import * as dayjs from 'dayjs';

export class InvMovimientoProducto {
	id: int = "";
	fecha?: dayjs.Dayjs | null;
	fechaRegistro!: localdatetime;
	cantidad!: number;
	invAlmacen: InvAlmacen = new InvAlmacen();
	cnfProducto: CnfProducto = new CnfProducto();
	actComprobante: ActComprobante = new ActComprobante();
	token?: string = "";
};
