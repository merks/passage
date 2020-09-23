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
package org.eclipse.passage.lic.internal.equinox.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.passage.lic.internal.api.ServiceInvocationResult;
import org.eclipse.passage.lic.internal.api.diagnostic.Trouble;
import org.eclipse.passage.lic.internal.api.requirements.Requirement;
import org.eclipse.passage.lic.internal.base.BaseNamedData;
import org.eclipse.passage.lic.internal.base.BaseServiceInvocationResult;
import org.eclipse.passage.lic.internal.base.SumOfCollections;
import org.eclipse.passage.lic.internal.base.diagnostic.code.ServiceCannotOperate;
import org.eclipse.passage.lic.internal.equinox.i18n.AccessMessages;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;

/**
 * Looks for {@linkplain Requirement} declarations among all the
 * licensing-namespaced {@code Capabilities} in the given {@code Bundle}
 * 
 * @see RequirementFromCapability
 * @see BundleRequirements
 */
@SuppressWarnings("restriction")
final class RequirementsFromBundle extends BaseNamedData<ServiceInvocationResult<Collection<Requirement>>> {

	public RequirementsFromBundle(Bundle bundle) {
		super(key -> new FromBundle(bundle).read(key));
	}

	@Override
	public String key() {
		return new LicCapabilityNamespace().get();
	}

	private final static class FromBundle {

		private final Bundle bundle;

		FromBundle(Bundle bundle) {
			this.bundle = bundle;
		}

		ServiceInvocationResult<Collection<Requirement>> read(String key) {
			Optional<BundleWiring> wiring = Optional.ofNullable(bundle.adapt(BundleWiring.class));
			if (!wiring.isPresent()) {
				return fromManifest(AccessMessages.RequirementsFromBundle_no_wiring, key);
			}
			Optional<List<BundleCapability>> capabilities = Optional.ofNullable(wiring.get().getCapabilities(key));
			if (!capabilities.isPresent()) {
				return fromManifest(AccessMessages.RequirementsFromBundle_no_capabilities, key);
			}
			return capabilities.get().stream()//
					.map(capability -> new RequirementFromCapability(bundle, capability))//
					.map(RequirementFromCapability::get)//
					.reduce(new BaseServiceInvocationResult.Sum<>(new SumOfCollections<Requirement>()))//
					.orElseGet(BaseServiceInvocationResult<Collection<Requirement>>::new);
		}

		private ServiceInvocationResult<Collection<Requirement>> fromManifest(String why, String key) {
			return new RequirementsFromManifest(bundle, new Trouble(new ServiceCannotOperate(), why), key).get();
		}
	}

}
