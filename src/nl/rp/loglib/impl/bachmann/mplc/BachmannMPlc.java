package nl.rp.loglib.impl.bachmann.mplc;

import nl.rp.loglib.impl.codesys.v2.CoDeSysV2;

public class BachmannMPlc extends CoDeSysV2 {


	public BachmannMPlc() {

	}

	@Override
	protected String getOutputDirectory() {
		return "bachmann/mplc";
	}

}
