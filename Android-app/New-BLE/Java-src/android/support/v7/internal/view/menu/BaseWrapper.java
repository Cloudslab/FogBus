package android.support.v7.internal.view.menu;

class BaseWrapper<T> {
    final T mWrappedObject;

    BaseWrapper(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.mWrappedObject = object;
    }

    public T getWrappedObject() {
        return this.mWrappedObject;
    }
}
