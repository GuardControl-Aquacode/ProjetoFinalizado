package br.com.guardcontrol.dao;

import br.com.guardcontrol.modelo.Vigilante;
import java.util.List;

public interface IVigilanteDao  {
    List<Vigilante> consultar();
    Vigilante consultar(String id);
    Vigilante cadastrar(Vigilante objeto);
    boolean alterar(Vigilante objeto, String id);
    boolean excluir(String id);
}
