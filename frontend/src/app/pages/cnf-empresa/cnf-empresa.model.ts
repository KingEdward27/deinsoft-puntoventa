import { CnfTipoDocumento } from "../cnf-tipo-documento/cnf-tipo-documento.model";
import { CnfDistrito } from "../cnf-distrito/cnf-distrito.model";

export class CnfEmpresa {
	id: int = "";
	nombre: string = "";
	descripcion: string = "";
	nroDocumento: string = "";
	direccion: string = "";
	telefono: string = "";
	empresacol: string = "";
	estado: string = "";
	token: string = "";
	cnfTipoDocumento: CnfTipoDocumento = new CnfTipoDocumento();
	cnfDistrito: CnfDistrito = new CnfDistrito();
	token?: string = "";
};
