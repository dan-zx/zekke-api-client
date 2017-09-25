package com.github.danzx.zekke.client;

import com.github.danzx.zekke.client.core.model.AccessTokenHolder;

import static java.util.Objects.requireNonNull;

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
