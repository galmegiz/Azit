package com.sun.Azit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventMember is a Querydsl query type for EventMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventMember extends EntityPathBase<EventMember> {

    private static final long serialVersionUID = 234197636L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventMember eventMember = new QEventMember("eventMember");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final EnumPath<com.sun.Azit.constant.Role> memberRole = createEnum("memberRole", com.sun.Azit.constant.Role.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final EnumPath<com.sun.Azit.constant.PaymentStatus> paymentStatus = createEnum("paymentStatus", com.sun.Azit.constant.PaymentStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QEventMember(String variable) {
        this(EventMember.class, forVariable(variable), INITS);
    }

    public QEventMember(Path<? extends EventMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventMember(PathMetadata metadata, PathInits inits) {
        this(EventMember.class, metadata, inits);
    }

    public QEventMember(Class<? extends EventMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event"), inits.get("event")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

