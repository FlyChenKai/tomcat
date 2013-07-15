/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.el.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.el.ELProcessor;

import org.junit.Assert;
import org.junit.Test;

import org.apache.el.TesterBeanA;

public class TestCollectionOperations {

    private static final TesterBeanA bean01 = new TesterBeanA();
    private static final TesterBeanA bean02 = new TesterBeanA();
    private static final TesterBeanA bean03 = new TesterBeanA();
    private static final List<TesterBeanA> beans;

    static {
        List<TesterBeanA> list = new ArrayList<>();

        bean01.setValLong(1);
        list.add(bean01);

        bean02.setValLong(2);
        list.add(bean02);

        bean03.setValLong(3);
        list.add(bean03);

        beans = Collections.unmodifiableList(list);
    }


    @Test
    public void testToList01() {
        ELProcessor processor = new ELProcessor();
        Object result = processor.getValue("['a','b','c'].stream().toList()",
                List.class);
        List<String> expected = new ArrayList<>(3);
        expected.add("a");
        expected.add("b");
        expected.add("c");

        Assert.assertEquals(expected, result);
    }


    @Test
    public void testToList02() {
        ELProcessor processor = new ELProcessor();
        String[] src = new String[] { "a", "b", "c" };
        processor.defineBean("src", src);
        Object result = processor.getValue("src.stream().toList()",
                List.class);
        List<String> expected = new ArrayList<>(3);
        expected.add("a");
        expected.add("b");
        expected.add("c");

        Assert.assertEquals(expected, result);
    }


    @Test
    public void testFilter01() {
        ELProcessor processor = new ELProcessor();
        processor.defineBean("beans", beans);
        Object result = processor.getValue(
                "beans.stream().filter(b->b.valLong > 2).toList()",
                List.class);
        List<TesterBeanA> expected = new ArrayList<>(1);
        expected.add(bean03);

        Assert.assertEquals(expected, result);
    }
}
