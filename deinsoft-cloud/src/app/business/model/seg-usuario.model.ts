import { CnfEmpresa } from './cnf-empresa.model';
import { SegRolUsuario } from './seg-rol-usuario.model';

export class SegUsuario {
	id: number = 0;
	email: string = "";
	nombre: string = "";
	password: string = "";
	repassword: string = "";
	estado!: number;
	token?: string = "";
	rucEmpresa: string;
	nombreEmpresa:string;
	perfilEmpresa: number = 0;
	tokenRecoverPassword?: string = "";
	listSegRolUsuario: SegRolUsuario[] = [];
};
