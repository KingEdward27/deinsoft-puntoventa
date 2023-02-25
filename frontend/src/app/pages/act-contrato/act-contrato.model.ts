import { CnfMaestro } from "../cnf-maestro/cnf-maestro.model";
import { CnfLocal } from "../cnf-local/cnf-local.model";
import { CnfTipoComprobante } from "../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { CnfFormaPago } from "../cnf-forma-pago/cnf-forma-pago.model";
import { CnfPlanContrato } from "../cnf-plan-contrato/cnf-plan-contrato.model";
import * as dayjs from 'dayjs';

export class ActContrato {
	id: number = 0;
	fecha?: dayjs.Dayjs;
	fechaRegistro!: localdatetime;
	flagEstado: string = "";
	numero: string = "";
	observacion: string = "";
	serie: string = "";
	nroPoste: string = "";
	urlMap: string = "";
	cnfMaestro: CnfMaestro = new CnfMaestro();
	cnfLocal: CnfLocal = new CnfLocal();
	cnfTipoComprobante: CnfTipoComprobante = new CnfTipoComprobante();
	cnfFormaPago: CnfFormaPago = new CnfFormaPago();
	cnfPlanContrato: CnfPlanContrato = new CnfPlanContrato();
	token?: string = "";
};
