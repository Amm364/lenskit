/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2014 LensKit Contributors.  See CONTRIBUTORS.md.
 * Work on LensKit has been funded by the National Science Foundation under
 * grants IIS 05-34939, 08-08692, 08-12148, and 10-17697.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.grouplens.lenskit.specs

import org.grouplens.lenskit.data.pref.PreferenceDomain
import org.grouplens.lenskit.util.test.MiscBuilders
import org.junit.Test

import static org.hamcrest.Matchers.*
import static org.junit.Assert.assertThat

class SpecificationContextTest {
    @Test
    public void testEmpty() {
        def context = SpecificationContext.create()
        def spec = MiscBuilders.configObj {
            type "hash"
        }
        def map = context.buildWithHandler(MockConfigurator, spec)
        assertThat map, notNullValue()
        assertThat map.keySet(), hasSize(0)
    }

    @Test
    public void testValue() {
        def context = SpecificationContext.create()
        def spec = MiscBuilders.configObj {
            type "hash"
            animal "wombat"
        }
        def map = context.buildWithHandler(MockConfigurator, spec)
        assertThat map, notNullValue()
        assertThat map, hasEntry("animal", "wombat")
    }

    @Test
    public void testConfigureDomainByBuilder() {
        def context = SpecificationContext.create()
        def spec = MiscBuilders.configObj {
            minimum (-1.0)
            maximum 1.0
        }
        def domain = context.build(PreferenceDomain, spec)
        assertThat(domain, notNullValue())
        assertThat(domain, instanceOf(PreferenceDomain))
        assertThat(domain.getMinimum(), equalTo(-1.0d))
        assertThat(domain.getMaximum(), equalTo(1.0d))
        assertThat(domain.getPrecision(), equalTo(0.0d))
    }
}
