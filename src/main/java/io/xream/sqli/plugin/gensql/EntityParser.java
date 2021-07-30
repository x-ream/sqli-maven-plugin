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

import io.xream.sqli.annotation.X;
import io.xream.sqli.parser.Parser;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @Author Sim
 */
public class EntityParser {

    public static void parse(Set<Class<?>> clzzSet) {
        for (Class clzz : clzzSet) {
            Field[] fields = clzz.getDeclaredFields();
            for (Field field : fields) {
                X.Key a = field.getAnnotation(X.Key.class);
                if (a != null) {
                    Parser.parse(clzz);
                    System.out.println("[INFO] Entity: " + clzz.getName());
                }
            }
        }
    }
}
