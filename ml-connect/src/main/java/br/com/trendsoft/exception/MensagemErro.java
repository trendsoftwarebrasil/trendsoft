package br.com.trendsoft.exception;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A classe MensagemErro representa uma {@link Exception} customizada.
 *
 * @author SESTM (em: 16/09/2009).
 *
 */

public class MensagemErro extends Exception {

	/**
	 * mensagemErroFilha (Tipo: MensagemErro) - Objeto que possui a mesnagem de erro anterior a esta..
	 */
	private MensagemErro mensagemErroFilha;

	/**
	 * mensagemUsuario (Tipo: String) - String contendo a mensagem a ser exibída
	 * para o usuário.
	 */
	private String mensagemUsuario;

	/**
	 * serialVersionUID (Tipo: long) .
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger log = LogManager.getLogger(MensagemErro.class);

	/**
	 * Cria uma instância da classe MensagemErro.
	 * 
	 */
	public MensagemErro() {
		super();
	}

	/**
	 * Cria uma instância da classe MensagemErro.
	 * 
	 * @param mensagem
	 */
	public MensagemErro(String mensagem) {
		super(mensagem);
	}

	/**
	 * Cria uma instância da classe MensagemErro.
	 * 
	 * @param causa
	 */
	public MensagemErro(Throwable causa) {
		super(causa);
	}

	/**
	 * Cria uma instância da classe MensagemErro.
	 * 
	 * @param mensagem
	 * @param causa
	 */
	public MensagemErro(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	/**
	 * Cria uma instância da classe MensagemErro.java.
	 * 
	 * @param mensagem
	 * @param causa
	 * @param mensagemErroFilha
	 * @param mensagemUsuario
	 */
	public MensagemErro(String mensagem, Throwable causa,
			MensagemErro mensagemErroFilha, String mensagemUsuario) {
		super(mensagem, causa);
		this.setMensagemErroFilha(mensagemErroFilha);
		this.setMensagemUsuario(mensagemUsuario);
	}
	
	/**
	 * Recupera o valor do atributo mensagemErroFilha.
	 * 
	 * @return mensagemErroFilha
	 */
	public MensagemErro getMensagemErroFilha() {
		return mensagemErroFilha;
	}

	/**
	 * Atribui um valor ao atributo mensagemErroFilha.
	 * 
	 * @param mensagemErroFilha
	 *            Valor a ser atribuído.
	 */
	public void setMensagemErroFilha(MensagemErro mensagemErroFilha) {
		this.mensagemErroFilha = mensagemErroFilha;
	}

	/**
	 * Recupera o valor do atributo mensagemUsuario.
	 * 
	 * @return mensagemUsuario
	 */
	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	/**
	 * Atribui um valor ao atributo mensagemUsuario.
	 * 
	 * @param mensagemUsuario
	 *            Valor a ser atribuído.
	 */
	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}

	/**
	 * Verifica se o objeto <b>(MensagemErro)</b> possui outro obejto
	 * MensagemErro <b>(mensagem de erro filha)</b> no atributo
	 * mensagemErroFilha.
	 * 
	 * @return
	 */
	public boolean temFilho() {

		if (this.getMensagemErroFilha() == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Método que gera um arquivo de erro/log. Consulta o próprio objeto e busca
	 * a todas as mensagens de erros inclusas/filhas (atrvés do atributo
	 * mensagemErroFilha), gerando uma arquivo com o percurso e as descrições
	 * das mensagens de erro das MensagemErro.
	 * 
	 */
	public void geraArquivoMensagemErro() {

		int cont = 1;
		ArrayList<String> listaMensagemErro = new ArrayList<String>();

		if (this.temFilho()) {

			this.geraListaMensagemErro(this, listaMensagemErro, cont);
			cont = cont + 1;

			MensagemErro msgErrofilha = this.getMensagemErroFilha();

			while (msgErrofilha.temFilho()) {
				this.geraListaMensagemErro(msgErrofilha, listaMensagemErro,
						cont);
				cont = cont + 1;
				msgErrofilha = msgErrofilha.getMensagemErroFilha();
			}

			this.geraListaMensagemErro(msgErrofilha, listaMensagemErro, cont);

		} else {

			this.geraListaMensagemErro(this, listaMensagemErro, cont);

		}

		this.geraLogMsgErro(listaMensagemErro);

	}

	/**
	 * Método que gera um arquivo de erro/log e retorna a primeira mensagem de
	 * erro (MensagemErro). Consulta o próprio objeto e busca a todas as
	 * mensagens de erros inclusas/filhas (atrvés do atributo
	 * mensagemErroFilha), gerando uma arquivo com o percurso e as descrições
	 * das mensagens de erro das MensagemErro e retornando a primeiro erro
	 * encontrado.
	 * 
	 * @param idGeraArquivoMensagemErro
	 *            <b>S</b> para gerar o arquivo de erro.
	 * @return Retorna a primeira mensagem de erro (MensagemErro), a primeira
	 *         {@link Exception}
	 */
	public MensagemErro recuperaPrimeiraMensagemErroFilha(
			String idGeraArquivoMensagemErro) {

		MensagemErro mensagemErroRetorno;
		int cont = 1;
		ArrayList<String> listaMensagemErro = new ArrayList<String>();

		if (this.temFilho()) {

			this.geraListaMensagemErro(this, listaMensagemErro, cont);
			cont = cont + 1;

			MensagemErro msgErrofilha = this.getMensagemErroFilha();

			while (msgErrofilha.temFilho()) {
				this.geraListaMensagemErro(msgErrofilha, listaMensagemErro,
						cont);
				cont = cont + 1;
				msgErrofilha = msgErrofilha.getMensagemErroFilha();
			}

			this.geraListaMensagemErro(msgErrofilha, listaMensagemErro, cont);

			mensagemErroRetorno = msgErrofilha;

		} else {

			this.geraListaMensagemErro(this, listaMensagemErro, cont);
			mensagemErroRetorno = this;

		}

		if (idGeraArquivoMensagemErro.equals("S")) {
			this.geraLogMsgErro(listaMensagemErro);
		}

		return mensagemErroRetorno;

	}

	/**
	 * Formata texto de erro a ser gerado. Utiliza um ArrayList<String>
	 * listaMensagemErro, para armazenar as descrições da mensagem de erro.
	 * 
	 * @param msgErro
	 *            Objeto MensagemErro que armazena a {@link Exception}.
	 * @param listaMensagemErro
	 *            <code>ArrayList<String></code> que armazena as mensagen de
	 *            erro.
	 * @param cont
	 *            Contador numérico pára encadear as mensagen.
	 */
	private void geraListaMensagemErro(MensagemErro msgErro,
			ArrayList<String> listaMensagemErro, int cont) {

		listaMensagemErro.add("MENSAGEM DE ERRO: " + cont
				+ " ------------------------------------------------");
		listaMensagemErro.add("-- Classe          : "
				+ msgErro.getClass().getName());
		listaMensagemErro.add("-- Mensagem Java   : " + msgErro.getMessage());
		listaMensagemErro.add("-- Mensagem Usuario: "
				+ msgErro.getMensagemUsuario());
		listaMensagemErro
				.add("-----------------------------------------------------------------------------");
		listaMensagemErro
				.add("                                                                             ");

	}

	/**
	 * Gera o arquivo de erro.
	 * 
	 * @param listaMensagemErro
	 *            <code>ArrayList<String></code> contendo as mensagen de
	 *            erro.
	 */
	private void geraLogMsgErro(ArrayList<String> listaMensagemErro) {
		try {
			int tamanho = (listaMensagemErro.size()) - 1;
			int cont = 0;

			while (cont <= tamanho) {
				String texto = listaMensagemErro.get(cont);
				log.error(texto);
				cont = cont + 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static final long getSerialversionuid() {
		return serialVersionUID;
	}
}
