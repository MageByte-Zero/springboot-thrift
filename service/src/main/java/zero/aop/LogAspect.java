package zero.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        long startTime = System.currentTimeMillis();
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        //获取方法名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        // 打印耗时的信息
        this.printExecTime(methodName, startTime, endTime);
        //打印耗时
        return obj;

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
