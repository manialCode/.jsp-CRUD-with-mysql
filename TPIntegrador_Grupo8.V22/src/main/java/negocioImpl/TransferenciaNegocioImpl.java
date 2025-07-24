package negocioImpl;

import negocio.TransferenciaNegocio;
import entidad.Movimientos;
import daoImpl.TransferenciaDAOImpl;

public class TransferenciaNegocioImpl implements TransferenciaNegocio {
	
	private TransferenciaDAOImpl transferenciaDAOImpl = new TransferenciaDAOImpl();

	public TransferenciaNegocioImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean enviarTransferencia(Movimientos movimiento, String CBU_Destino) {
		
		return transferenciaDAOImpl.ejecutarSPenviarTransferencia(movimiento, CBU_Destino);
	}

}