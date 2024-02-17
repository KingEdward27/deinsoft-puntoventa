import { CnfMenuTipoSistema } from "../cnf-menu-tipo-sistema/cnf-menu-tipo-sistema.model";

export class CnfTipoSistema {
	id: number = 0;
	nombre: string = "";
	listCnfMenuTipoSistema: CnfMenuTipoSistema[] = [];
	token?: string = "";
};
