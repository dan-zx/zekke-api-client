package com.github.danzx.zekke.client.query.impl.http.rx;

import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.Coordinates;
import com.github.danzx.zekke.client.query.ManyNearWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;
import com.github.danzx.zekke.client.query.impl.http.ManyNearWaypointsHttpQuery;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import java.util.List;
import java.util.concurrent.Callable;

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
                    List<W> result = httpQuery.get();
                    return Observable.fromIterable(result);
                } catch (Exception ex) {
                    return Observable.error(ex);
                }
            }
        });
    }

    @Override
    public ManyNearWaypointsHttpQueryRxWrapper atMostDistance(int distance) {
        httpQuery.atMostDistance(distance);
        return this;
    }

    @Override
    public ManyNearWaypointsHttpQueryRxWrapper limit(int limit) {
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
