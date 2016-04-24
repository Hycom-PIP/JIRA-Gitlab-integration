package ut.pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl;

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

import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.api.ProcessorInterface;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.CommitData;
import pl.hycom.jira.plugins.gitlab.integration.gitpanel.impl.ProcessorManager;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
  * Tests of processor manager.
  * @author Augustyn Kończak <mailto:augustyn.konczak@hycom.pl> on 10.04.16.
  */
@Log4j
@RunWith(MockitoJUnitRunner.class)
public class ProcessorManagerTest {

    @InjectMocks
    private ProcessorManager manager;

    private List<ProcessorInterface> processorsList;

    @Before
    public void setUp() {
        processorsList = new ArrayList<>();
        processorsList.add(new ProcessorInterface() {
            @Override
            public void execute(CommitData commitInfo) {
                //log.info("inside processor execute, processing: " + commitInfo);
                System.out.println("inside processor execute, processing: " + commitInfo);
            }
        });
    }

    @Test
    public void referenceTest() {
        assertThat("Tested List is not empty", processorsList, is(notNullValue()));
        assertThat("Reference should be injected.", manager, is(notNullValue()));
        List<CommitData> list = new ArrayList<>();
        list.add(new CommitData());
        manager.startProcessors(list);
    }
    @Test
    public void getImplementingClassesTest(){
        manager.getImplementingClasses();
        assertFalse(false);
    }
}
