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
package org.eclipse.passage.lic.equinox.restrictions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.passage.lic.base.restrictions.RestrictionVerdicts;
import org.eclipse.passage.lic.equinox.ApplicationConfigurations;
import org.eclipse.passage.lic.equinox.LicensingEquinox;
import org.eclipse.passage.lic.runtime.LicensingConfiguration;
import org.eclipse.passage.lic.runtime.inspector.FeatureCase;
import org.eclipse.passage.lic.runtime.inspector.FeatureInspector;
import org.eclipse.passage.lic.runtime.requirements.LicensingRequirement;
import org.eclipse.passage.lic.runtime.restrictions.RestrictionVerdict;

public class EquinoxRestrictions {

	public static Iterable<RestrictionVerdict> getFeatureVerdicts(String... featureIds) {
		FeatureInspector featureInspector = LicensingEquinox.getFeatureInspector();
		if (featureInspector == null) {
			LicensingConfiguration configuration = ApplicationConfigurations.getLicensingConfiguration();
			if (featureIds.length == 0) {
				String id = configuration.getProductIdentifier();
				return Collections.singletonList(RestrictionVerdicts.createConfigurationError(id, configuration));
			}
			List<RestrictionVerdict> errors = new ArrayList<>();
			for (String id : featureIds) {
				errors.add(RestrictionVerdicts.createConfigurationError(id, configuration));
			}
			return errors;
		}
		try (FeatureCase inspection = featureInspector.inspectFeatures(featureIds)) {
			return inspection.getRestrictions();
		}
	}

	public static IStatus getRestrictionStatus(Iterable<RestrictionVerdict> verdicts, String featureName) {
		String pluginId = LicensingEquinox.PI_LIC_EQUINOX;
		if (!verdicts.iterator().hasNext()) {
			String message = NLS.bind("Feature \"{0}\" is licensed properly", featureName);
			return new Status(IStatus.OK, pluginId, message);
		}
		String title = "Issues with licensing";
		MultiStatus status = new MultiStatus(pluginId, RestrictionVerdicts.CODE_CONFIGURATION_ERROR, title, null);
		for (RestrictionVerdict verdict : verdicts) {
			LicensingRequirement requirement = verdict.getLicensingRequirement();
			if (requirement != null) {
				featureName = requirement.getFeatureName();
			}
			String message = NLS.bind("Feature \"{0}\" is not licensed properly", featureName);
			status.add(new Status(IStatus.ERROR, pluginId, message));
		}
		return status;
	}

}