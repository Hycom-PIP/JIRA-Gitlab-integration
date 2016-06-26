package pl.hycom.jira.plugins.gitlab.integration.ao;
/*
 * <p>Copyright (c) 2016, Authors
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

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;


@Service
public interface GitlabComManDao
{
    public void updateGitlabProjectId(int jiraProjectId) throws SQLException;
    /*
    GitlabComEntity getProject(String projectName);
    public List<GitlabComEntity> getAllProjects();
    public boolean findProject(String insertedProject);
    */
}
