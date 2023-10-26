package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.demo.sweetfish.logic.model.Goods

@Dao
interface GoodsDao {

    /**
     * 插入一条新的商品信息
     * @param goods 新商品信息
     * @return 新商品的唯一标识符Id
     */
    @Insert
    fun insert(goods: Goods): Long

    /**
     * 插入列表内所有新商品信息
     * @param goodsList 新商品信息列表
     */
    @Insert
    fun insert(goodsList: List<Goods>)

    /**
     * 更新商品信息
     * @param newGoods 更新后的商品信息
     */
    @Update
    fun update(newGoods: Goods)

    /**
     * 查找所有商品信息
     * @return 返回一个包含所有商品信息的列表
     */
    @Query("select * from goods")
    fun findAll(): List<Goods>

    /**
     * 根据id查找商品信息
     * @param id 要查找的商品id
     * @return 返回查找到的商品信息,找不到则返回null
     */
    @Query("select * from goods where id = :id")
    fun findByGoodsId(id: Long): Goods?

    /**
     * 根据卖家id查找商品信息
     * @param id 要查找的卖家id
     * @return 返回一个包含该卖家所有商品信息的列表
     */
    @Query("select * from goods where sellerId = :id")
    fun findBySellerId(id: Long): List<Goods>

    /**
     * 通过商品名称查找拥有相似名称的商品信息
     * @param title 要查询的商品名称
     * @return 返回一个包含所有拥有相似名称的商品信息的列表
     */
    @Query("select * from goods where title like :title")
    fun findLikeGoodsTitle(title: String): List<Goods>

    /**
     * 删除一条商品信息
     * @param goods 要删除的商品信息
     */
    @Delete
    fun delete(goods: Goods)

    /**
     * 删除所有商品信息
     */
    @Query("delete from Goods")
    fun deleteAll()

}