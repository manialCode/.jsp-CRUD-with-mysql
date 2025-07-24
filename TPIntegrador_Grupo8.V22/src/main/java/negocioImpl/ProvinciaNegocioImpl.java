package negocioImpl;

import java.util.ArrayList;

import daoImpl.ProvinciaDAOIml;
import entidad.Provincias;
import negocio.ProvinciaNegocio;

public class ProvinciaNegocioImpl implements ProvinciaNegocio {
	
	private ProvinciaDAOIml pd = new ProvinciaDAOIml();
	
@Override
public ArrayList<Provincias> getAll(){
	return pd.readAllProvinces();
}
}
