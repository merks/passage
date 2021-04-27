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
package org.eclipse.passage.loc.internal.licenses.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.passage.lic.internal.api.LicensedProduct;
import org.eclipse.passage.lic.internal.api.LicensingException;
import org.eclipse.passage.lic.internal.emf.EObjectFromBytes;
import org.eclipse.passage.lic.keys.model.api.KeyPair;
import org.eclipse.passage.loc.internal.api.workspace.Keys;
import org.eclipse.passage.loc.internal.api.workspace.ResourceHandle;
import org.eclipse.passage.loc.internal.equinox.OperatorGearAware;
import org.eclipse.passage.loc.internal.licenses.core.i18n.IssuingMessages;

@SuppressWarnings("restriction")
public final class ProductKeys {

	private final LicensedProduct product;

	ProductKeys(LicensedProduct product) {
		this.product = product;
	}

	InputStream scrStream() throws LicensingException {
		return getStream(KeyPair::getScr);
	}

	InputStream pubStream() throws LicensingException {
		return getStream(KeyPair::getPub);
	}

	byte[] scrBytes() throws LicensingException {
		return getBytes(KeyPair::getScr);
	}

	byte[] pubBytes() throws LicensingException {
		return getBytes(KeyPair::getPub);
	}

	private InputStream getStream(Function<KeyPair, String> get) throws LicensingException {
		return new ByteArrayInputStream(getBytes(get));
	}

	private byte[] getBytes(Function<KeyPair, String> get) throws LicensingException {
		Keys.Smart keys = new Keys.Smart(keys());
		if (!keys.exists(product)) {
			throw new LicensingException(
					String.format(IssuingMessages.ProductKeys_keys_no_storage_for_product, product));
		}
		ResourceHandle source = keys.located(product);
		try {
			KeyPair pair = new EObjectFromBytes<KeyPair>(source.content(), KeyPair.class).get();
			return get.apply(pair).getBytes();
		} catch (Exception e) {
			throw new LicensingException(
					String.format(IssuingMessages.ProductKeys_keys_reading_failed, product, source.info()));
		}
	}

	private Keys keys() throws LicensingException {
		Optional<Keys> keys = new OperatorGearAware().withGear(gear -> Optional.of(gear.workspace().keys()));
		if (!keys.isPresent()) {
			throw new LicensingException(IssuingMessages.ProductKeys_keys_no_service);
		}
		return keys.get();
	}

}
