package zero.aop;

import com.alibaba.fastjson.JSON;
import com.zero.common.exception.ExceptionEnum;
import com.zero.common.exception.ServiceException;
import com.zero.thrift.protocol.response.TResponseStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 异常信息处理,以及入口日志请求记录AOP
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * zero.thrift.proxy包下所有类方法
     */
    @Pointcut("execution(* zero.thrift.proxy.*.*(..))")
    public void logPoiontcut() {
    }

    @Around("logPoiontcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) {
        // 定义返回对象、得到方法需要的参数
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        String argsJson = JSON.toJSONString(args);
        logger.info("thrift param is : {}", argsJson);
        //获取方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        long startTime = System.currentTimeMillis();
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            //记录异常日志,如果用logger.error(String format, Object... arguments)异常堆栈信息会丢失
            logger.error("exec method {} occur exception.", methodName, throwable);//throwable为最后参数即可打印堆栈信息
            //处理异常结构体返回客户端
            return convertResultVO(joinPoint, throwable);
        }

        long endTime = System.currentTimeMillis();
        // 打印耗时的信息
        this.printExecTime(methodName, startTime, endTime);
        //打印耗时
        return obj;

    }

    /**
     * 处理service异常返回thrift结构体
     * @param joinPoint
     * @param throwable
     * @return
     */
    private Object convertResultVO(ProceedingJoinPoint joinPoint, Throwable throwable) {
        //方法返回类型
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
        try {
            //创建一个返回对象
            Object result = returnType.newInstance();
            TResponseStatus responseStatus = new TResponseStatus();
            if (throwable instanceof ServiceException) {
                ExceptionEnum exceptionEnum = ((ServiceException) throwable).getExceptionEnum();
                String msg = ((ServiceException) throwable).getExceptionEnum().getMessage();
                responseStatus.setCode(exceptionEnum.getCode());
                responseStatus.setMsg(msg);
            } else {
                //默认系统异常
                responseStatus.setCode(ExceptionEnum.SYS_ERROR.getCode());
                responseStatus.setMsg(ExceptionEnum.SYS_ERROR.getMessage());
            }
            Field responseStatusFiled = returnType.getField("responseStatus");
            //设置响应码
            responseStatusFiled.set(result, responseStatus);
            return result;
        } catch (InstantiationException e) {
            logger.error("program occur a InstantiationException", e);
        } catch (IllegalAccessException e) {
            logger.error("program occur a IllegalAccessException", e);
        } catch (NoSuchFieldException e) {
            logger.error("program occur a NoSuchFieldException", e);
        }
        return null;
    }

    /**
     * 打印方法执行耗时的信息，如果超过了一定的时间，才打印
     * @param methodName
     * @param startTime
     * @param endTime
     */
    private void printExecTime(String methodName, long startTime, long endTime) {
        long diffTime = endTime - startTime;
        logger.warn("-----{} 方法执行耗时：{} ms", methodName, diffTime);
//        if (diffTime > ONE_MINUTE) {
//            logger.warn("-----{} 方法执行耗时：{} ms", methodName, diffTime + " ms");
//        }
    }

}
