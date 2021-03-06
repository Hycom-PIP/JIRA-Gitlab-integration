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
package pl.hycom.jira.plugins.gitlab.integration.gitlab.events.model;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
@Data
public class Repository {
    @JsonProperty("name")
    public String name;
    @JsonProperty("url")
    public String url;
    @JsonProperty("description")
    public String description;
    @JsonProperty("homepage")
    public String homepage;
    @JsonProperty("git_http_url")
    public String gitHttpUrl;
    @JsonProperty("git_ssh_url")
    public String gitSshUrl;
    @JsonProperty("visibility_level")
    public Integer visibilityLevel;
}
