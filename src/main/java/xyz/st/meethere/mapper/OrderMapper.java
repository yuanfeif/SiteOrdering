package xyz.st.meethere.mapper;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.st.meethere.entity.Ground;
import xyz.st.meethere.entity.PreOrder;
import xyz.st.meethere.entity.User;

import java.util.List;

@Repository
public interface OrderMapper {
    //    用户订单管理
    @Select("select preorder.userId, preorder.groundId, groundName, userName, preOrderId, orderTime, price, " +
            "startTime, duration, payed, checked from (preorder join user on preorder.userId=user.userId) join ground" +
            " on ground.groundId=preorder.groundId where preorder.userId=#{id}")
    List<PreOrder> getPreOrdersByUserId(Integer id);

    @Delete("delete from preorder where preOrderId=#{preOrderId}")
    int deletePreOrder(Integer preOrderId);

    @Update("update preorder set orderTime=#{orderTime},startTime=#{startTime},price=#{price},duration=#{duration} " +
            "where preOrderId=#{preOrderId}")
    int updatePreOrder(PreOrder preOrder);

    @Options(useGeneratedKeys = true, keyProperty = "preOrderId")
    @Insert("insert into preorder(groundId,userId,orderTime,price,startTime,duration,payed,checked) values" +
            "(#{groundId}," +
            "#{userId},#{orderTime},#{price},#{startTime},#{duration},#{payed},#{checked})")
    int addPreOrder(PreOrder preOrder);

    @Select("select * from user where userId = #{id}")
    User checkUserExistence(Integer id);

    @Select("select * from ground where groundId = #{id}")
    Ground checkGroundExistence(Integer id);

    @Select("select pricePerHour from ground where groundId=#{gid}")
    Integer getGroundPrice(Integer gid);

    @Select("select * from preorder where groundId=#{id}")
    List<PreOrder> getPreOrdersByGroundId(Integer id);

    @Select("select preorder.userId, preorder.groundId, groundName, userName, preOrderId, orderTime, price, " +
            "startTime, duration, payed, checked from (preorder join user on preorder.userId=user.userId) join ground" +
            " on ground.groundId=preorder.groundId where preorder.userId=#{uid} and preOrderId=#{oid}")
    PreOrder getAPreOrderOfUser(Integer uid, Integer oid);

    // 管理员订单管理
//    添加上降序获取功能
    @Select("select * from preorder where preOrderId=#{pid}")
    PreOrder getPreOrderByPid(Integer pid);

    @Select("select preorder.userId, preorder.groundId, groundName, userName, preOrderId, orderTime, price, " +
            "startTime, duration, payed, checked from (preorder join user on preorder.userId=user.userId) join ground" +
            " on ground.groundId=preorder.groundId")
    List<PreOrder> getAllPreOrders();

    @Select("select * from preorder where checked = 0")
    List<PreOrder> getAllUncheckedOrders();

    @Update("update preorder set checked= 1 where preOrderId = #{id}")
    int checkPreOrder(Integer id);

    @Update("update preorder set checked= -1 where preOrderId = #{id}")
    int checkPreOrderFail(Integer id);
}
