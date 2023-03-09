import { CnfTipoComprobante } from "../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { ActPagoDetalle } from "../act-pago-detalle/act-pago-detalle.model";
import * as dayjs from 'dayjs';

export class ActPago {
	id: number = 0;
	billete!: number;
	fecha?: dayjs.Dayjs | null;
	fechaRegistro!: localdatetime;
	igv!: number;
	numero: string = "";
	serie: string = "";
	subtotal!: number;
	total!: number;
	vuelto!: number;
	cnfTipoComprobante: CnfTipoComprobante = new CnfTipoComprobante();
	listActPagoDetalle: ActPagoDetalle[] = [];
	token?: string = "";
};
