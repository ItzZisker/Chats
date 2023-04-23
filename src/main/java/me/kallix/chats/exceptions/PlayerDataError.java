package me.kallix.chats.exceptions;

public final class PlayerDataError extends RuntimeException {
    public PlayerDataError(Throwable thr) {
        super("PlayerData exception", thr);
    }
}
