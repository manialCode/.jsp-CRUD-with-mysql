package daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.Conexion;
import dao.ReporteDAO;
import entidad.Clientes;
import entidad.Cuentas;
import entidad.Movimientos;
import entidad.Prestamos;

public class ReporteDAOImpl implements ReporteDAO {

	public ArrayList<String> ejecutarSPreportes4() {
		ArrayList<String> resultadoFinal = new ArrayList<String>();
		Connection cn = null;
		try {
			cn = Conexion.obtenerConexion();
			CallableStatement cst = cn.prepareCall("CALL reportes4");
			ResultSet resultadoCST = cst.executeQuery();
			while (resultadoCST.next()) {
				String resultado = resultadoCST.getString("estado_pre") + ": " + resultadoCST.getInt("total_prestamos");
				resultadoFinal.add(resultado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoFinal;
	}

	public ArrayList<String> ejecutarSPreportes3() {
		ArrayList<String> resultadoFinal = new ArrayList<String>();
		Connection cn = null;
		try {
			cn = Conexion.obtenerConexion();
			CallableStatement cst = cn.prepareCall("CALL reportes3");
			ResultSet resultadoCST = cst.executeQuery();
			while (resultadoCST.next()) {
				String resultado = resultadoCST.getString("Tipo de Cuenta") + ": " + resultadoCST.getInt("Cantidad");
				resultadoFinal.add(resultado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoFinal;
	}

	public ArrayList<Prestamos> ejecutarSPreportes2(Clientes cliente, String apellido, int idTipo) {
		if (cliente == null)
			return null;
		ArrayList<Prestamos> resultadoFinal = new ArrayList<Prestamos>();
		Connection cn = null;
		try {
			cn = Conexion.obtenerConexion();
			CallableStatement cst = cn.prepareCall("CALL reportes2(?,?,?)");
			cst.setString(1, cliente.getDni_cli());
			cst.setString(2, cliente.getApellido_cli());
			cst.setInt(3, idTipo);
			ResultSet resultadoCST = cst.executeQuery();
			while (resultadoCST.next()) {
				Prestamos prestamo = new Prestamos();
				prestamo.setId_pre(resultadoCST.getInt("id_pre"));
				prestamo.setCBU_pre(resultadoCST.getString("CBU_pre"));
				prestamo.setDni_pre(resultadoCST.getString("dni_pre"));
				prestamo.setFecha_pre(resultadoCST.getDate("fecha_pre"));
				prestamo.setImportePedido_pre(resultadoCST.getBigDecimal("importePedido_pre"));
				prestamo.setIntereses_pre(resultadoCST.getBigDecimal("intereses_pre"));
				prestamo.setCant_Cuotas(resultadoCST.getInt("cantCuotas_pre"));
				prestamo.setMontoTotal_pre(resultadoCST.getBigDecimal("montoTotal_pre"));
				prestamo.setMontoMensual_pre(resultadoCST.getBigDecimal("montoMensual_pre"));
				prestamo.setEstado_pre(resultadoCST.getString("estado_pre"));

				resultadoFinal.add(prestamo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoFinal;
	}

	public ArrayList<Movimientos> ejecutarSPreportes1(Date fechaInicio, Date fechaFin) {
		ArrayList<Movimientos> resultadoFinal = new ArrayList<Movimientos>();
		Connection cn = null;
		try {
			cn = Conexion.obtenerConexion();
			CallableStatement cst = cn.prepareCall("CALL reportes1(?,?)");
			cst.setDate(1, fechaInicio);
			cst.setDate(2, fechaFin);
			cst.execute();
			ResultSet resultadoCST = cst.executeQuery();

			while (resultadoCST.next()) {
				Movimientos movimiento = new Movimientos();
				movimiento.setFecha_mo(resultadoCST.getDate("fecha_mo"));
				movimiento.setImporte_mo(resultadoCST.getBigDecimal("importe_mo"));
				movimiento.setTipoDeMovimiento_mo(resultadoCST.getInt("tipoDeMovimiento_mo"));
				movimiento.setCBU_mo(resultadoCST.getString("CBU_mo"));

				resultadoFinal.add(movimiento);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoFinal;
	}

	@Override
	public ArrayList<String> ejecutarSPreportes5() {
		ArrayList<String> resultadoFinal = new ArrayList<String>();
		Connection cn = null;
		try {
			cn = Conexion.obtenerConexion();
			CallableStatement cst = cn.prepareCall("CALL reportes5");
			ResultSet resultadoCST = cst.executeQuery();
			while (resultadoCST.next()) {
				String resultado = resultadoCST.getString("Tipo de Movimiento") + ": " + resultadoCST.getInt("TotalMovimientos");
				resultadoFinal.add(resultado);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultadoFinal;
	}

}
