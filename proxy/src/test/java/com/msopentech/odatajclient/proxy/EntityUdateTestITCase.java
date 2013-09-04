/*
 * Copyright 2013 MS OpenTech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msopentech.odatajclient.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.ConcurrencyInfo;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Message;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.MessageKey;
import com.msopentech.odatajclient.proxy.microsoft.test.odata.services.astoriadefaultservice.types.Order;
import org.junit.Test;

/**
 * This is the unit test class to check entity update operations.
 */
public class EntityUdateTestITCase extends AbstractTest {

    @Test
    public void update() {
        Order order = container.getOrder().get(-9);

        final ConcurrencyInfo ci = order.getConcurrency();
        ci.setToken("XXX");

        container.getCustomer().flush();

        order = container.getOrder().get(-9);
        assertEquals("XXX", order.getConcurrency().getToken());
    }

    @Test
    public void multiKey() {
        final MessageKey key = new MessageKey();
        key.setFromUsername("1");
        key.setMessageId(-10);

        Message message = container.getMessage().get(key);
        assertNotNull(message);

        message.setBody("XXX");

        container.getMessage().flush();

        message = container.getMessage().get(key);
        assertNotNull(message);
        assertEquals("XXX", message.getBody());
    }
}
