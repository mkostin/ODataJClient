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
package com.msopentech.odatajclient.plugin;

import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataMetadataRequest;
import com.msopentech.odatajclient.engine.communication.request.retrieve.ODataRetrieveRequestFactory;
import com.msopentech.odatajclient.engine.communication.response.ODataRetrieveResponse;
import com.msopentech.odatajclient.engine.data.metadata.EdmMetadata;
import com.msopentech.odatajclient.engine.data.metadata.edm.ComplexType;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityContainer.EntitySet;
import com.msopentech.odatajclient.engine.data.metadata.edm.EntityType;
import com.msopentech.odatajclient.engine.data.metadata.edm.Schema;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

@Mojo(name = "pojos", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MetadataMojo extends AbstractMojo {

    /**
     * Generated files base root.
     */
    @Parameter(property = "outputDirectory", required = true)
    private String outputDirectory;

    /**
     * OData service root URL.
     */
    @Parameter(property = "serviceRootURL", required = true)
    private String serviceRootURL;

    /**
     * Base package.
     */
    @Parameter(property = "basePackage", required = true)
    private String basePackage;

    private Utilities utility = null;

    private static String TOOL_DIR = "ojc-plugin";

    @Override
    public void execute() throws MojoExecutionException {
        if (new File(outputDirectory + "/" + TOOL_DIR).exists()) {
            getLog().info("Nothing to do because " + TOOL_DIR + " directory already exists. Clean to update.");
            return;
        }

        Velocity.addProperty(
                Velocity.RESOURCE_LOADER, "class");
        Velocity.addProperty(
                "class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        final ODataMetadataRequest req = ODataRetrieveRequestFactory.getMetadataRequest(serviceRootURL);

        final ODataRetrieveResponse<EdmMetadata> res = req.execute();
        final EdmMetadata metadata = res.getBody();

        if (metadata == null) {
            throw new IllegalStateException("Metadata not found");
        }

        for (Schema schema : metadata.getSchemas()) {
            utility = new Utilities(metadata, schema, basePackage);

            // write package-info for the base package
            File base = mkdir(utility.getSchemaName());
            String pkg = basePackage + "." + utility.getSchemaName();

            parseObj(base, pkg, "package-info", "package-info.java");

            // write container and top entity sets into the base package
            for (EntityContainer container : schema.getEntityContainers()) {
                parseObj(base, pkg, container, "container", StringUtils.capitalize(container.getName()) + ".java");

                for (EntitySet entitySet : container.getEntitySets()) {
                    parseObj(base, pkg, entitySet, "entitySet", StringUtils.capitalize(entitySet.getName()) + ".java");
                }

                parseObj(base, pkg, container, "asyncContainer",
                        "Async" + StringUtils.capitalize(container.getName()) + ".java");

                for (EntitySet entitySet : container.getEntitySets()) {
                    parseObj(base, pkg, entitySet, "asyncEntitySet",
                            "Async" + StringUtils.capitalize(entitySet.getName()) + ".java");
                }
            }

            // write package-info for types package
            base = mkdir(utility.getSchemaName() + "/types");
            pkg = basePackage + "." + utility.getSchemaName() + ".types";
            parseObj(base, pkg, "package-info", "package-info.java");

            // write types into types package
            for (ComplexType complex : schema.getComplexTypes()) {
                parseObj(base, pkg, complex, "complexType", StringUtils.capitalize(complex.getName()) + ".java");
            }

            // write entities into types package
            for (EntityType entity : schema.getEntityTypes()) {
                parseObj(base, pkg, entity, "entityType", StringUtils.capitalize(entity.getName()) + ".java");
            }
        }
    }

    private File mkdir(final String path) {
        final File dir = new File(outputDirectory + "/" + TOOL_DIR + "/" + basePackage.replace('.', '/') + "/" + path);

        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IllegalArgumentException("Invalid path '" + path + "': it is not a directory");
            }
        } else {
            dir.mkdirs();
        }

        return dir;
    }

    private void writeFile(final String name, final File path, final VelocityContext ctx, final Template template)
            throws MojoExecutionException {

        if (!path.exists()) {
            throw new IllegalArgumentException("Invalid base path '" + path.getAbsolutePath() + "'");
        }

        FileWriter fw = null;

        try {
            final File toBeWritten = new File(path, name);
            if (toBeWritten.exists()) {
                throw new IllegalStateException("File '" + toBeWritten.getAbsolutePath() + "' already exists");
            }
            fw = new FileWriter(toBeWritten);
            template.merge(ctx, fw);
        } catch (IOException e) {
            throw new MojoExecutionException("Error creating file '" + name + "'", e);
        } finally {
            try {
                if (fw != null) {
                    fw.flush();
                    fw.close();
                }
            } catch (IOException ignore) {
                // ignore
            }
        }
    }

    private VelocityContext newContext() {

        final VelocityContext ctx = new VelocityContext();

        ctx.put("utility", utility);
        ctx.put("basePackage", basePackage);
        ctx.put("schemaName", utility.getSchemaName());
        ctx.put("namespace", utility.getNamespace());

        return ctx;
    }

    private void parseObj(final File base, final String pkg, final String name, final String out)
            throws MojoExecutionException {
        parseObj(base, pkg, null, name, out);
    }

    private void parseObj(final File base, final String pkg, final Object obj, final String name, final String out)
            throws MojoExecutionException {
        VelocityContext ctx = newContext();
        ctx.put("package", pkg);

        if (obj != null) {
            ctx.put(name, obj);
        }

        Template template = Velocity.getTemplate(name + ".vm");
        writeFile(out, base, ctx, template);
    }
}
