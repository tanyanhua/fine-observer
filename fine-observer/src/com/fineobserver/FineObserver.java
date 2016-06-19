package com.fineobserver;

public interface FineObserver<T> {
	public void notify(T t);

	public Class<T> getType();
}
