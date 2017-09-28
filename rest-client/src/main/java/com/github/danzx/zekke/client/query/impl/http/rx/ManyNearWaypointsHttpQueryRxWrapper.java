package com.github.danzx.zekke.client.query.impl.http.rx;

import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.query.ManyNearWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;
import com.github.danzx.zekke.client.query.impl.http.ManyNearWaypointsHttpQuery;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class ManyNearWaypointsHttpQueryRxWrapper<W extends BaseWaypoint> implements ManyNearWaypointsOptions, Query<Observable<W>> {

    private final ManyNearWaypointsHttpQuery<W> httpQuery;

    public ManyNearWaypointsHttpQueryRxWrapper(ManyNearWaypointsHttpQuery<W> httpQuery) {
        this.httpQuery = httpQuery;
    }

    @Override
    public Observable<W> get() {
        return Observable.defer(new Callable<ObservableSource<W>>() {
            @Override
            public ObservableSource<W> call() throws Exception {
                try {
                    return Observable.fromIterable(httpQuery.get());
                } catch (Exception ex) {
                    return Observable.error(ex);
                }
            }
        });
    }

    @Override
    public ManyNearWaypointsHttpQueryRxWrapper<W> atMostDistance(int distance) {
        httpQuery.atMostDistance(distance);
        return this;
    }

    @Override
    public ManyNearWaypointsHttpQueryRxWrapper<W> limit(int limit) {
        httpQuery.limit(limit);
        return this;
    }

    @Override
    public Coordinates getLocation() {
        return httpQuery.getLocation();
    }

    @Override
    public Integer getDistance() {
        return httpQuery.getDistance();
    }

    @Override
    public Integer getLimit() {
        return httpQuery.getLimit();
    }
}
