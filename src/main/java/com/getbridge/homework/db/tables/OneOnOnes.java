/*
 * This file is generated by jOOQ.
 */
package com.getbridge.homework.db.tables;


import com.getbridge.homework.db.Keys;
import com.getbridge.homework.db.Public;
import com.getbridge.homework.db.tables.records.OneOnOnesRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function8;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row8;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.time.LocalDateTime;
import java.util.function.Function;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OneOnOnes extends TableImpl<OneOnOnesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.one_on_ones</code>
     */
    public static final OneOnOnes ONE_ON_ONES = new OneOnOnes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OneOnOnesRecord> getRecordType() {
        return OneOnOnesRecord.class;
    }

    /**
     * The column <code>public.one_on_ones.id</code>.
     */
    public final TableField<OneOnOnesRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.one_on_ones.title</code>.
     */
    public final TableField<OneOnOnesRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.one_on_ones.description</code>.
     */
    public final TableField<OneOnOnesRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.one_on_ones.planned_date</code>.
     */
    public final TableField<OneOnOnesRecord, LocalDateTime> PLANNED_DATE = createField(DSL.name("planned_date"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    /**
     * The column <code>public.one_on_ones.location</code>.
     */
    public final TableField<OneOnOnesRecord, String> LOCATION = createField(DSL.name("location"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.one_on_ones.created_at</code>.
     */
    public final TableField<OneOnOnesRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.one_on_ones.updated_at</code>.
     */
    public final TableField<OneOnOnesRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.one_on_ones.conclude</code>.
     */
    public final TableField<OneOnOnesRecord, LocalDateTime> CONCLUDE = createField(DSL.name("conclude"), SQLDataType.LOCALDATETIME(6), this, "");

    private OneOnOnes(Name alias, Table<OneOnOnesRecord> aliased) {
        this(alias, aliased, null);
    }

    private OneOnOnes(Name alias, Table<OneOnOnesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.one_on_ones</code> table reference
     */
    public OneOnOnes(String alias) {
        this(DSL.name(alias), ONE_ON_ONES);
    }

    /**
     * Create an aliased <code>public.one_on_ones</code> table reference
     */
    public OneOnOnes(Name alias) {
        this(alias, ONE_ON_ONES);
    }

    /**
     * Create a <code>public.one_on_ones</code> table reference
     */
    public OneOnOnes() {
        this(DSL.name("one_on_ones"), null);
    }

    public <O extends Record> OneOnOnes(Table<O> child, ForeignKey<O, OneOnOnesRecord> key) {
        super(child, key, ONE_ON_ONES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<OneOnOnesRecord, Long> getIdentity() {
        return (Identity<OneOnOnesRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<OneOnOnesRecord> getPrimaryKey() {
        return Keys.PK_ONE_ON_ONES_ID;
    }

    @Override
    public OneOnOnes as(String alias) {
        return new OneOnOnes(DSL.name(alias), this);
    }

    @Override
    public OneOnOnes as(Name alias) {
        return new OneOnOnes(alias, this);
    }

    @Override
    public OneOnOnes as(Table<?> alias) {
        return new OneOnOnes(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public OneOnOnes rename(String name) {
        return new OneOnOnes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OneOnOnes rename(Name name) {
        return new OneOnOnes(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public OneOnOnes rename(Table<?> name) {
        return new OneOnOnes(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row8<Long, String, String, LocalDateTime, String, LocalDateTime, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function8<? super Long, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function8<? super Long, ? super String, ? super String, ? super LocalDateTime, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
