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
package org.eclipse.passage.lic.products.migration.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.passage.lic.products.model.api.Product;
import org.eclipse.passage.lic.products.model.api.ProductLine;
import org.eclipse.passage.lic.products.model.api.ProductVersion;
import org.eclipse.passage.lic.products.model.api.ProductVersionFeature;
import org.junit.Test;

public class ProductsMigratorTest {

	@Test
	public void testMigratorPositive() throws Exception {
		File legacy = new File(System.getProperty("user.dir") + File.separator + "model/org.eclipse.passage.lic.lic_products");
		URI uri = URI.createFileURI(legacy.getPath());
		Resource resource = new XMIResourceImpl(uri);
		resource.load(null);
		EList<EObject> contents = resource.getContents();
		EObject eObject = contents.get(0);

		ProductLine productLine = ProductLine.class.cast(eObject);
		assertEquals("org.eclipse.passage.lic", productLine.getIdentifier());
		assertEquals("Eclipse Passage LIC", productLine.getName());
		assertEquals("Eclipse Passage Licensing Integration Components", productLine.getDescription());

		EList<Product> products = productLine.getProducts();
		assertEquals(1, products.size());

		Product p0 = products.get(0);
		assertEquals("org.eclipse.passage.lic.product", p0.getIdentifier());
		assertEquals("Eclipse Passage LIC", p0.getName());
		assertEquals("Eclipse Passage Licensing Integration Components product", p0.getDescription());
			
		EList<ProductVersion> p0vs = p0.getProductVersions();
		assertEquals(2, p0vs.size());
		ProductVersion p0v0 = p0vs.get(0);
		assertEquals("0.3.3", p0v0.getVersion());
		assertEquals("./.passage/org.eclipse.passage.lic.product/0.3.3/org.eclipse.passage.lic.product_0.3.3.pub", p0v0.getInstallationToken());
		assertEquals("./.passage/org.eclipse.passage.lic.product/0.3.3/org.eclipse.passage.lic.product_0.3.3.scr", p0v0.getSecureToken());
		EList<ProductVersionFeature> p0v0pvfs = p0v0.getProductVersionFeatures();
		assertEquals(1, p0v0pvfs.size());
		ProductVersionFeature p0v0pvf0 = p0v0pvfs.get(0);
		assertEquals("org.eclipse.passage.lic.launch", p0v0pvf0.getFeatureIdentifier());
		assertEquals("0.3.3", p0v0pvf0.getFeatureVersion());
		assertEquals("error", p0v0pvf0.getRestrictionLevel());

		ProductVersion p0v1 = p0vs.get(1);
		assertEquals("0.4.0", p0v1.getVersion());
		assertEquals("./.passage/org.eclipse.passage.lic.product/0.4.0/org.eclipse.passage.lic.product_0.4.0.pub", p0v1.getInstallationToken());
		assertEquals("./.passage/org.eclipse.passage.lic.product/0.4.0/org.eclipse.passage.lic.product_0.4.0.scr", p0v1.getSecureToken());
		EList<ProductVersionFeature> p1v0pvfs = p0v1.getProductVersionFeatures();
		assertEquals(1, p1v0pvfs.size());
		ProductVersionFeature p1v0pvf0 = p1v0pvfs.get(0);
		assertEquals("org.eclipse.passage.lic.product", p1v0pvf0.getFeatureIdentifier());
		assertEquals("0.4.0", p1v0pvf0.getFeatureVersion());
		assertEquals("error", p1v0pvf0.getRestrictionLevel());
	}
}