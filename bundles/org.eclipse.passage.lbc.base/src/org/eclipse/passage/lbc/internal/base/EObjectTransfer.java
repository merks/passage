/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
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
package org.eclipse.passage.lbc.internal.base;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.passage.lbc.internal.api.FloatingResponse;
import org.eclipse.passage.lic.internal.api.LicensingException;
import org.eclipse.passage.lic.internal.emf.EObjectToBytes;

public final class EObjectTransfer implements FloatingResponse {

	private final EObject payload;

	public EObjectTransfer(EObject payload) {
		this.payload = payload;
	}

	@Override
	public boolean failed() {
		return false;
	}

	@Override
	public boolean carriesPayload() {
		return true;
	}

	@Override
	public Error error() {
		throw new IllegalStateException("Successful response does not possess error information"); //$NON-NLS-1$ // dev
	}

	@Override
	public byte[] payload() throws LicensingException {
		return new EObjectToBytes(payload).get();
	}

}
