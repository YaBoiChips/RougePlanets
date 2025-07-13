package yaboichips.rogue_planets.common.nbt;

import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class NBTList<T, N extends Tag> implements INBTSerializable<ListTag>, List<T> {
    private List<T> delegate;
    private final Function<T, N> write;
    private final Function<N, T> read;

    public NBTList(List<T> list, Function<T, N> write, Function<N, T> read) {
        this.delegate = list;
        this.write = write;
        this.read = read;
    }

    public NBTList(Function<T, N> write, Function<N, T> read) {
        this(new ArrayList<>(), write, read);
    }

    public ListTag serializeNBT() {
        ListTag nbt = new ListTag();
        this.delegate.forEach((value) -> {
            nbt.add(this.write.apply(value));
        });
        return nbt;
    }

    public void deserializeNBT(ListTag nbt) {
        this.delegate.clear();
        nbt.forEach((entry) -> {
            this.add(this.read.apply((N) entry));
        });
    }

    public int size() {
        return this.delegate.size();
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean contains(Object o) {
        return this.delegate.contains(o);
    }

    public Iterator<T> iterator() {
        return this.delegate.iterator();
    }

    public Object[] toArray() {
        return this.delegate.toArray();
    }

    public <T1> T1[] toArray(T1[] a) {
        return this.delegate.toArray(a);
    }

    public boolean add(T t) {
        return this.delegate.add(t);
    }

    public boolean remove(Object o) {
        return this.delegate.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.delegate.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return this.delegate.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return this.delegate.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return this.delegate.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.delegate.retainAll(c);
    }

    public void clear() {
        this.delegate.clear();
    }

    public T get(int index) {
        return this.delegate.get(index);
    }

    public T set(int index, T element) {
        return this.delegate.set(index, element);
    }

    public void add(int index, T element) {
        this.delegate.add(index, element);
    }

    public T remove(int index) {
        return this.delegate.remove(index);
    }

    public int indexOf(Object o) {
        return this.delegate.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return this.delegate.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return this.delegate.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return this.delegate.listIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return this.delegate.subList(fromIndex, toIndex);
    }

    public static <T extends INBTSerializable<N>, N extends Tag> NBTList<T, N> of(Function<N, T> read) {
        return new NBTList<>(INBTSerializable::serializeNBT, read);
    }

    public static <T extends INBTSerializable<N>, N extends Tag> NBTList<T, N> of(List<T> list, Function<N, T> read) {
        return new NBTList<>(list, INBTSerializable::serializeNBT, read);
    }

    public static <T extends INBTSerializable<N>, N extends Tag> NBTList<T, N> of(Supplier<T> supplier) {
        return new NBTList<>(INBTSerializable::serializeNBT, (n) -> {
            T value = (T) supplier.get();
            value.deserializeNBT(n);
            return value;
        });
    }

    public static NBTList<UUID, StringTag> ofUUID() {
        return new NBTList((uuid) -> {
            return StringTag.valueOf(uuid.toString());
        }, (stringNBT) -> {
            return UUID.fromString(stringNBT.toString());
        });
    }
}
