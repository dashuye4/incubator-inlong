/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.inlong.commons.config.metrics;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * MetricRegister
 */
public class MetricRegister {

    public static final Logger LOGGER = LoggerFactory.getLogger(MetricRegister.class);

    /**
     * register MetricItem
     * 
     * @param obj
     */
    public static void register(MetricItem obj) {
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        StringBuilder beanName = new StringBuilder();
        beanName.append(MetricUtils.getDomain(obj.getClass())).append(MetricItemMBean.DOMAIN_SEPARATOR)
                .append(obj.getDimensionsKey());
        String strBeanName = beanName.toString();
        try {
            ObjectName objName = new ObjectName(strBeanName);
            mbs.registerMBean(obj, objName);
        } catch (Exception ex) {
            LOGGER.error("exception while register mbean:{},error:{}", strBeanName, ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    /**
     * register MetricItemSet
     * 
     * @param obj
     */
    public static void register(MetricItemSet<? extends MetricItem> obj) {
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        StringBuilder beanName = new StringBuilder();
        beanName.append(MetricUtils.getDomain(obj.getClass())).append(MetricItemMBean.DOMAIN_SEPARATOR).append("name=")
                .append(obj.getName());
        String strBeanName = beanName.toString();
        try {
            ObjectName objName = new ObjectName(strBeanName);
            mbs.registerMBean(obj, objName);
        } catch (Exception ex) {
            LOGGER.error("exception while register mbean:{},error:{}", strBeanName, ex.getMessage());
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
