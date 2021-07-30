/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.xream.sqli.plugin.gensql;

import io.xream.sqli.builder.Criteria;
import io.xream.sqli.test.SqlGenerator;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author Sim
 */
public class SqlGeneratorByBuilder {

    public static void generate(List<Class<?>> clzzList) {
        for (Class clzz : clzzList) {

            String fileName = clzz.getSimpleName();
            try {
                Object obj = clzz.newInstance();

                Method[] arr = clzz.getDeclaredMethods();

                for (Method method : arr) {
                    Class rc = method.getReturnType();
                    if (rc == Criteria.ResultMapCriteria.class) {
                        Object r = method.invoke(obj);
                        Criteria.ResultMapCriteria resultMapCriteria = (Criteria.ResultMapCriteria)r;
                        SqlGenerator.generator().build(fileName + "." + method.getName(),resultMapCriteria);
                    }
                }
                SqlGenerator.generator().generate(fileName);
                System.out.println("[INFO] Generated: " + fileName + ".sql");
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
