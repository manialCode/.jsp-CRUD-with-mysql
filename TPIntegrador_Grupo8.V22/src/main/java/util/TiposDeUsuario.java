package util;

public enum TiposDeUsuario {
	USUARIO_BANCO(1),
	USUARIO_CLIENTE(2);
	
	private final int tipo;
	
	TiposDeUsuario(int tipoU){
		this.tipo = tipoU;
	}
	
	public int getTipo() {
		return tipo;
	}
}
