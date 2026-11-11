package br.com.guardcontrol.dao;

import br.com.guardcontrol.modelo.Gestor;
import java.util.List;

public interface IGestorDao  {
    List<Gestor> consultar();
    Gestor consultar(Integer id);
    Gestor cadastrar(Gestor objeto);
    boolean alterar(Gestor objeto, Integer id);
    boolean excluir(Integer id);
}
