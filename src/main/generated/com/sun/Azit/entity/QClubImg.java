package com.sun.Azit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClubImg is a Querydsl query type for ClubImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClubImg extends EntityPathBase<ClubImg> {

    private static final long serialVersionUID = -436857891L;

    public static final QClubImg clubImg = new QClubImg("clubImg");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    public final StringPath oriImgName = createString("oriImgName");

    public QClubImg(String variable) {
        super(ClubImg.class, forVariable(variable));
    }

    public QClubImg(Path<? extends ClubImg> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClubImg(PathMetadata metadata) {
        super(ClubImg.class, metadata);
    }

}

