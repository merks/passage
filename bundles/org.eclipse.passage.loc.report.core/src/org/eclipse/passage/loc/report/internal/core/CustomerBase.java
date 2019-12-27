/*******************************************************************************
 * Copyright (c) 2019 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.loc.report.internal.core;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.passage.lic.users.UserDescriptor;
import org.eclipse.passage.lic.users.UserLicenseDescriptor;
import org.eclipse.passage.lic.users.registry.UserRegistry;
import org.eclipse.passage.loc.yars.internal.api.Storage;

/**
 * FIXME
 * 
 * @since 0.1
 */
@SuppressWarnings("restriction")
public abstract class CustomerBase implements Storage<UserDescriptor> {
	private final UserRegistry registry;

	protected CustomerBase(UserRegistry registry) {
		this.registry = registry;
	}

	public Set<UserDescriptor> forProducts(Set<String> products) {
		return StreamSupport.stream(registry.getUserLicenses().spliterator(), false)//
				.filter(lic -> products.contains(lic.getProductIdentifier())) //
				.map(UserLicenseDescriptor::getUser) //
				.collect(Collectors.toSet());
	}

}