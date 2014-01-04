/*
 * Copyright 2014 Adam Dubiel, Przemek Hertel.
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
package org.smartparam.manager.audit.javers;

import java.util.Date;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.model.domain.Diff;
import org.smartparam.editor.identity.RepositoryName;
import org.smartparam.editor.model.ParameterEntryKey;
import org.smartparam.editor.model.ParameterKey;
import org.smartparam.engine.core.parameter.Parameter;
import org.smartparam.engine.core.parameter.ParameterEntry;
import org.smartparam.manager.Action;
import org.smartparam.manager.audit.EventLogEntry;
import org.smartparam.manager.audit.EventLogEntryFactory;
import org.smartparam.manager.authz.UserProfile;

/**
 *
 * @author Adam Dubiel
 */
public class JaversEventLogEntryFactory implements EventLogEntryFactory {

    @Override
    public Class<? extends EventLogEntry> produces() {
        return JaversEventLogEntry.class;
    }

    @Override
    public EventLogEntry produceParameterCreationLog(UserProfile responsible, RepositoryName repository, ParameterKey key, Parameter initialState) {
        Javers javers = JaversBuilder.javers().registerEntity(JaversEventLogEntry.class)
                .registerValueObject(ParameterKey.class, ParameterEntryKey.class, RepositoryName.class)
                .build();
        Diff diff = javers.compare("someone", null, initialState);
        // how to serialize diff? is serialized diff eligalbe for persistence?
        // i can provide serializer (Jackson, Gson) with configuration to serialize Value Objects
        String serializedDiff = "diff.serialize(Serializer ?)";

        return JaversEventLogEntry.parameterEvent(new Date().getTime(), repository, Action.CREATE_PARAMETER, responsible.login(), key,
                diff, serializedDiff);
    }

    @Override
    public EventLogEntry produceParameterChangeLog(UserProfile responsible, Action action, RepositoryName repository, ParameterKey key, Parameter previousState, Parameter currentState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceParameterDeletionLog(UserProfile responsible, RepositoryName repository, ParameterKey key, Parameter lastState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceEntryCreationLog(UserProfile responsible, RepositoryName repository, ParameterKey key, ParameterEntryKey entryKey, ParameterEntry initialState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceEntryChangeLog(UserProfile responsible, RepositoryName repository, ParameterKey key, ParameterEntryKey entryKey, ParameterEntry previousState, ParameterEntry currentState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceEntryDeletionLog(UserProfile responsible, RepositoryName repository, ParameterKey key, ParameterEntryKey entryKey, ParameterEntry lastState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
