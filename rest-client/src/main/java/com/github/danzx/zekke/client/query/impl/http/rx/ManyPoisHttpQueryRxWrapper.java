package com.github.danzx.zekke.client.query.impl.http.rx;

import com.github.danzx.zekke.client.core.model.BoundingBox;
import com.github.danzx.zekke.client.core.model.Poi;
import com.github.danzx.zekke.client.query.ManyPoisOptions;
import com.github.danzx.zekke.client.query.Query;
import com.github.danzx.zekke.client.query.impl.http.ManyPoisHttpQuery;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

public class ManyPoisHttpQueryRxWrapper implements ManyPoisOptions, Query<Observable<Poi>> {

    private final ManyPoisHttpQuery httpQuery;

    public ManyPoisHttpQueryRxWrapper(ManyPoisHttpQuery httpQuery) {
        this.httpQuery = httpQuery;
    }

    @Override
    public Observable<Poi> get() {
        return Observable.defer(new Callable<ObservableSource<? extends Poi>>() {
            @Override
            public ObservableSource<? extends Poi> call() throws Exception {
                try {
                    return Observable.fromIterable(httpQuery.get());
                } catch (Exception ex) {
                    return Observable.error(ex);
                }
            }
        });
    }

    @Override
    public ManyPoisHttpQueryRxWrapper enclosedInRectangle(BoundingBox bbox) {
        httpQuery.enclosedInRectangle(bbox);
        return this;
    }

    @Override
    public ManyPoisHttpQueryRxWrapper forSuggestions() {
        httpQuery.forSuggestions();
        return this;
    }

    @Override
    public ManyPoisHttpQueryRxWrapper complete() {
        httpQuery.complete();
        return this;
    }

    @Override
    public ManyPoisHttpQueryRxWrapper withNameContainig(String nameQuery) {
        httpQuery.withNameContainig(nameQuery);
        return this;
    }

    @Override
    public boolean arePoisComplete() {
        return httpQuery.arePoisComplete();
    }

    @Override
    public String getNameQuery() {
        return httpQuery.getNameQuery();
    }

    @Override
    public BoundingBox getBbox() {
        return httpQuery.getBbox();
    }
}
