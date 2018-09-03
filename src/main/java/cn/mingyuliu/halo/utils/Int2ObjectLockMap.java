package cn.mingyuliu.halo.utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.concurrent.locks.StampedLock;

/**
 * int-Object
 *
 * @author cuiyt
 * @date 2018年2月5日
 */
public class Int2ObjectLockMap<V> extends Int2ObjectOpenHashMap<V>{
    private final StampedLock sl = new StampedLock();

    public Int2ObjectLockMap(int expected) {
        super(expected);
    }

    @Override
    public V get(int key) {
        long stamp = sl.tryOptimisticRead();
        V obj = super.get(key);
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                obj = super.get(key);
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return obj;
    }

    @Override
    public V put(int key, V value) {
        long stamp = sl.writeLock();
        try {
            return super.put(key, value);
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    @Override
    public V remove(int key) {
        long stamp = sl.writeLock();
        try {
            return super.remove(key);
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    /**
     * Removes all of the mappings from this map, 不会重新构建map
     */
    @Override
    public void clear() {
        long stamp = sl.writeLock();
        try {
            super.clear();
        } finally {
            sl.unlockWrite(stamp);
        }
    }

}
