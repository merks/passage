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
package org.eclipse.passage.lic.json.tests.tobemoved;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.passage.lic.internal.api.conditions.Condition;
import org.eclipse.passage.lic.internal.api.conditions.EvaluationType;
import org.eclipse.passage.lic.internal.base.conditions.BaseCondition;
import org.eclipse.passage.lic.internal.base.conditions.BaseEvaluationInstructions;
import org.eclipse.passage.lic.internal.base.conditions.BaseValidityPeriodClosed;
import org.eclipse.passage.lic.internal.base.conditions.BaseVersionMatch;
import org.eclipse.passage.lic.internal.base.conditions.MatchingRuleCompatible;
import org.eclipse.passage.lic.internal.base.conditions.MatchingRuleEquivalent;
import org.eclipse.passage.lic.internal.base.conditions.MatchingRulePerfect;

@SuppressWarnings("restriction")
public final class TestConditions {

	private final List<Condition> conditions = Arrays.asList(//
			new BaseCondition("who-are-you-guys?", //$NON-NLS-1$
					new BaseVersionMatch("1.1.1", new MatchingRuleEquivalent()), //$NON-NLS-1$
					new BaseValidityPeriodClosed(new Date(), new Date(System.currentTimeMillis() + 1000)), //
					new BaseEvaluationInstructions(new EvaluationType.Of("metal-test"), "cow-says=moo;cat-says=meow")), //$NON-NLS-1$ //$NON-NLS-2$
			new BaseCondition("extraterrestrial-verifier", //$NON-NLS-1$
					new BaseVersionMatch("17.0.8", new MatchingRuleCompatible()), //$NON-NLS-1$
					new BaseValidityPeriodClosed(new Date(), new Date(System.currentTimeMillis() + 2000)), //
					new BaseEvaluationInstructions(new EvaluationType.Of("barrier-body-asessment"), //$NON-NLS-1$
							"heads=1;heands=2;legs=2")), //$NON-NLS-1$
			new BaseCondition("good-witch", //$NON-NLS-1$
					new BaseVersionMatch("0.32.17.patch5", new MatchingRulePerfect()), //$NON-NLS-1$
					new BaseValidityPeriodClosed(new Date(), new Date(System.currentTimeMillis() + 3000)), //
					new BaseEvaluationInstructions(new EvaluationType.Of("physical"), "thermal-conductivity=water")) //$NON-NLS-1$ //$NON-NLS-2$
	);

	Collection<Condition> conditions() {
		return conditions;
	}

}