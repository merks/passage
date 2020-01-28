/*******************************************************************************
 * Copyright (c) 2018, 2020 ArSysOp
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
package org.eclipse.passage.lic.internal.equinox.access;

import static org.eclipse.passage.lic.base.LicensingProperties.LICENSING_CONDITION_TYPE_DESCRIPTION;
import static org.eclipse.passage.lic.base.LicensingProperties.LICENSING_CONDITION_TYPE_ID;
import static org.eclipse.passage.lic.base.LicensingProperties.LICENSING_CONDITION_TYPE_NAME;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.passage.lic.api.access.PermissionEmitter;
import org.eclipse.passage.lic.api.access.PermissionEmitterRegistry;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component
public class EquinoxPermissionEmitterRegistry implements PermissionEmitterRegistry {

	private static final String NAME_UNDEFINED = ""; //$NON-NLS-1$
	private static final String DESCRIPTION_UNDEFINED = ""; //$NON-NLS-1$

	private final Map<String, String> conditionTypeNames = new HashMap<>();
	private final Map<String, String> conditionTypeDescriptions = new HashMap<>();
	private final Map<String, PermissionEmitter> permissionEmitters = new HashMap<>();

	@Reference(cardinality = ReferenceCardinality.MULTIPLE)
	public void bindPermissionEmitter(PermissionEmitter emitter, Map<String, Object> properties) {
		Object conditionType = properties.get(LICENSING_CONDITION_TYPE_ID);
		String type = String.valueOf(conditionType);
		permissionEmitters.put(type, emitter);
		Object conditionTypeName = properties.get(LICENSING_CONDITION_TYPE_NAME);
		if (conditionTypeName != null) {
			conditionTypeNames.put(type, String.valueOf(conditionTypeName));
		}
		Object conditionTypeDescription = properties.get(LICENSING_CONDITION_TYPE_DESCRIPTION);
		if (conditionTypeDescription != null) {
			conditionTypeDescriptions.put(type, String.valueOf(conditionTypeDescription));
		}
	}

	public void unbindPermissionEmitter(PermissionEmitter emitter, Map<String, Object> properties) {
		Object conditionType = properties.get(LICENSING_CONDITION_TYPE_ID);
		String type = String.valueOf(conditionType);
		PermissionEmitter existing = permissionEmitters.remove(type);
		if (emitter == existing) {
			permissionEmitters.remove(type);
		}
	}

	@Override
	public Iterable<String> getSupportedConditionTypes() {
		return Collections.unmodifiableSet(permissionEmitters.keySet());
	}

	@Override
	public String getDefaultConditionType() {
		// TODO configure via property?
		return "<undefined>"; //$NON-NLS-1$
	}

	@Override
	public PermissionEmitter getPermissionEmitter(String conditionType) {
		return permissionEmitters.get(conditionType);
	}

	@Override
	public Iterable<? extends PermissionEmitter> getPermissionEmitters() {
		return permissionEmitters.values();
	}

	@Override
	public String getConditionTypeName(String conditionType) {
		String value = conditionTypeNames.get(conditionType);
		if (value != null) {
			return value;
		}
		return NAME_UNDEFINED;
	}

	@Override
	public String getConditionTypeDescription(String conditionType) {
		String value = conditionTypeDescriptions.get(conditionType);
		if (value != null) {
			return value;
		}
		return DESCRIPTION_UNDEFINED;
	}

}
