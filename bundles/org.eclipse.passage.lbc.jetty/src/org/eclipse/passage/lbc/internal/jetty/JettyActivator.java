/*******************************************************************************
 * Copyright (c) 2020, 2021 ArSysOp
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
package org.eclipse.passage.lbc.internal.jetty;

import java.nio.file.Path;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.passage.lbc.internal.base.EagerFloatingState;
import org.eclipse.passage.lbc.internal.base.FlotingRequestHandled;
import org.eclipse.passage.lbc.internal.base.api.FloatingState;
import org.eclipse.passage.lic.internal.base.logging.Logging;
import org.eclipse.passage.lic.internal.jetty.JettyHandler;
import org.eclipse.passage.lic.internal.jetty.JettyServer;
import org.eclipse.passage.lic.internal.net.connect.Port;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class JettyActivator implements BundleActivator {

	private final JettyServer jetty;
	private final FloatingState state;

	public JettyActivator() {
		configureLogging();
		this.jetty = new JettyServer(this::handler);
		this.state = new EagerFloatingState();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		jetty.launch(new Port(Platform.getApplicationArgs(), 8090));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		jetty.terminate();
	}

	private JettyHandler handler() {
		return new JettyHandler(request -> new FlotingRequestHandled(new StatedRequest(request, state)).get());
	}

	private void configureLogging() {
		new Logging(this::logConfig).configure();
	}

	private Path logConfig() throws Exception {
		return FileLocator.getBundleFile(FrameworkUtil.getBundle(getClass())).toPath()//
				.resolve("config") //$NON-NLS-1$
				.resolve("log4j2.xml"); //$NON-NLS-1$
	}

}
