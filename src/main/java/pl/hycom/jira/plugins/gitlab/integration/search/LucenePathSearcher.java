package pl.hycom.jira.plugins.gitlab.integration.search;

/*
 * <p>Copyright (c) 2016, Damian Deska & Kamil Rogowski
 * Project:  gitlab-integration.</p>
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at</p>
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0</p>
 *
 * <p>Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.</p>
 */

import com.atlassian.jira.config.util.IndexPathManager;
import com.sun.istack.NotNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Kamil Rogowski on 31.05.2016.
 */
@Log4j
@Service
public class LucenePathSearcher {

    @Autowired
    private IndexPathManager indexPathManager;
    private Path luceneIndexPath;

    private static final String COMMIT_INDEXER_DIRECTORY = "gitlab-integration";

    @PostConstruct
    public void init() {
        if(getIndexPath() == null){
            throw new NullPointerException("Cannot start plugin, indexPath is NULL");
        } else {
            Path path = Paths.get(indexPathManager.getPluginIndexRootPath(), File.separator, COMMIT_INDEXER_DIRECTORY);
            if(path == null) {
                throw new InvalidPathException(getIndexPathStr(), "Index path doesn't exists");
            }
            luceneIndexPath = path;
        }

        final boolean ifDirectoryExists = luceneIndexPath.toFile().exists();

        if (!ifDirectoryExists) {
            final File file = new File(getIndexPathStr());
            file.mkdir();
        }
    }


    private String getIndexPathStr() {

        return luceneIndexPath.toString();
    }

    public Path getIndexPath(){

        return luceneIndexPath;
    }

}
