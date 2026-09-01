package cn.lili.feign.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 会员DTO（跨服务共享）
 *
 * @author lili
 */
@Data
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    private String id;

    /**
     * 手机号
     */
    private String mobile;
}
