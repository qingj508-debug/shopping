package cn.lili.common.exception;

import cn.lili.common.enums.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局业务异常类
 *
 * @author Chopper
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 3447728300174142127L;


    /**
     * 异常消息
     */
    private String msg = ResultCode.ERROR.message();

    /**
     * 错误码
     */
    private ResultCode resultCode;

    public ServiceException(String msg) {
        this.resultCode = ResultCode.ERROR;
        this.msg = msg;
    }

    public ServiceException() {
        super();
        // 无参构造时默认错误码，避免 resultCode 为 null 导致全局异常处理器 NPE（如积分商品不存在场景）
        this.resultCode = ResultCode.ERROR;
    }

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.msg = message;
    }

}
