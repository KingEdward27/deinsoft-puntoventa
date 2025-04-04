
import { ActComprobante } from "@pages/act-comprobante/act-comprobante.model";
import { CnfImpuestoCondicion } from "./cnf-impuesto-condicion.model";
import { CnfProducto } from "./cnf-producto.model";
import { CnfMedioPago } from "./cnf-medio-pago.model";

export class ActMedioPagoDetalle {
	id: number = 0;
	actComprobante: ActComprobante = new ActComprobante();
	cnfMedioPago: CnfMedioPago = new CnfMedioPago();
	monto!: number;
	index: number;
};
