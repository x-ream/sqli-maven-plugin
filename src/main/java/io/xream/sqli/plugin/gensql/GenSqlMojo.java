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


import io.xream.sqli.plugin.util.ClassFileReader;
import io.xream.sqli.plugin.util.PluginClassLoaderUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Sim
 */
@Mojo(name="gensql",defaultPhase = LifecyclePhase.COMPILE,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class GenSqlMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;


    @Parameter(required = true)
    private List<String> basePackages;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {


        System.out.println("[INFO] ------------------------------------------------------------------------");
        System.out.println("[INFO] Parse bean if contains @X.Key");
        System.out.println("[INFO] Parse bean if contains @GenSql");
        System.out.println("[INFO] ------------------------------------------------------------------------");


        Set<Class<?>> clzzSet = new HashSet<>();
        try {

            ClassLoader clzzLoader = PluginClassLoaderUtil.getClassLoader(this,this.project);

            for (String basePackage : basePackages) {
                clzzSet.addAll(ClassFileReader.getClasses(basePackage,clzzLoader));
            }


        }catch (Exception e) {

        }

        if (clzzSet == null || clzzSet.isEmpty())
            return;

        EntityParser.parse(clzzSet);

        List<Class<?>> clzzList = GenSqlParser.parse(clzzSet);

        if (clzzList == null || clzzList.isEmpty())
            return;

        SqlGeneratorByBuilder.generate(clzzList);

    }

}
