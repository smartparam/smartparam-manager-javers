/*
 * Copyright 2014 Adam Dubiel.
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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import org.javers.core.json.JsonTypeAdapter;
import org.smartparam.engine.matchers.type.Range;
import org.smartparam.manager.json.vendor.gson.RangeSerializer;

/**
 *
 * @author Adam Dubiel
 */
public class RangeTypeAdapter implements JsonTypeAdapter<Range<?>> {

    private final RangeSerializer rangeSerializer = new RangeSerializer();

    @Override
    public Range<?> fromJson(JsonElement json, JsonDeserializationContext jsonDeserializationContext) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public JsonElement toJson(Range<?> sourceValue, JsonSerializationContext jsonSerializationContext) {
        return rangeSerializer.serialize(sourceValue, null, jsonSerializationContext);
    }

    @Override
    public Class getValueType() {
        return Range.class;
    }

}
