package com.fineobserver;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.util.Log;

public class FineObserverManager {
	private static final String TAG = "FineObserverManager";

	private static final Map<Class<?>, List<WeakReference<FineObserver<?>>>> OBSERVERS = new HashMap<Class<?>, List<WeakReference<FineObserver<?>>>>();

	private static final Handler HANDLER = new Handler();

	public synchronized static <T> void register(FineObserver<T> observer) {
		if (observer == null) {
			return;
		}
		Class<?> clazz = observer.getType();
		List<WeakReference<FineObserver<?>>> observers = OBSERVERS.get(clazz);
		if (observers == null) {
			observers = new ArrayList<WeakReference<FineObserver<?>>>();
			OBSERVERS.put(clazz, observers);
		}
		Iterator<WeakReference<FineObserver<?>>> iterator = observers.iterator();
		WeakReference<FineObserver<?>> weakReference;
		FineObserver<?> observerItem;
		boolean exists = false;
		while (iterator.hasNext()) {
			weakReference = iterator.next();
			observerItem = weakReference.get();
			if (observerItem == null) {
				iterator.remove();
			} else if (!exists) {
				if (observerItem == observer) {
					exists = true;
				}
			}
		}
		if (!exists) {
			observers.add(new WeakReference<FineObserver<?>>(observer));
		}
	}

	public synchronized static <T> void unregister(FineObserver<T> observer) {
		if (observer == null) {
			return;
		}
		Class<?> clazz = observer.getType();
		List<WeakReference<FineObserver<?>>> observers = OBSERVERS.get(clazz);
		if (observers == null) {
			return;
		}
		Iterator<WeakReference<FineObserver<?>>> iterator = observers.iterator();
		WeakReference<FineObserver<?>> weakReference;
		FineObserver<?> observerItem;
		boolean exists = false;
		while (iterator.hasNext()) {
			weakReference = iterator.next();
			observerItem = weakReference.get();
			if (observerItem == null) {
				iterator.remove();
			} else if (!exists) {
				if (observerItem == observer) {
					exists = true;
					iterator.remove();
				}
			}
		}
		if (observers.isEmpty()) {
			OBSERVERS.remove(clazz);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized static <T> void notify(final T t) {
		if (t == null) {
			return;
		}
		Class<?> clazz = t.getClass();
		List<WeakReference<FineObserver<?>>> observers = OBSERVERS.get(clazz);
		if (observers == null) {
			return;
		}
		Iterator<WeakReference<FineObserver<?>>> iterator = observers.iterator();
		FineObserver<T> observerItem;
		while (iterator.hasNext()) {
			final WeakReference<FineObserver<?>> weakReference = iterator.next();
			observerItem = (FineObserver<T>) weakReference.get();
			if (observerItem == null) {
				iterator.remove();
			} else {
				HANDLER.post(new Runnable() {

					@Override
					public void run() {
						FineObserver<T> observer = (FineObserver<T>) weakReference.get();
						if (observer != null) {
							try {
								observer.notify(t);
							} catch (Exception e) {
								Log.e(TAG, "", e);
							}
						}
					}
				});
			}
		}
		if (observers.isEmpty()) {
			OBSERVERS.remove(clazz);
		}
	}

}
