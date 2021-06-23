package com.example.damataxi.domain.taxiPot.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReservationId is a Querydsl query type for ReservationId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QReservationId extends BeanPath<ReservationId> {

    private static final long serialVersionUID = -1108973648L;

    public static final QReservationId reservationId = new QReservationId("reservationId");

    public final NumberPath<Integer> potId = createNumber("potId", Integer.class);

    public final NumberPath<Integer> userGcn = createNumber("userGcn", Integer.class);

    public QReservationId(String variable) {
        super(ReservationId.class, forVariable(variable));
    }

    public QReservationId(Path<? extends ReservationId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReservationId(PathMetadata metadata) {
        super(ReservationId.class, metadata);
    }

}

