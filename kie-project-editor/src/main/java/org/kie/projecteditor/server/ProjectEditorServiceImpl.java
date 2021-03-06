/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.projecteditor.server;

import org.jboss.errai.bus.server.annotations.Service;
import org.kie.projecteditor.shared.model.KProjectModel;
import org.kie.projecteditor.shared.service.ProjectEditorService;
import org.uberfire.backend.vfs.Path;

import javax.enterprise.context.ApplicationScoped;

@Service
@ApplicationScoped
public class ProjectEditorServiceImpl
        implements ProjectEditorService {


    @Override
    public void save(KProjectModel model) {
        //TODO -Rikkola-
    }

    @Override
    public KProjectModel load(Path path) {
        KProjectModel kProjectModel = ProjectEditorContentHandler.toModel(KProjectLoader.load());
        return kProjectModel;
    }

}
