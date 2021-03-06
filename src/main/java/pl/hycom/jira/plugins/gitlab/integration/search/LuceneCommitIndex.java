/*
 * <p>Copyright (c) 2016, Authors</p>
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
package pl.hycom.jira.plugins.gitlab.integration.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Inject)) //Inject all final variables.
public class LuceneCommitIndex implements CommitIndex {
    static final Version VERSION_LUCENE = Version.LUCENE_33;

    private final LuceneIndexAccessor indexAccessor;

    @Override
    public void indexFile(Commit commit) throws IOException {
        Document document = CommitMapper.getDocument(commit);
        IndexWriter indexWriter = indexAccessor.getIndexWriter();
        indexWriter.addDocument(document);
    }


    public List<Document> searchCommits(String fieldName, String fieldValue) throws IOException {
        List<Document> foundedCommitsList = new ArrayList<>();
        BooleanQuery query = new BooleanQuery();
        query.add(new TermQuery(new Term(fieldName, fieldValue)), BooleanClause.Occur.MUST);
        IndexSearcher searcher = new IndexSearcher(indexAccessor.getIndexReader());
        TopDocs docs = searcher.search(query, HITS_PER_PAGE);
        ScoreDoc[] hits = docs.scoreDocs;
        for (ScoreDoc hit : hits) {
            int docId = hit.doc;
            Document document = searcher.doc(docId);
            foundedCommitsList.add(document);
        }
        searcher.close();
        return foundedCommitsList;
    }

    @NotNull
    public List<Commit> searchCommitsByIssue(@NotNull String jiraIssueKey) {
        try {
            Query query = new TermQuery(new Term(CommitFields.JIRA_ISSUE_KEY.name(), jiraIssueKey));
            IndexReader reader = indexAccessor.getIndexReader();
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(query, HITS_PER_PAGE);
            return Arrays.stream(docs.scoreDocs).map(hit -> convertToCommit(searcher, hit)).collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("Failed to search commits by JIRA issue key: " + jiraIssueKey + " with message: " + e.getMessage());
            log.debug("Stack: ", e);
        }
        return Collections.emptyList();
    }

    private Commit convertToCommit(IndexSearcher searcher, ScoreDoc hit) {
        try {
            Document document = searcher.doc(hit.doc);
            return CommitMapper.getCommit(document);
        } catch(IOException e) {
            log.warn("Failed to get commit from lucene doc Id: " + hit.doc +" with msg: " + e.getMessage() , e);
            return null;
        }
    }

    public boolean checkIfCommitIsIndexed(String idValue) throws IOException {
        BooleanQuery query = new BooleanQuery();
        /*Query query = new QueryParser(CommitFields.ID.name(), analyzer).parse(idValue);*/
        query.add(new TermQuery(new Term(CommitFields.ID.name(), idValue)), BooleanClause.Occur.SHOULD);
        IndexSearcher searcher = new IndexSearcher(indexAccessor.getIndexReader());
        TopDocs docs = searcher.search(query, HITS_PER_PAGE);
        Optional<ScoreDoc> firstDoc = Arrays.stream(docs.scoreDocs).filter(hit -> {
            try {
                Document document = searcher.doc(hit.doc);
                log.debug("Found: " + document);
                if (idValue.equals(document.get(CommitFields.ID.name()))) {
                    return true;
                }
            } catch (IOException e) {
                log.warn("Failed to search for hit: " + hit.doc + " with message: " + e.getMessage());
            }
            return false;
        }).findFirst();
        return firstDoc.isPresent();
    }

    @Override
    public void clearIndex() throws IOException {
        indexAccessor.getIndexWriter().deleteAll();
        indexAccessor.getIndexWriter().commit();
    }

    @Override
    public void commit() throws IOException {
        indexAccessor.getIndexWriter().commit();
    }
}
