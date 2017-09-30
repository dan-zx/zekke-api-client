package com.github.danzx.zekke.client;

import static java.util.Objects.requireNonNull;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;

public abstract class BaseZekkeApiClient implements ZekkeApiClient {

    private AccessTokenHolder tokenHolder;

    protected AccessTokenHolder getTokenHolder() {
        return tokenHolder;
    }

    @Override
    public void setAuthenticaton(AccessTokenHolder tokenHolder) {
        this.tokenHolder = requireNonNull(tokenHolder);
    }
}
