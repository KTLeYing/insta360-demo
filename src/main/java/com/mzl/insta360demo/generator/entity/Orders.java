package com.mzl.insta360demo.generator.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author mzl
 * @since 2024-07-28
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 主键ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 用户ID
     */
      private Long userId;

      /**
     * 商品ID
     */
      private Long productId;

      /**
     * 总金额
     */
      private BigDecimal amount;

      /**
     * 数量
     */
      private Integer quantity;

      /**
     * 订单状态，pending_pay: 待支付; create_fail：创建失败；pay_success: 支付成功, pay_fail: 支付失败
     */
      private String status;

      /**
     * 创建时间
     */
      private Date createdTime;

      /**
     * 更新时间
     */
      private Date updatedTime;


}
