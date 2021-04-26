package com.djh.shanjupay.common.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * builder工具类
 *
 * @author MrMyHui
 * @date 2021/01/21
 */
@Data
public class BuilderUtils<T> {

    private final Supplier<T> instantiate;

    private List<Consumer<T>> modifiers = new ArrayList<>();

    private BuilderUtils(Supplier<T> instantiate) {
        this.instantiate = instantiate;
    }

    public static <T> BuilderUtils<T> of(Supplier<T> instantiate) {
        return new BuilderUtils<>(instantiate);
    }

    public <P1> BuilderUtils<T> with(Consumer1<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        modifiers.add(c);
        return this;
    }

    public <P1, P2> BuilderUtils<T> with(Consumer2<T, P1, P2> consumer, P1 p1, P2 p2) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3> BuilderUtils<T> with(Consumer3<T, P1, P2, P3> consumer, P1 p1, P2 p2, P3 p3) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3, P4> BuilderUtils<T> with(Consumer4<T, P1, P2, P3, P4> consumer, P1 p1, P2 p2, P3 p3, P4 p4) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3, p4);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3, P4, P5> BuilderUtils<T> with(Consumer5<T, P1, P2, P3, P4, P5> consumer, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3, p4, p5);
        modifiers.add(c);
        return this;
    }

    public <P1, P2, P3, P4, P5, P6> BuilderUtils<T> with(Consumer6<T, P1, P2, P3, P4, P5, P6> consumer, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2, p3, p4, p5, p6);
        modifiers.add(c);
        return this;
    }

    public T build() {
        T value = instantiate.get();
        modifiers.forEach(modifier -> modifier.accept(value));
        modifiers.clear();
        return value;
    }

    /**
     * 1 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer1<T, P1> {
        /**
         * 接受
         *
         * @param t  t
         * @param p1 p1
         */
        void accept(T t, P1 p1);
    }

    /**
     * 2 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer2<T, P1, P2> {
        /**
         * 接受
         *
         * @param t  t
         * @param p1 p1
         * @param p2 p2
         */
        void accept(T t, P1 p1, P2 p2);
    }

    /**
     * 3 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer3<T, P1, P2, P3> {
        /**
         * 接受
         *
         * @param t  t
         * @param p1 p1
         * @param p2 p2
         * @param p3 p3
         */
        void accept(T t, P1 p1, P2 p2, P3 p3);
    }

    /**
     * 4 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer4<T, P1, P2, P3, P4> {
        /**
         * 接受
         *
         * @param t  t
         * @param p1 p1
         * @param p2 p2
         * @param p3 p3
         * @param p4 p4
         */
        void accept(T t, P1 p1, P2 p2, P3 p3, P4 p4);
    }

    /**
     * 5 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer5<T, P1, P2, P3, P4, P5> {
        /**
         * 接受
         *
         * @param t  t
         * @param p1 p1
         * @param p2 p2
         * @param p3 p3
         * @param p4 p4
         * @param p5 p5
         */
        void accept(T t, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
    }

    /**
     * 6 参数 Consumer
     */
    @FunctionalInterface
    public interface Consumer6<T, P1, P2, P3, P4, P5, P6> {
        /**
         * 接受
         *
         * @param t  t
         * @param p1 p1
         * @param p2 p2
         * @param p3 p3
         * @param p4 p4
         * @param p5 p5
         * @param p6 p6
         */
        void accept(T t, P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
    }
}
