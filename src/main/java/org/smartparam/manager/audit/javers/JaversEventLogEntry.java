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

import org.smartparam.editor.identity.RepositoryName;
import org.smartparam.editor.model.ParameterEntryKey;
import org.smartparam.editor.model.ParameterKey;
import org.smartparam.manager.Action;
import org.smartparam.manager.audit.AbstractEventLogEntry;

/**
 * Generic parameter should be something like Diff? Or other representation of change set.
 * SerializedDiff is serialized diff.
 *
 * @author Adam Dubiel
 */
public class JaversEventLogEntry extends AbstractEventLogEntry<Object> {

    static JaversEventLogEntry parameterEvent(long timestamp,
            RepositoryName repository, Action action, String responsibleLogin,
            ParameterKey parameterKey,
            Object stateDiff, String serializedDiff) {
        return new JaversEventLogEntry(timestamp, repository, action, responsibleLogin, parameterKey, stateDiff, serializedDiff);
    }

    static JaversEventLogEntry entryEvent(long timestamp,
            RepositoryName repository, Action action, String responsibleLogin,
            ParameterKey parameterKey, ParameterEntryKey parameterEntryKey,
            Object stateDiff, String serializedDiff) {
        return new JaversEventLogEntry(timestamp, repository, action, responsibleLogin, parameterKey, parameterEntryKey, stateDiff, serializedDiff);
    }

    private JaversEventLogEntry(long timestamp,
            RepositoryName repository, Action action, String responsibleLogin,
            ParameterKey parameterKey,
            Object stateDiff, String serializedDiff) {
        super(timestamp, repository, action, responsibleLogin, parameterKey, stateDiff, serializedDiff);
    }

    private JaversEventLogEntry(long timestamp,
            RepositoryName repository, Action action, String responsibleLogin,
            ParameterKey parameterKey, ParameterEntryKey entryKey,
            Object stateDiff, String serializedDiff) {
        super(timestamp, repository, action, responsibleLogin, parameterKey, entryKey, stateDiff, serializedDiff);
    }

}
