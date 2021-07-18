package com.xuleyan.finals.dal.mapper;

import com.xuleyan.finals.dal.pojo.GoodsSecondsKill;
import com.xuleyan.finals.dal.pojo.GoodsSecondsKillCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsSecondsKillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    long countByExample(GoodsSecondsKillCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int deleteByExample(GoodsSecondsKillCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int insert(GoodsSecondsKill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int insertSelective(GoodsSecondsKill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    List<GoodsSecondsKill> selectByExample(GoodsSecondsKillCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    GoodsSecondsKill selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int updateByExampleSelective(@Param("record") GoodsSecondsKill record, @Param("example") GoodsSecondsKillCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int updateByExample(@Param("record") GoodsSecondsKill record, @Param("example") GoodsSecondsKillCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int updateByPrimaryKeySelective(GoodsSecondsKill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_seconds_kill
     *
     * @mbg.generated Sat Jul 17 11:16:24 CST 2021
     */
    int updateByPrimaryKey(GoodsSecondsKill record);
}