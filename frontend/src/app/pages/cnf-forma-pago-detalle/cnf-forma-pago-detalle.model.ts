import { CnfFormaPago } from "../cnf-forma-pago/cnf-forma-pago.model";

export class CnfFormaPagoDetalle {
	id: int = "";
	modoDiasIntervalo!: int;
	modoPorcentaje!: number;
	modoMonto!: number;
	modoDiaVencimiento!: int;
	cnfFormaPago: CnfFormaPago = new CnfFormaPago();
	token?: string = "";
};
