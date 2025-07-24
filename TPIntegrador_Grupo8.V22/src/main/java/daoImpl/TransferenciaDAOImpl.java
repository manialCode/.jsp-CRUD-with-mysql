package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;

import dao.Conexion;
import entidad.Movimientos;

public class TransferenciaDAOImpl {

	public TransferenciaDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	 public boolean ejecutarSPenviarTransferencia(Movimientos movimiento, String cbu_Destino) {
	        boolean estado = false;
	        Connection cn = null;

	        try {
	            cn = Conexion.obtenerConexion();
	            CallableStatement cs = cn.prepareCall("CALL enviarTransferencia(?,?,?,?)");
	            cs.setString(1, movimiento.getCBU_mo());
	            cs.setBigDecimal(2, movimiento.getImporte_mo());
	            cs.setString(3, movimiento.getDetalle_mo());
	            cs.setString(4, cbu_Destino);
	           
	            cs.execute();
	            estado = true;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return estado;
	    }


}