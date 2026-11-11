package br.com.guardcontrol.dao;

import br.com.guardcontrol.modelo.Cliente;
import java.util.List;

public interface IClienteDao  {
    List<Cliente> consultar();
    Cliente consultar(Integer id);
    Cliente cadastrar(Cliente objeto);
    boolean alterar(Cliente objeto, Integer id);
    boolean excluir(Integer id);
}
