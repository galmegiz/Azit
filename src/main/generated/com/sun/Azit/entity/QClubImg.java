package com.sun.Azit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClubImg is a Querydsl query type for ClubImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubImg extends EntityPathBase<ClubImg> {

    private static final long serialVersionUID = -436857891L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClubImg clubImg = new QClubImg("clubImg");

    public final QClub club;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath oriImgName = createString("oriImgName");

    public final StringPath repImgYn = createString("repImgYn");

    public QClubImg(String variable) {
        this(ClubImg.class, forVariable(variable), INITS);
    }

    public QClubImg(Path<? extends ClubImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClubImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClubImg(PathMetadata metadata, PathInits inits) {
        this(ClubImg.class, metadata, inits);
    }

    public QClubImg(Class<? extends ClubImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.club = inits.isInitialized("club") ? new QClub(forProperty("club"), inits.get("club")) : null;
    }

}

