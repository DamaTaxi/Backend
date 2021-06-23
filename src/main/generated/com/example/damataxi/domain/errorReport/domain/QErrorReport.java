package com.example.damataxi.domain.errorReport.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorReport is a Querydsl query type for ErrorReport
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QErrorReport extends EntityPathBase<ErrorReport> {

    private static final long serialVersionUID = 662768928L;

    public static final QErrorReport errorReport = new QErrorReport("errorReport");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath title = createString("title");

    public QErrorReport(String variable) {
        super(ErrorReport.class, forVariable(variable));
    }

    public QErrorReport(Path<? extends ErrorReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorReport(PathMetadata metadata) {
        super(ErrorReport.class, metadata);
    }

}

