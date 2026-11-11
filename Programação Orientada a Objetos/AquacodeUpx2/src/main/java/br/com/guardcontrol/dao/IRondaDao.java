package br.com.guardcontrol.dao;

import br.com.guardcontrol.modelo.Ronda;
import java.util.List;

public interface IRondaDao  {
    List<Ronda> consultar();
    Ronda consultar(Integer id);
    Ronda cadastrar(Ronda objeto);
    boolean alterar(Ronda objeto, Integer id);
    boolean excluir(Integer id);
}
