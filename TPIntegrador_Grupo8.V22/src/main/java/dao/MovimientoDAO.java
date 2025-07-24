package dao;

import java.util.ArrayList;

import entidad.Movimientos;

public interface MovimientoDAO {
    ArrayList<Movimientos> readAll();
    ArrayList<Movimientos> readByCBU(String cbu);
    ArrayList<Movimientos> readByClient(String dni);
    boolean insert(Movimientos mov);
    boolean update(Movimientos mov);
    boolean delete(int id);
    public ArrayList<Movimientos> readByMovementType(int idTipo);
}
