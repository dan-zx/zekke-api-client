package com.github.danzx.zekke.client.api.query;

public interface AuthenticationSelectionQuery<R> {
    Query<R> anonymously();
    Query<R> withAdminPrivileges(String userId, String password);
}
