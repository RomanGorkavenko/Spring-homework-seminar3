package ru.gb.spring.homework.sem3.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import ru.gb.spring.homework.sem3.annotations.RecoverException;

@Slf4j
@Aspect
@Component
public class ExceptionAndTimerAspect {

    @Pointcut("within(@ru.gb.spring.homework.sem3.annotations.* *)")
    public void beansAnnotatedWith() {
    }

    @Pointcut("@annotation(ru.gb.spring.homework.sem3.annotations.Timer)")
    public void methodsAnnotatedWithTimer() {
    }

    @Pointcut("@annotation(ru.gb.spring.homework.sem3.annotations.RecoverException)")
    public void methodsAnnotatedWithRecoverException() {
    }

    /**
     * Задание для 8 семинара
     * 1. Создать аннотацию замера времени исполнения метода (Timer). Она может ставиться над методами или классами.
     * Аннотация работает так: после исполнения метода (метода класса) с такой аннотацией, необходимо в лог записать следующее:
     * className - methodName #(количество секунд выполнения)
     */
    @Around("beansAnnotatedWith() || methodsAnnotatedWithTimer()")
    public Object executionTimeOfMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        StopWatch countdown = new StopWatch();

        countdown.start();
        Object result = proceedingJoinPoint.proceed();
        countdown.stop();

        log.error("{}-{} #({} ms)", className, methodName, countdown.getTotalTimeMillis());

        return result;
    }

    /**
     * Задание для 8 семинара
     *  2.* Создать аннотацию RecoverException, которую можно ставить только над методами.
     * У аннотации должен быть параметр noRecoverFor, в котором можно перечислить другие классы исключений.
     * Аннотация работает так: если во время исполнения метода был экспешн, то не прокидывать его выше
     * и возвращать из метода значение по умолчанию (null, 0, false, ...).
     * При этом, если тип исключения входит в список перечисленных в noRecoverFor исключений,
     * то исключение НЕ прерывается и прокидывается выше.
     * 3.*** Параметр noRecoverFor должен учитывать иерархию исключений.
     */
    @Around("methodsAnnotatedWithRecoverException()")
    public Object recoverException(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.error("Перехват исключений");
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        RecoverException annotation = signature.getMethod().getAnnotation(RecoverException.class);

        Object result;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            for (Class<? extends RuntimeException> clazz : annotation.noRecoverFor()) {

                //3.*** Параметр noRecoverFor должен учитывать иерархию исключений.
                //clazz.isAssignableFrom(e.getClass())
                if (e.getClass().equals(clazz) || clazz.isAssignableFrom(e.getClass())) {
                    log.warn("Исключение пробросилось дальше");
                    throw e;
                }
            }
            if (signature.getMethod().getReturnType().isPrimitive()) {
                if(signature.getMethod().getReturnType().getSimpleName().equals("boolean")) {
                    log.warn("{} return=false", signature.getMethod().getReturnType());
                    return false;
                } else {
                    log.warn("{} return=0", signature.getMethod().getReturnType());
                    return 0;
                }
            }
            log.warn("{} return=null", signature.getMethod().getReturnType());
            return null;
        }
        return result;
    }

}
