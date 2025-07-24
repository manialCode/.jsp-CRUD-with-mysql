package negocio;

import entidad.Movimientos;

public interface TransferenciaNegocio {

	public boolean enviarTransferencia(Movimientos movimiento, String CBU_Destino);
	

}
