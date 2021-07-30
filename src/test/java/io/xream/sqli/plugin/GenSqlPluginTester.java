package io.xream.sqli.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.IOException;

/**
 * @Author Sim
 */
public class GenSqlPluginTester {

    public static void main(String[] args) throws IOException, MojoFailureException, MojoExecutionException {
        TestMojo testMojo = new TestMojo();
        testMojo.execute();

    }

}
