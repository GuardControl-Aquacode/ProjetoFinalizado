package br.com.guardcontrol;

import br.com.guardcontrol.dao.ClienteDao;
import br.com.guardcontrol.dao.VigilanteDao;
import br.com.guardcontrol.factory.ConnectionFactory;
import br.com.guardcontrol.modelo.Cliente;
import br.com.guardcontrol.modelo.Vigilante;
import br.com.guardcontrol.tela.MenuGestor;
import br.com.guardcontrol.tela.MenuVigilante;
import br.com.guardcontrol.util.Entrada;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class GuardControl {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            try (Connection teste = ConnectionFactory.getConnection()) {
                System.out.println("Conexão com guard_control estabelecida!");
            } catch (SQLException e) {
                System.out.println("Não foi possível conectar ao MySQL: " + e.getMessage());
                System.out.println("Confira o DER e AQUACODE_DB_URL, AQUACODE_DB_USER, AQUACODE_DB_PASSWORD.");
                return;
            }
            ClienteDao empresas = new ClienteDao();
            VigilanteDao vigilantes = new VigilanteDao();
            while (true) {
                System.out.println("\n========= Guard Control =========");
                System.out.println("1 - Selecionar empresa por ID (demonstração)");
                System.out.println("2 - Selecionar vigilante por matrícula)");
                System.out.println("3 - Cadastrar empresa");
                System.out.println("0 - Encerrar");
                try {
                    switch (Entrada.texto(sc, "Escolha")) {
                        case "1":
                            for (Cliente c : empresas.consultar()) System.out.println(c.getIdCliente() + " - " + c.getNome());
                            Cliente empresa = empresas.consultar(Entrada.inteiro(sc, "ID da empresa"));
                            if (empresa == null) System.out.println("Empresa nao encontrada.");
                            else new MenuGestor(sc, empresa).exibir();
                            break;
                        case "2":
                            Vigilante v = vigilantes.consultarPorMatricula(Entrada.obrigatorio(sc, "Matricula/RE"));
                            if (v == null) System.out.println("Matricula não encontrada.");
                            else new MenuVigilante(sc, v).exibir();
                            break;
                        case "3": cadastrarEmpresa(sc, empresas); break;
                        case "0": System.out.println("Guard Control encerrado."); return;
                        default: System.out.println("Opcao invalida.");
                    }
                } catch (IllegalStateException | IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }
    private static void cadastrarEmpresa(Scanner sc, ClienteDao dao) {
        Cliente c = new Cliente();
        c.setNome(Entrada.obrigatorio(sc, "Nome da empresa"));
        c.setTelefone(Entrada.obrigatorio(sc, "Telefone"));
        c.setEmail(Entrada.texto(sc, "E-mail (opcional)"));
        if (Entrada.confirmar(sc, "Confirmar cadastro?")) {
            dao.cadastrar(c);
            System.out.println("Empresa cadastrada. ID: " + c.getIdCliente());
        }
    }
}
