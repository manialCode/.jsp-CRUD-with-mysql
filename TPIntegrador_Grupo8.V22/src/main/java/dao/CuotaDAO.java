package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entidad.Cuotas;

public interface CuotaDAO {
    boolean ejecutarSPcrearCuota(Cuotas cuota);
    boolean ejecutarSPmodificarCuota(Cuotas cuota);
    boolean ejecutarSPeliminarCuota(int idCuota);
    List<Cuotas> readByPrestamo(int idPrestamo);
    List<Cuotas> readALL();
    public ArrayList<Cuotas> readAllCuo();
    
    public ArrayList<Cuotas> readByClient(String dniCli);
    
    boolean pagarCuotas(String listaCuotasCSV, String cbuCuenta);
}

