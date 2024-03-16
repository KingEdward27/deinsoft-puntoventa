import { CnfEmpresa } from './cnf-empresa.model';

export class SegUsuario {
	id: number = 0;
	email: string = "";
	nombre: string = "";
	password: string = "";
	estado!: number;
	token?: string = "";
	rucEmpresa: string;
	nombreEmpresa:string;
};
