package org.smartparam.manager.audit.javers;

import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.NewObject;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.map.EntryValueChanged;
import org.javers.core.diff.changetype.map.MapChange;
import org.joda.time.LocalDate;
import org.smartparam.engine.core.index.Star;
import org.smartparam.engine.core.output.entry.MapEntry;
import org.smartparam.engine.core.parameter.entry.ParameterEntryKey;
import org.smartparam.engine.matchers.decoder.Range;
import org.smartparam.manager.audit.EventDescription;
import org.smartparam.manager.audit.EventLogEntry;
import org.smartparam.manager.authz.Action;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author bartosz walacik
 */
public class JaversEventLogEntryFactoryTest {

    @Test
    public void shouldAssembleEventLogForAddEntry() {
        //given
        EventDescription eventDescription = mock(EventDescription.class);
        when(eventDescription.responsibleLogin()).thenReturn("login");
        ParameterEntryKey entryKey = mock(ParameterEntryKey.class);
        MapEntry mapEntry = new MapEntry();
        JaversEventLogEntryFactory factory = new JaversEventLogEntryFactory();

        //when
        EventLogEntry eventLogEntry = factory.produceEntryCreationLog(eventDescription, entryKey, mapEntry);

        //then
        assertThat(eventLogEntry.entryKey()).isSameAs(entryKey);
        assertThat(eventLogEntry.responsibleLogin()).isEqualTo("login");
        assertThat(eventLogEntry.action()).isEqualTo(Action.ADD_ENTRY);
    }

    @Test
    public void shouldCreateDiffForUpdateEntry() {
        //given
        EventDescription eventDescription = mock(EventDescription.class);
        when(eventDescription.responsibleLogin()).thenReturn("login");
        ParameterEntryKey entryKey = mock(ParameterEntryKey.class);
        MapEntry mapEntryOld = new MapEntry();
        mapEntryOld.put("level1", new BigDecimal(2.)) ;
        mapEntryOld.put("level2", new Range(new LocalDate(2000, 1, 1), Star.star()));

        MapEntry mapEntryNew = new MapEntry();
        mapEntryNew.put("level1", new BigDecimal(2.)) ;
        mapEntryNew.put("level2", new Range(new LocalDate(2009, 9, 9), Star.star()));

        JaversEventLogEntryFactory factory = new JaversEventLogEntryFactory();

        //when
        JaversEventLogEntry eventLogEntry =
                (JaversEventLogEntry)factory.produceEntryChangeLog(eventDescription, entryKey, mapEntryOld, mapEntryNew);

        //then
        Diff diff = eventLogEntry.getDiff();
         //uncomment to see json
         //  String serializedDiff = eventLogEntry.getSerializedDiff();
         //  System.out.println("diff\n" + serializedDiff);

        assertThat(diff.getUserId()).isEqualTo("login");
        assertThat(diff.getChanges()).hasSize(1);
        MapChange mapChange = (MapChange)diff.getChanges().get(0);
        assertThat(mapChange.getEntryChanges()).hasSize(1);
        assertThat(mapChange.getEntryChanges().get(0)).isInstanceOf(EntryValueChanged.class);
    }

    @Test
    public void shouldCreateInitialDiffForAddEntry() {
        //given
        EventDescription eventDescription = mock(EventDescription.class);
        when(eventDescription.responsibleLogin()).thenReturn("login");
        ParameterEntryKey entryKey = mock(ParameterEntryKey.class);
        MapEntry mapEntry = new MapEntry();
        mapEntry.put("level1", new BigDecimal(2.)) ;
        mapEntry.put("level2", new LocalDate(2000, 1, 1));
        mapEntry.put("level3", "str");
        mapEntry.put("level4", new Range(new LocalDate(2000, 1, 1), Star.star()));
        JaversEventLogEntryFactory factory = new JaversEventLogEntryFactory();

        //when
        JaversEventLogEntry eventLogEntry =
                (JaversEventLogEntry)factory.produceEntryCreationLog(eventDescription, entryKey, mapEntry);

        //then
        Diff diff = eventLogEntry.getDiff();
         //uncomment to see json
         // String serializedDiff = eventLogEntry.getSerializedDiff();
         // System.out.println("diff\n" + serializedDiff);

        assertThat(diff.getUserId()).isEqualTo("login");
        assertThat(diff.getChanges()).hasSize(3);
        assertThat(diff.getChanges().get(0)).isInstanceOf(NewObject.class);
        assertThat(diff.getChanges().get(1)).isInstanceOf(ValueChange.class); //key
        assertThat(diff.getChanges().get(2)).isInstanceOf(MapChange.class);
        MapChange mapChange = (MapChange)diff.getChanges().get(2);
        assertThat(mapChange.getEntryChanges()).hasSize(4);
    }
}
