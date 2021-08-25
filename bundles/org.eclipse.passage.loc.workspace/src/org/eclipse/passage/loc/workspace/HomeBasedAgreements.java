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
package org.eclipse.passage.loc.workspace;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.passage.lic.base.io.LicensingFolder;
import org.eclipse.passage.lic.base.io.UserHomePath;
import org.eclipse.passage.loc.internal.api.workspace.Agreements;
import org.eclipse.passage.loc.internal.api.workspace.ResourceHandle;

final class HomeBasedAgreements implements Agreements {

	private final Path residence;

	HomeBasedAgreements() {
		this.residence = new LicensingFolder(new UserHomePath()).get().resolve("agreements"); //$NON-NLS-1$
	}

	@Override
	public ResourceHandle located(String file) {
		return new LocalFileHandle(residence.resolve(file));
	}

	@Override
	public boolean exists(String file) {
		return Files.exists(residence.resolve(file));
	}

}