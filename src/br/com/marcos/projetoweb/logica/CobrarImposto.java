package br.com.marcos.projetoweb.logica;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.marcos.projetoweb.dao.ContaDAO;
import br.com.marcos.projetoweb.dao.SeguroDAO;
import br.com.marcos.projetoweb.model.Conta;
import br.com.marcos.projetoweb.model.ContaCorrente;
import br.com.marcos.projetoweb.model.ContaPoupanca;
import br.com.marcos.projetoweb.model.Produto;
import br.com.marcos.projetoweb.model.Seguro;

public class CobrarImposto implements Logica{

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		String opcao = req.getParameter("opcao");

		Produto produto = (Produto)session.getAttribute("produto");
		if(opcao.equals("cobrar")) {
			if(produto instanceof ContaCorrente) {
				ContaDAO cDAO = new ContaDAO();
				ContaCorrente conta = (ContaCorrente) produto;
				conta.setSaldo(conta.getSaldo() - conta.valorImposto());
				cDAO.alterarSaldo(conta);
				return "sucesso.jsp?pagina=conta";
			}
			else if(produto instanceof ContaPoupanca) {
				ContaDAO cDAO = new ContaDAO();
				ContaPoupanca conta = (ContaPoupanca) produto;
				conta.setSaldo(conta.getSaldo() - conta.valorImposto());
				cDAO.alterarSaldo(conta);
				return "sucesso.jsp?pagina=conta";
			}
			else if(produto instanceof Seguro) {
				SeguroDAO sDAO = new SeguroDAO();
				Seguro seguro = (Seguro) produto;
				seguro.setValor(seguro.getValor() - seguro.valorImposto());
				sDAO.alterarValor(seguro);
				return "sucesso.jsp?pagina=seguro";
			}
		}
		if(produto instanceof Conta) {
			return "conta.jsp";
		}
		else {
			return "seguro.jsp";
		}
	}

}