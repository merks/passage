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
package org.eclipse.passage.lic.runtime.conditions;

import org.eclipse.passage.lic.runtime.LicensingConfiguration;

/**
 * The miner to extract {@link LicensingCondition}(s) from different sources:
 * <li>local file system</li>
 * <li>network server</li>
 * <li>etc</li>
 */
public interface ConditionMiner {

	/**
	 * 
	 * @param configuration
	 * @return
	 */
	Iterable<LicensingCondition> extractLicensingConditions(LicensingConfiguration configuration);

}