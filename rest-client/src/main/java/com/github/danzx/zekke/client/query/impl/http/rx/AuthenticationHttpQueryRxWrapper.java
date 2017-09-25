package com.github.danzx.zekke.client.query.impl.http.rx;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;
import com.github.danzx.zekke.client.query.Query;
import com.github.danzx.zekke.client.query.impl.http.AuthenticationHttpQuery;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import java.util.concurrent.Callable;

public class AuthenticationHttpQueryRxWrapper implements Query<Observable<AccessTokenHolder>> {

    private final AuthenticationHttpQuery httpQuery;

    public AuthenticationHttpQueryRxWrapper(AuthenticationHttpQuery httpQuery) {
        this.httpQuery = httpQuery;
    }

    @Override
    public Observable<AccessTokenHolder> get() {
        return Observable.defer(new Callable<ObservableSource<? extends AccessTokenHolder>>() {
            @Override
            public ObservableSource<? extends AccessTokenHolder> call() throws Exception {
                try {
                    AccessTokenHolder result = httpQuery.get();
                    return Observable.just(result);
                } catch (Exception ex) {
                    return Observable.error(ex);
                }
            }
        });
    }
}
