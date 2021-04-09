/*******************************************************************************
 * Copyright (c) 2021 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.lic.internal.jetty.interaction;

import org.eclipse.passage.lic.internal.api.ServiceInvocationResult;
import org.eclipse.passage.lic.internal.api.restrictions.ExaminationCertificate;
import org.eclipse.passage.lic.internal.equinox.EquinoxPassageLicenseCoverage;

final class LicenseStatus extends Command {

	public LicenseStatus() {
		super(new Scope.Self(), new String[] { "status" });//$NON-NLS-1$
	}

	public void status() {
		ServiceInvocationResult<ExaminationCertificate> response = new EquinoxPassageLicenseCoverage().assess();
		if (response.data().isPresent()) {
			reportCertificate(response.data().get());
		}
		reportDiagnostic(response.diagnostic());
	}

	private void reportCertificate(ExaminationCertificate certificate) {
		System.out.printf("\n%s\n", new RequirementsLicensingStatusExplained(certificate).get()); //$NON-NLS-1$
	}

}