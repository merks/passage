/*******************************************************************************
 * Copyright (c) 2018-2019 ArSysOp
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
package org.eclipse.passage.lic.registry.users;

import org.eclipse.passage.lic.registry.DescriptorRegistry;

public interface UsersRegistry extends DescriptorRegistry {

	Iterable<? extends UserOriginDescriptor> getUserOrigins();

	UserOriginDescriptor getUserOrigin(String userOriginId);

	void registerUserOrigin(UserOriginDescriptor userOrigin);

	void unregisterUserOrigin(String userOriginId);

	Iterable<? extends UserDescriptor> getUsers();

	Iterable<? extends UserDescriptor> getUsers(String userOriginId);

	UserDescriptor getUser(String userId);

	void registerUser(UserDescriptor user);

	void unregisterUser(String userId);

}
