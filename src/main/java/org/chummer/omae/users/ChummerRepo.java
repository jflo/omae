package org.chummer.omae.users;

import java.util.List;

public interface ChummerRepo {

	public List<String> getAllChummNames();
	public Chummer getChummer(String ldapDN);
	public void newChummer(Chummer chum);

}
