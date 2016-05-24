﻿package pl.hycom.jira.plugins.gitlab.integration.service.processors;
/*
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;
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
import pl.hycom.jira.plugins.gitlab.integration.exceptions.ProcessException;
import pl.hycom.jira.plugins.gitlab.integration.model.Commit;

import javax.validation.constraints.NotNull;

/**
 * Created by Karol Joachimiak on 08.04.2016.
 */
public interface ProcessorInterface {
void execute(@NotNull Commit commitInfo) throws ProcessException;
}
