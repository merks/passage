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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.passage.loc.yars.internal.api.FetchParams;
import org.eclipse.passage.loc.yars.internal.api.FetchedData;
import org.eclipse.passage.loc.yars.internal.api.ListMedia;
import org.eclipse.passage.loc.yars.internal.api.Query;
import org.eclipse.passage.loc.yars.internal.api.Storage;
import org.eclipse.passage.loc.yars.internal.api.model.InMemoryStorage;
import org.eclipse.passage.loc.yars.internal.api.model.StoredEntry;
import org.junit.Test;

/**
 * <p>
 * Here we illustrate how to use {@linkplain org.eclipse.passage.loc.yars.api}
 * in order to
 * </p>
 * <ul>
 * <li>fetch something from a {@linkplain Storage},</li>
 * <li>reorganize it to another type and</li>
 * <li>finally end up in a {@code CSV} and {@code JSON} persistence or in a
 * simple <i>enlistment</i> of the result.</li>
 * </ul>
 * 
 * 
 * <p>
 * </p>
 * 
 * <ul>
 * <li>Our {@linkplain Storage} consists of a simple entries of
 * {@linkplain StoredEntry} type - we emulate <i>in memory base</i>.</li>
 * <li>We define a {@linkplain Query} - {@linkplain All} - which fetches all
 * entries from our storage. It also emulates a <i>business logic</i> that is
 * implemented as a conversion stored entries to entities of another type
 * ({@linkplain ExportedEntry})<i></li>
 * <li>When we as our <i>query</i> for {@linkplain Query#data()}, it actually
 * does not interacts with the {@code storage}, but only instantiate a dedicated
 * {@linkplain FetchedData} instance.</li>
 * <li>We implement {@linkplain FetchedData} as {@linkplain Fetch} class. It is,
 * by contract, aware of our particular storage type and knows how to get data
 * from it.</li>
 * <li>We have tree <i>target format definitions</i>: CSV, JSON (strings) and a
 * runtime list. All of them are unaware of storing and fetching details.</li>
 * 
 * <li></li>
 * <li></li>
 * </ul>
 */
@SuppressWarnings("restriction")
public class ExportTest {

	@Test
	public void testCsv() {
		assertEquals("name;\nGammy;\nQuami;\nTsunami;", //$NON-NLS-1$
				queryResult(new Csv("name"))); //$NON-NLS-1$
	}

	@Test
	public void testEnlistment() {
		assertEquals(Arrays.asList( //
				new ExportedEntry("Gammy"), //$NON-NLS-1$
				new ExportedEntry("Quami"), //$NON-NLS-1$
				new ExportedEntry("Tsunami")), //$NON-NLS-1$
				queryResult(new Enlistment<>()));
	}

	@Test
	public void testJson() {
		assertEquals("{\n" + //$NON-NLS-1$
				"\t\"node\" : {\n" + //$NON-NLS-1$
				"\t\t\"name\" : \"Gammy\"\n" + //$NON-NLS-1$
				"\t}\n" + //$NON-NLS-1$
				"\t\"node\" : {\n" + //$NON-NLS-1$
				"\t\t\"name\" : \"Quami\"\n" + //$NON-NLS-1$
				"\t}\n" + //$NON-NLS-1$
				"\t\"node\" : {\n" + //$NON-NLS-1$
				"\t\t\"name\" : \"Tsunami\"\n" + //$NON-NLS-1$
				"\t}\n" + //$NON-NLS-1$
				"}\n", //$NON-NLS-1$
				queryResult(new Json()));
	}

	private <T> T queryResult(ListMedia<ExportedEntry, T> media) {
		new Export(new All().fetch(//
				new InMemoryStorage( //
						new StoredEntry("Gammy", "US"), //$NON-NLS-1$ //$NON-NLS-2$
						new StoredEntry("Quami", "France"), //$NON-NLS-1$ //$NON-NLS-2$
						new StoredEntry("Tsunami", "Japan")//$NON-NLS-1$ //$NON-NLS-2$
				), //
				new FetchParams.Empty()))//
						.write(media);
		return media.content();
	}

}
