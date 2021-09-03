/*******************************************************************************
 * Copyright (c) 2021 ArSysOp
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
package org.eclipse.passage.lic.internal.jetty;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.passage.lic.internal.jetty.i18n.Messages;
import org.eclipse.passage.lic.internal.net.connect.Port;

public final class JettyServer {

	private final Logger log = LogManager.getLogger(getClass());
	private final Supplier<JettyHandler> handler;
	private Optional<Server> server = Optional.empty();

	public JettyServer(Supplier<JettyHandler> handler) {
		Objects.requireNonNull(handler, "JettyServer::handler"); //$NON-NLS-1$
		this.handler = handler;
	}

	public void launch(Port port) throws JettyException {
		try {
			server = Optional.of(new Server(port.get().get()));
			server.get().setHandler(handler.get());
			server.get().start();
			log.info(String.format(Messages.started, port.get().get()));
		} catch (Exception e) {
			logAndRethrow(e, Messages.error_onstart);
		}
	}

	public void terminate() throws JettyException {
		try {
			if (server.isPresent()) {
				server.get().stop();
			}
			log.info(String.format(Messages.stopped));
		} catch (Exception e) {
			logAndRethrow(e, Messages.error_onstop);
		}
	}

	public String state() throws JettyException {
		try {
			if (!server.isPresent()) {
				return "NOT LAUNCHED"; //$NON-NLS-1$
			}
			return server.get().getState();
		} catch (Exception e) {
			throw new JettyException(Messages.error_onstate, e);
		}
	}

	private void logAndRethrow(Exception e, String template) throws JettyException {
		String message = String.format(template, e.getClass(), e.getMessage());
		log.error(message, e);
		throw new JettyException(message, e);
	}

}
