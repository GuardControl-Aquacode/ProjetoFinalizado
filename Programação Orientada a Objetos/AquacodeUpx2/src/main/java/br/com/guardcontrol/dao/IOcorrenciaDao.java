package br.com.guardcontrol.dao;

import br.com.guardcontrol.modelo.Ocorrencia;
import java.util.List;

public interface IOcorrenciaDao  {
    List<Ocorrencia> consultar();
    Ocorrencia consultar(Integer id);
    Ocorrencia cadastrar(Ocorrencia objeto);
    boolean alterar(Ocorrencia objeto, Integer id);
    boolean excluir(Integer id);
}
