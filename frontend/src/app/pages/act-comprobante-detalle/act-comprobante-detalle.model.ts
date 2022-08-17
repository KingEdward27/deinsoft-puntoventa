import { ActComprobante } from "../act-comprobante/act-comprobante.model";
import { CnfProducto } from "../cnf-producto/cnf-producto.model";
import { CnfImpuestoCondicion } from "../cnf-impuesto-condicion/cnf-impuesto-condicion.model";

export class ActComprobanteDetalle {
	id: int = "";
	descripcion: string = "";
	cantidad!: number;
	precio!: number;
	descuento!: number;
	importe!: number;
	afectacionIgv!: number;
	actComprobante: ActComprobante = new ActComprobante();
	cnfProducto: CnfProducto = new CnfProducto();
	cnfImpuestoCondicion: CnfImpuestoCondicion = new CnfImpuestoCondicion();
	token?: string = "";
};
