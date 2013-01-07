/**
 * Copyright (C) 2012 Patrice Brend'amour <p.brendamour@bitzeche.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.brendamour.jpasskit.apns;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsDelegate;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.DeliveryError;

public class PKSendPushNotificationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PKSendPushNotificationUtil.class);
    private static final String EMPTY_PUSH_JSON_STRING = "{}";
    private final ApnsService service;

    public PKSendPushNotificationUtil(final String pathToP12, final String passwordForP12) {
        this(pathToP12, passwordForP12, 10);
    }

    public PKSendPushNotificationUtil(final String pathToP12, final String passwordForP12, final int poolSize) {
        service =
                APNS.newService().withCert(pathToP12, passwordForP12).withProductionDestination()
                        .withDelegate(new ApnsLoggingDelegate()).asPool(poolSize).build();
    }

    public void sendPushNotification(final String pushtoken) {

        LOGGER.debug("Sending Push notification for key: {}", pushtoken);

        service.push(pushtoken, EMPTY_PUSH_JSON_STRING);
        LOGGER.debug("Send Push notification for key: {}", pushtoken);

    }

    public void sendMultiplePushNotifications(final List<String> pushtokens) {

        LOGGER.debug("Sending Push notification for keys: {}", pushtokens);
        service.push(pushtokens, EMPTY_PUSH_JSON_STRING);
        LOGGER.debug("Send Push notification for keys: {}", pushtokens);

    }

    public Map<String, Date> getInactiveDevices() {
        LOGGER.debug("Querying inactive devices");
        Map<String, Date> inactiveDevices = service.getInactiveDevices();
        LOGGER.debug("Inactive devices: {}", inactiveDevices);
        return inactiveDevices;
    }

    class ApnsLoggingDelegate implements ApnsDelegate {

        public void messageSent(final ApnsNotification message) {
            LOGGER.debug("Message sent: {}", message);
        }

        public void messageSendFailed(final ApnsNotification message, final Throwable e) {
            LOGGER.debug("Message failed: {}", message, e);
        }

        public void connectionClosed(final DeliveryError e, final int messageIdentifier) {
            LOGGER.debug("Connection closed: {}", messageIdentifier, e);

        }

    }
}
