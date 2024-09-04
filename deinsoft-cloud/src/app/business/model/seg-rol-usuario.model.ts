import { SegRol } from './seg-rol.model';
import { SegUsuario } from './seg-usuario.model';

export class SegRolUsuario {
	id: number = 0;
	segRol: SegRol = new SegRol();
	segUsuario: SegUsuario = new SegUsuario();
	token?: string = "";
};
