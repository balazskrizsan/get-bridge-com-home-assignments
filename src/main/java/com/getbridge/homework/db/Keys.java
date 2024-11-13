/*
 * This file is generated by jOOQ.
 */
package com.getbridge.homework.db;


import com.getbridge.homework.db.tables.FlywaySchemaHistory;
import com.getbridge.homework.db.tables.OneOnOnes;
import com.getbridge.homework.db.tables.Participants;
import com.getbridge.homework.db.tables.records.FlywaySchemaHistoryRecord;
import com.getbridge.homework.db.tables.records.OneOnOnesRecord;
import com.getbridge.homework.db.tables.records.ParticipantsRecord;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<OneOnOnesRecord> PK_ONE_ON_ONES_ID = Internal.createUniqueKey(OneOnOnes.ONE_ON_ONES, DSL.name("pk_one_on_ones_id"), new TableField[] { OneOnOnes.ONE_ON_ONES.ID }, true);
    public static final UniqueKey<ParticipantsRecord> PK___ONE_ON_ONES_ID___EMPLOYEE_ID = Internal.createUniqueKey(Participants.PARTICIPANTS, DSL.name("pk___one_on_ones_id___employee_id"), new TableField[] { Participants.PARTICIPANTS.ONE_ON_ONES_ID, Participants.PARTICIPANTS.EMPLOYEE_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ParticipantsRecord, OneOnOnesRecord> PARTICIPANTS__FK___ONE_ON_ONES_ID___ONE_ON_ONES__ID = Internal.createForeignKey(Participants.PARTICIPANTS, DSL.name("fk___one_on_ones_id___one_on_ones__id"), new TableField[] { Participants.PARTICIPANTS.ONE_ON_ONES_ID }, Keys.PK_ONE_ON_ONES_ID, new TableField[] { OneOnOnes.ONE_ON_ONES.ID }, true);
}
