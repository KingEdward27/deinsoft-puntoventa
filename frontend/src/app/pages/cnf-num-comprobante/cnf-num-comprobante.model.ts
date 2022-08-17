import { CnfTipoComprobante } from "../cnf-tipo-comprobante/cnf-tipo-comprobante.model";
import { CnfLocal } from "../cnf-local/cnf-local.model";

export class CnfNumComprobante {
	id: int = "";
	serie: string = "";
	ultimoNro!: int;
	cnfTipoComprobante: CnfTipoComprobante = new CnfTipoComprobante();
	cnfLocal: CnfLocal = new CnfLocal();
	token?: string = "";
};
