package io.xream.sqli.plugin;

import io.xream.sqli.plugin.util.OuterJarClassLoader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @Author Sim
 */
public class TestMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}")
    public MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        String jarPath = "file:E:\\x7\\xream\\x7\\demo\\target\\x7-demo-2.10.0.jar";
        ClassLoader cl = OuterJarClassLoader.getLoader(jarPath);
        try {
            cl.loadClass("x7.App");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
