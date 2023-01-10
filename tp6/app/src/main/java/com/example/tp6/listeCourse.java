package com.example.tp6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class listeCourse implements List<courseItem> {
    private final List<courseItem> liste;
    private final String nom;

    public listeCourse(String nom) {
        this.liste = new ArrayList<>();
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public int size() {
        return liste.size();
    }

    @Override
    public boolean isEmpty() {
        return liste.isEmpty();
    }

    @Override
    public boolean contains(@Nullable Object o) {
        return liste.contains(o);
    }

    @NonNull
    @Override
    public Iterator<courseItem> iterator() {
        return liste.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return liste.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return (T[]) liste.toArray();
    }

    @Override
    public boolean add(courseItem courseItem) {
        return liste.add(courseItem);
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return liste.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return liste.containsAll(collection);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends courseItem> collection) {
        return liste.addAll(collection);
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends courseItem> collection) {
        return liste.addAll(i, collection);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return liste.removeAll(collection);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return liste.retainAll(collection);
    }

    @Override
    public void clear() {
        this.liste.clear();
    }

    @Override
    public courseItem get(int i) {
        return liste.get(i);
    }

    @Override
    public courseItem set(int i, courseItem courseItem) {
        return liste.set(i, courseItem);
    }

    @Override
    public void add(int i, courseItem courseItem) {
        liste.add(i, courseItem);
    }

    @Override
    public courseItem remove(int i) {
        return liste.remove(i);
    }

    @Override
    public int indexOf(@Nullable Object o) {
        return liste.indexOf(o);
    }

    @Override
    public int lastIndexOf(@Nullable Object o) {
        return liste.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<courseItem> listIterator() {
        return liste.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<courseItem> listIterator(int i) {
        return liste.listIterator(i);
    }

    @NonNull
    @Override
    public List<courseItem> subList(int i, int i1) {
        return liste.subList(i, i1);
    }
}
