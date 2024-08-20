
import { ActOrden } from "@pages/act-orden/act-orden.model";
import { CnfImpuestoCondicion } from "./cnf-impuesto-condicion.model";
import { CnfProducto } from "./cnf-producto.model";

export class ActOrdenDetalle {
	id: number = 0;
	descripcion: string = "";
	cantidad!: number;
	precio!: number;
	descuento!: number;
	importe!: number;
	afectacionIgv!: number;
	porcentajeGanancia!: number;
	actComprobante: ActOrden = new ActOrden();
	cnfProducto: CnfProducto = new CnfProducto();
	cnfImpuestoCondicion: CnfImpuestoCondicion = new CnfImpuestoCondicion();
	token?: string = "";
	precioVenta!: number;
};
