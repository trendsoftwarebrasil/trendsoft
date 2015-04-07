package br.com.trendsoft.exception;

import javax.persistence.EntityTransaction;


public class DaoException extends MensagemErro {
	
	private EntityTransaction transacao;
	
	/**
	 * serialVersionUID (Tiop: long) .
	 */
	private static final long serialVersionUID = 1L;
	
	public DaoException () {
		super();
	}
	
	public DaoException (String mensagem) {
		super(mensagem);
	}
	
	public DaoException (Throwable causa) {
		super(causa);
	}
	
	public DaoException (String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public DaoException (MensagemErro mensagemErroFilha, String mensagemUsuario) {
		super();
		this.setMensagemErroFilha(mensagemErroFilha);
		this.setMensagemUsuario(mensagemUsuario);
	}
	
	public DaoException (String mensagem, MensagemErro mensagemErroFilha, String mensagemUsuario) {
		super(mensagem);
		this.setMensagemErroFilha(mensagemErroFilha);
		this.setMensagemUsuario(mensagemUsuario);
	}
	
	public DaoException (Throwable causa, MensagemErro mensagemErroFilha, String mensagemUsuario) {
		super(causa);
		this.setMensagemErroFilha(mensagemErroFilha);
		this.setMensagemUsuario(mensagemUsuario);
	}
	

	public DaoException (String mensagem, Throwable causa, MensagemErro mensagemErroFilha, String mensagemUsuario) {
		super(mensagem, causa);
		this.setMensagemErroFilha(mensagemErroFilha);
		this.setMensagemUsuario(mensagemUsuario);
	}

	public DaoException(String message, Throwable cause, String string) {
		
	}

	public EntityTransaction getTransacao() {
		return transacao;
	}

	public void setTransacao(EntityTransaction transacao) {
		this.transacao = transacao;
	}
	
}
