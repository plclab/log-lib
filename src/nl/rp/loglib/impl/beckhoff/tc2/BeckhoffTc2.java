package nl.rp.loglib.impl.beckhoff.tc2;

import nl.rp.loglib.impl.codesys.v2.CoDeSysV2;

public class BeckhoffTc2 extends CoDeSysV2 {


	public BeckhoffTc2() {

	}

	@Override
	protected String getOutputDirectory() {
		return "beckhoff/tc2";
	}

}
