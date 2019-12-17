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
package org.eclipse.passage.loc.yars.internal.api.export;

import java.util.Arrays;
import java.util.List;

import org.eclipse.passage.loc.yars.internal.api.ListMedia;

@SuppressWarnings("restriction")
class Csv implements ListMedia<ExportedEntry, String> {

	private final StringBuilder builder;
	private final List<String> header;

	private Csv(StringBuilder builder, List<String> header) {
		this.builder = builder;
		this.header = header;
	}

	Csv(String... header) {
		this(new StringBuilder(), Arrays.asList(header));
	}

	@Override
	public Csv start() {
		builder.delete(0, builder.length());
		header.forEach(facet -> builder.append(facet).append(";")); //$NON-NLS-1$
		return this;
	}

	@Override
	public Csv startNode(ExportedEntry node) {
		builder.append("\n"); //$NON-NLS-1$
		return this;
	}

	@Override
	public Csv inner(String data, String name) {
		builder.append(data).append(";"); //$NON-NLS-1$
		return this;
	}

	@Override
	public String content() {
		return builder.toString();
	}

}