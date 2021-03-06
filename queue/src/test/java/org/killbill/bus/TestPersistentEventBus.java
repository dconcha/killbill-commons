/*
 * Copyright 2010-2011 Ning, Inc.
 * Copyright 2015 Groupon, Inc
 * Copyright 2015 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.bus;

import org.killbill.TestSetup;
import org.killbill.bus.api.PersistentBus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestPersistentEventBus extends TestSetup {

    private TestEventBusBase testEventBusBase;
    private PersistentBus busService;

    @Override
    @BeforeClass(groups = "slow")
    public void beforeClass() throws Exception {
        super.beforeClass();
        busService = new DefaultPersistentBus(getDBI(), clock, getPersistentBusConfig(), metricRegistry, databaseTransactionNotificationApi);
        testEventBusBase = new TestEventBusBase(busService);
    }

    @Override
    @BeforeMethod(groups = "slow")
    public void beforeMethod() throws Exception {
        super.beforeMethod();
        busService.start();
    }

    @AfterMethod(groups = "slow")
    public void afterMethod() throws Exception {
        busService.stop();
    }

    @Test(groups = "slow")
    public void testSimple() {
        Assert.assertEquals(busService.getInProcessingBusEvents().size(), 0);
        testEventBusBase.testSimple();
        Assert.assertEquals(busService.getInProcessingBusEvents().size(), 0);
    }

    @Test(groups = "slow")
    public void testSimpleWithExceptionAndRetrySuccess() {
        Assert.assertEquals(busService.getInProcessingBusEvents().size(), 0);
        testEventBusBase.testSimpleWithExceptionAndRetrySuccess();
        Assert.assertEquals(busService.getInProcessingBusEvents().size(), 0);
    }

    @Test(groups = "slow")
    public void testSimpleWithExceptionAndFail() {
        Assert.assertEquals(busService.getInProcessingBusEvents().size(), 0);
        testEventBusBase.testSimpleWithExceptionAndFail();
        Assert.assertEquals(busService.getInProcessingBusEvents().size(), 0);
    }
}
