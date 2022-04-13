package com.company.demo.datasource;

import io.jmix.data.impl.liquibase.JmixLiquibase;
import liquibase.integration.spring.SpringResourceAccessor;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

public class DemoLiquibase extends JmixLiquibase {

    protected Map<String, SortedSet<String>> changelogPaths = new HashMap<>();

    @Override
    protected SpringResourceAccessor createResourceOpener() {
        return new SamplerResourceOpener(resourceLoader);
    }

    public class SamplerResourceOpener extends JmixResourceAccessor {

        public SamplerResourceOpener(ResourceLoader resourceLoader) {
            super(resourceLoader);
        }

        @Override
        public SortedSet<String> list(String relativeTo, String path, boolean includeFiles, boolean includeDirectories,
                                      boolean recursive) throws IOException {
            // Store paths to changelogs to use in session data source
            SortedSet<String> set = changelogPaths.get(path);
            if (set == null) {
                set = super.list(relativeTo, path, includeFiles, includeDirectories, recursive);
                changelogPaths.put(path, set);
            }
            return set;
        }
    }
}
