package com.github.danzx.zekke.client.query.impl.http.rx;

import com.github.danzx.zekke.client.core.model.TypedWaypoint;
import com.github.danzx.zekke.client.query.Query;
import com.github.danzx.zekke.client.query.impl.http.OneWaypointHttpQuery;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class OneWaypointHttpQueryRxWrapper implements Query<Observable<TypedWaypoint>> {

    private final OneWaypointHttpQuery httpQuery;

    public OneWaypointHttpQueryRxWrapper(OneWaypointHttpQuery httpQuery) {
        this.httpQuery = httpQuery;
    }

    @Override
    public Observable<TypedWaypoint> get() {
        return Observable.defer(new Callable<ObservableSource<? extends TypedWaypoint>>() {
            @Override
            public ObservableSource<? extends TypedWaypoint> call() throws Exception {
                try {
                    return Observable.just(httpQuery.get());
                } catch (Exception ex) {
                    return Observable.error(ex);
                }
            }
        });
    }
}
