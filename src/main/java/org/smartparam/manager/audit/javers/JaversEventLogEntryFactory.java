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
import org.smartparam.manager.authz.Action;
import org.smartparam.manager.audit.EventDescription;
import org.smartparam.manager.audit.EventLogEntry;
import org.smartparam.manager.audit.EventLogEntryFactory;

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
    public EventLogEntry produceParameterCreationLog(EventDescription description, Parameter initialState) {
        Javers javers = JaversBuilder.javers().registerEntity(JaversEventLogEntry.class)
                .registerValueObject(ParameterKey.class, ParameterEntryKey.class, RepositoryName.class)
                .build();
        Diff diff = javers.compare("someone", null, initialState);
        // how to serialize diff? is serialized diff eligalbe for persistence?
        // i can provide serializer (Jackson, Gson) with configuration to serialize Value Objects
        String serializedDiff = "diff.serialize(Serializer ?)";

        return JaversEventLogEntry.parameterEvent(new Date().getTime(), description, Action.CREATE_PARAMETER, diff, serializedDiff);
    }

    public EventLogEntry produceParameterChangeLog(EventDescription description, Action action, Parameter previousState, Parameter currentState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventLogEntry produceParameterDeletionLog(EventDescription description, Parameter lastState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventLogEntry produceEntryCreationLog(EventDescription description, ParameterEntryKey entryKey, ParameterEntry initialState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventLogEntry produceEntryChangeLog(EventDescription description, ParameterEntryKey entryKey, ParameterEntry previousState, ParameterEntry currentState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventLogEntry produceEntryDeletionLog(EventDescription description, ParameterEntryKey entryKey, ParameterEntry lastState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}