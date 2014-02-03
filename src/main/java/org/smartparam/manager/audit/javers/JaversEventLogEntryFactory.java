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

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.smartparam.engine.core.output.entry.MapEntry;
import org.smartparam.engine.core.parameter.Parameter;
import org.smartparam.engine.core.parameter.entry.ParameterEntryKey;
import org.smartparam.manager.audit.EventDescription;
import org.smartparam.manager.audit.EventLogEntry;
import org.smartparam.manager.audit.EventLogEntryFactory;
import org.smartparam.manager.authz.Action;

import java.util.Date;
import org.smartparam.engine.matchers.type.Range;
import org.smartparam.engine.matchers.type.RangeBoundary;

/**
 *
 * @author Adam Dubiel
 */
public class JaversEventLogEntryFactory implements EventLogEntryFactory {

    private final Javers javers;

    @Override
    public Class<? extends EventLogEntry> produces() {
        return JaversEventLogEntry.class;
    }

    public JaversEventLogEntryFactory() {
        javers = JaversBuilder.javers()
                .registerValueObject(MapEntry.class)
                .registerValue(Object.class)
                .registerValueTypeAdapter(new RangeTypeAdapter())
                .registerValueTypeAdapter(new StarTypeAdapter())
                .typeSafeValues()
                .build();
    }

    @Override
    public EventLogEntry produceParameterCreationLog(EventDescription description, Parameter initialState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceParameterChangeLog(EventDescription description, Action action, Parameter previousState, Parameter currentState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceParameterDeletionLog(EventDescription description, Parameter lastState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventLogEntry produceEntryCreationLog(EventDescription description, ParameterEntryKey entryKey, MapEntry initialState) {
        Diff diff = javers.initial(description.responsibleLogin(), initialState);
        String json = javers.toJson(diff);
        return JaversEventLogEntry.entryEvent(new Date().getTime(), description, Action.ADD_ENTRY, entryKey, diff, json);
    }

    @Override
    public EventLogEntry produceEntryChangeLog(EventDescription description, ParameterEntryKey entryKey, MapEntry previousState, MapEntry currentState) {
        Diff diff = javers.compare(description.responsibleLogin(), previousState, currentState);
        String json = javers.toJson(diff);
        return JaversEventLogEntry.entryEvent(new Date().getTime(), description, Action.UPDATE_ENTRY, entryKey, diff, json);
    }

    @Override
    public EventLogEntry produceEntryDeletionLog(EventDescription description, ParameterEntryKey entryKey, MapEntry lastState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
