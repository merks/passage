/*******************************************************************************
 * Copyright (c) 2024 ArSysOp
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
package org.eclipse.passage.lic.keys.model.util;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.passage.lic.internal.keys.model.migration.KeysResourceHandler;

/**
 * @generated NOT
 * @since 3.0
 */
public class KeysResourceImpl extends XMIResourceImpl {
	/**
	 * @generated
	 */
	public KeysResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * 
	 * @generated NOT
	 */
	public KeysResourceImpl() {
		super();
	}

	/**
	 * @generated NOT
	 */
	@Override
	protected void init() {
		super.init();
		Map<Object, Object> options = getDefaultLoadOptions();
		options.put(OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		options.put(OPTION_RESOURCE_HANDLER, new KeysResourceHandler());
	}

}
