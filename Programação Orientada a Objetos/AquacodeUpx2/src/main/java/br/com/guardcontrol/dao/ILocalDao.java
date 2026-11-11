package br.com.guardcontrol.dao;

import br.com.guardcontrol.modelo.Local;
import java.util.List;

public interface ILocalDao  {
    List<Local> consultar();
    Local consultar(Integer id);
    Local cadastrar(Local objeto);
    boolean alterar(Local objeto, Integer id);
    boolean excluir(Integer id);
}
