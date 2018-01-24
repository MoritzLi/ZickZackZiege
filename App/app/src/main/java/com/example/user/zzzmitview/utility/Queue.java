package com.example.user.zzzmitview.utility;

import android.support.annotation.NonNull;

import java.util.Iterator;

/**
 * Dynamisch lineare Datenstruktur, die immer das als erstes eingefügte Element liefert und
 * Elemente in einer Schlange aufreiht.
 *
 * @param <ContentType> Inhaltsdatentyp
 * @author Moritz
 * @version 2017.2312
 * @since 0.7.1
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public class Queue<ContentType> implements Iterable<ContentType> {
    private Node first, last;
    private int size;

    @SafeVarargs
    public Queue(ContentType... objects) {
        this.first = null;
        this.last = null;
        this.size = 0;

        for (ContentType object : objects)
            append(object);
    }

    public int size() {
        return size;
    }

    public ContentType getContent() {
        return first.content;
    }

    public Queue<ContentType> remove() {
        if (!isEmpty()) {
            this.first = first.next;
            size--;
        }

        return this;
    }

    public Queue<ContentType> append(ContentType object) {
        if (object != null) {
            Node n = new Node(object);
            if (isEmpty()) {
                first = n;
            } else {
                last.next = n;
            }
            last = n;
            size++;
        }

        return this;
    }

    public boolean isEmpty() {
        return first == null;
    }

    @NonNull
    @Override
    public Iterator<ContentType> iterator() {
        return new Iterator<ContentType>() {
            @Override
            public boolean hasNext() {
                return first != null;
            }

            @Override
            public ContentType next() {
                ContentType content = getContent();
                Queue.this.remove();
                return content;
            }
        };
    }

    public Queue<ContentType> clear() {
        first = null;
        last = null;
        size = 0;
        return this;
    }

    private class Node {
        private Node        next;
        private ContentType content;

        private Node(ContentType content) {
            this.content = content;
            this.next = null;
        }
    }
}