package rubrica;

import exception.FormatoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Luca Borsalino
 * Date: 2019/12/09
 * <p>
 * Versione con aggiunta della lettura e della scrittura da/su file.
 * Aggiunti anche i test (funzionanti) per i due metodi aggiunti.
 * Aggiunta la regex per il nome, da capire perche' non funziona quella per l'email.
 */

public class Contatto
{
	private final String NOME;
	private String eMail;
	private ArrayList<String> numeriTel;
	String regexNome = "[A-Z]([a-z])+";
	String regexMail = "(([a-z[A-Z[0-9]]])+[@]([a-z])+[.]([a-z])+){0,1}";
	String regexTelefono = "(((\\+39)?[0-9]{10})(,(\\+39)?[0-9]{10})*)?";

	public Contatto(String nome, String email, String tels) throws FormatoException
	{
		if (!nome.matches(regexNome)) throw new FormatoException();
		else NOME = nome;
		if (!email.matches(regexMail)) throw new FormatoException();
		else this.eMail = email;
		if (!tels.matches(regexTelefono)) throw new FormatoException();
		else numeriTel = new ArrayList<>(Arrays.asList(tels.split(",")));
	}

	public Contatto(String nome, String email) throws FormatoException
	{
		this(nome, email, "");
	}

	public Contatto(String nome) throws FormatoException
	{
		this(nome, "", "");
	}

	public String getEMail()
	{
		return eMail;
	}

	public String getNome()
	{
		return NOME;
	}

	public void setEMail(String eMail)
	{
		this.eMail = eMail;
	}

	/**
	 * Metodo che inserisce un numero nella lista dei numeri di telefono
	 *
	 * @param num: il nuovo numero da inserire
	 * @return 1 se il nuovo numero e' stato inserito, <br>-1 se era gia' presente.
	 */
	public int inserisciNumTel(String num)
	{
		if (numeriTel.contains(num)) return -1;
		numeriTel.add(num);
		return 1;
	}

	/**
	 * Metodo che inserisce un numero nella lista dei numeri di telefono
	 *
	 * @param num: il nuovo numero da inserire
	 * @return : 1 se il nuovo numero e' stato eliminato, <br>-1 se non era presente.
	 */
	public int eliminaNumTel(String num)
	{
		if (!numeriTel.contains(num)) return -1;
		numeriTel.remove(num);
		return 1;
	}

	/**
	 * Metodo che crea una stringa con tutti i numeri di telefono di un contatto.
	 *
	 * @return la stringa che concatena con una virgola i numeri di telefono.
	 */
	public String numTel()
	{
		StringBuilder sb = new StringBuilder();
		int i;

		for (i = 0; i < numeriTel.size() - 1; i++)
		{
			sb.append(numeriTel.get(i)).append(",");
		}
		sb.append(numeriTel.get(i));
		return String.valueOf(sb);
	}

	/**
	 * Metodo toString modificato appositamente per effettuare la scrittura su file.
	 *
	 * @return la stringa che concatena il NOME, l'eMail ed il/i numero/i di
	 * telefono del contatto.
	 */
	@Override public String toString()
	{
		return NOME + "," + eMail + "," + numTel() + "\n";
	}

	/**
	 * Equals modificato per confrontare due oggetti contatto.
	 */
	public boolean equals(Object contatto)
	{
		if (this == contatto) return true;
		if (!(contatto instanceof Contatto)) return false;
		Contatto cont = (Contatto) contatto;
		return Objects.equals(NOME, cont.getNome());
	}
}
