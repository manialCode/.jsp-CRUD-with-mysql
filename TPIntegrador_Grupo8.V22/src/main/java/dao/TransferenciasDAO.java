package dao;

import entidad.Movimientos;

public interface TransferenciasDAO {
	
	public boolean ejecutarSPenviarTransferencia(Movimientos movimiento, String cbu_Destino);

}
