package nl.rp.loglib.impl.beckhoff.tc3;

import nl.rp.loglib.impl.codesys.v3.CoDeSysV3;

public class BeckhoffTc3 extends CoDeSysV3 {


	public BeckhoffTc3() {

	}

	@Override
	protected String getOutputDirectory() {
		return "beckhoff/tc3";
	}

}
