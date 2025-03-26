package net.tarantel.chickenroost.network;

import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkUtil {


    public static <T> int writeCollection(PacketBuffer buf, Collection<T> collection, Consumer<T> writeElement,
                                          boolean removeWrittenFromCollection) {
        int i = 0;
        int initialWriterIndex = buf.writerIndex();
        buf.writeInt(0);

        int lastWriterIndex = initialWriterIndex;
        int maxElemSize = 0;
        Iterator<T> iter = collection.iterator();
        while (iter.hasNext()) {
            if (buf.capacity() < maxElemSize) break;
            T element = iter.next();
            writeElement.accept(element);
            i++;
            if (removeWrittenFromCollection) {
                iter.remove();
            }
            int writerIndex = buf.writerIndex();
            maxElemSize = Math.max(maxElemSize, writerIndex - lastWriterIndex);
            lastWriterIndex = writerIndex;
        }

        buf.setInt(initialWriterIndex, i);
        return i;
    }

    public static <T, C extends Collection<T>> C readCollection(Supplier<C> createCollection, PacketBuffer buf, Supplier<T> readElement) {
        C collection = createCollection.get();
        int size = buf.readInt();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                collection.add(readElement.get());
            }
        }
        return collection;
    }

    public static <T> List<T> readCollection(PacketBuffer buf, Supplier<T> readElement) {
        return readCollection(ArrayList::new, buf, readElement);
    }

    public static <T> int writeCollection(PacketBuffer buf, Collection<T> collection, BiConsumer<T, PacketBuffer> writeElement,
                                          boolean removeWrittenFromCollection) {
        int i = 0;
        int initialWriterIndex = buf.writerIndex();
        buf.writeInt(0);

        int lastWriterIndex = initialWriterIndex;
        int maxElemSize = 0;
        Iterator<T> iter = collection.iterator();
        while (iter.hasNext()) {
            if (buf.capacity() < maxElemSize) break;
            T element = iter.next();
            writeElement.accept(element, buf);
            i++;
            if (removeWrittenFromCollection) {
                iter.remove();
            }
            int writerIndex = buf.writerIndex();
            maxElemSize = Math.max(maxElemSize, writerIndex - lastWriterIndex);
            lastWriterIndex = writerIndex;
        }

        buf.setInt(initialWriterIndex, i);
        return i;
    }

    public static <T, C extends Collection<T>> C readCollection(Supplier<C> createCollection, PacketBuffer buf, Function<PacketBuffer, T> readElement) {
        C collection = createCollection.get();
        int size = buf.readInt();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                collection.add(readElement.apply(buf));
            }
        }
        return collection;
    }

    public static <T> List<T> readCollection(PacketBuffer buf, Function<PacketBuffer, T> readElement) {
        return readCollection(ArrayList::new, buf, readElement);
    }
}
