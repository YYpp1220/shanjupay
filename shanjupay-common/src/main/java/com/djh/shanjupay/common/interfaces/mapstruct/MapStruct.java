package com.djh.shanjupay.common.interfaces.mapstruct;

import java.util.List;

/**
 * 地图结构
 *
 * @author MyMrDiao
 * @date 2021/04/25
 */
public interface MapStruct<E, D, V> {
    /**
     * dto实体
     *
     * @param d d
     * @return {@link E}
     */
    E dtoToEntity(D d);

    /**
     * 实体dto
     *
     * @param e e
     * @return {@link D}
     */
    D entityToDto(E e);

    /**
     * 实体签证官
     *
     * @param e e
     * @return {@link V}
     */
    V entityToVo(E e);

    /**
     * dto,签证官
     *
     * @param d d
     * @return {@link V}
     */
    V dtoToVo(D d);

    /**
     * 签证官,dto
     *
     * @param v v
     * @return {@link D}
     */
    D voToDto(V v);

    /**
     * 签证官实体
     *
     * @param v v
     * @return {@link E}
     */
    E voToEntity(V v);

    /**
     * dto列表实体列表
     *
     * @param d d
     * @return {@link List <E>}
     */
    List<E> dtoListToEntityList(List<D> d);

    /**
     * 签证官dto名单列表
     *
     * @param v v
     * @return {@link List<D>}
     */
    List<D> voListToDtoList(List<V> v);

    /**
     * 实体类集合转为dto集合
     *
     * @param e e
     * @return {@link List<D>}
     */
    List<D> entityListToDtoList(List<E> e);
}
