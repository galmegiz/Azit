package com.sun.Azit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = 1364802378L;

    public static final QEvent event = new QEvent("event");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final StringPath hashtag = createString("hashtag");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> peopleLimit = createNumber("peopleLimit", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> recruitDeadline = createDateTime("recruitDeadline", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public final EnumPath<com.sun.Azit.constant.Estatus> status = createEnum("status", com.sun.Azit.constant.Estatus.class);

    public final StringPath summary = createString("summary");

    public final StringPath title = createString("title");

    public final StringPath titleTag = createString("titleTag");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

