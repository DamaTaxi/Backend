package com.example.damataxi.domain.taxiPot.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaxiPot is a Querydsl query type for TaxiPot
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxiPot extends EntityPathBase<TaxiPot> {

    private static final long serialVersionUID = -667584576L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTaxiPot taxiPot = new QTaxiPot("taxiPot");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final com.example.damataxi.domain.auth.domain.QUser creator;

    public final NumberPath<Double> destinationLatitude = createNumber("destinationLatitude", Double.class);

    public final NumberPath<Double> destinationLongitude = createNumber("destinationLongitude", Double.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> meetingAt = createDateTime("meetingAt", java.time.LocalDateTime.class);

    public final StringPath place = createString("place");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<Reservation, QReservation> reservations = this.<Reservation, QReservation>createList("reservations", Reservation.class, QReservation.class, PathInits.DIRECT2);

    public final EnumPath<TaxiPotTarget> target = createEnum("target", TaxiPotTarget.class);

    public QTaxiPot(String variable) {
        this(TaxiPot.class, forVariable(variable), INITS);
    }

    public QTaxiPot(Path<? extends TaxiPot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTaxiPot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTaxiPot(PathMetadata metadata, PathInits inits) {
        this(TaxiPot.class, metadata, inits);
    }

    public QTaxiPot(Class<? extends TaxiPot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new com.example.damataxi.domain.auth.domain.QUser(forProperty("creator"), inits.get("creator")) : null;
    }

}

