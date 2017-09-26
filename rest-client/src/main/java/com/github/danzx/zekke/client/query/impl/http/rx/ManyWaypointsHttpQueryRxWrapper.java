package com.github.danzx.zekke.client.query.impl.http.rx;

import com.github.danzx.zekke.client.core.model.BaseWaypoint;
import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.query.ManyWaypointsOptions;
import com.github.danzx.zekke.client.query.Query;
import com.github.danzx.zekke.client.query.impl.http.ManyWaypointsHttpQuery;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class ManyWaypointsHttpQueryRxWrapper<W extends BaseWaypoint> implements ManyWaypointsOptions, Query<Observable<W>> {

    private final ManyWaypointsHttpQuery<W> httpQuery;

    public ManyWaypointsHttpQueryRxWrapper(ManyWaypointsHttpQuery<W> httpQuery) {
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
    public ManyWaypointsHttpQueryRxWrapper<W> enclosedInRectangle(BoundingBox bbox) {
        httpQuery.enclosedInRectangle(bbox);
        return this;
    }

    @Override
    public BoundingBox getBbox() {
        return httpQuery.getBbox();
    }
}
