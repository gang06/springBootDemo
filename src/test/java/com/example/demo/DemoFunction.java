package com.example.demo;

import java.util.function.BiFunction;
import java.util.function.Function;
/**
 * @Description:
 * @author gang
 * @date 2021/12/1 14:28
 * @version 1.0
 */
public class DemoFunction {

    public static void main(String[] args) {
        DemoFunction t1 = new DemoFunction();
        // Function函数的使用
        Integer addResult = t1.compute(3, value -> value + value);
        System.out.println("加法结果：" + addResult);

        Integer subResult = t1.compute(3, value -> value - 1);
        System.out.println("减法结果：" + subResult);

        Integer multipResult = t1.compute(3, value -> value * value);
        System.out.println("乘法结果：" + multipResult);

        Integer divisionResult = t1.compute(6, value -> value / 3);
        System.out.println("除法结果：" + divisionResult);

        /*// 使用compose场景, 从右向左处理, 这里就是 (6 * 6) + 10 = 46
        Integer composeResult = t1.computeForCompose(6,
                value -> value + 10,
                value -> value * value);
        System.out.println("Function compose 结果：" + composeResult);

        // 使用andThen场景, 从左向右处理, 这里就是(3 + 20) - 10 = 13
        Integer andThenResult = t1.computeForAndThen(3,
                value -> value + 20,
                value -> value - 10);
        System.out.println("Function andThen 结果：" + andThenResult);


        // 使用 BiFunctioin场景, 这里是 2 + 3 = 5
        Integer biFuncResult = t1.computeForBiFunction(2, 3,
                (v1, v2) -> v1 + v2);
        System.out.println("BiFunction 结果：" + biFuncResult);

        // 使用 BiFunctioin andThen场景, 这里是 (2 * 3) + 6 = 12
        Integer biFuncAndThenResult = t1.computeForBiFunctionAndThen(2, 3,
                (v1, v2) -> v1 * v2, v1 -> v1 + 6);
        System.out.println("BiFunction andThen 结果：" + biFuncAndThenResult);*/

    }

    /**
     * @param num
     * @param function
     * @return
     * @desc 使用JDK8 Function函数
     */
    private Integer compute(Integer num, Function<Integer, Integer> function) {
        Integer result = function.apply(num);
        return result;
    }

    /**
     * @param num
     * @param function1
     * @param function2
     * @return
     * @desc 使用compose函数，简单的说，就是从右向左处理。
     */
    private Integer computeForCompose(Integer num,
                                      Function<Integer, Integer> function1,
                                      Function<Integer, Integer> function2) {
        return function1.compose(function2).apply(num);
    }

    /**
     * @param num
     * @param function1
     * @param function2
     * @return
     * @desc 使用andThen函数，简单的说，就是从左向右处理。
     */
    private Integer computeForAndThen(Integer num,
                                      Function<Integer, Integer> function1,
                                      Function<Integer, Integer> function2) {
        return function1.andThen(function2).apply(num);
    }

    /**
     * @param num1
     * @param nuum2
     * @param biFunction
     * @return
     * @desc 使用BiFunction
     */
    private Integer computeForBiFunction(Integer num1, Integer num2,
                                         BiFunction<Integer, Integer, Integer> biFunction) {
        return biFunction.apply(num1, num2);
    }

    /**
     * @param num1
     * @param num2
     * @param biFunction
     * @param function
     * @return
     * @desc 使用BiFunction andThen方法
     */
    private Integer computeForBiFunctionAndThen(Integer num1, Integer num2,
                                                BiFunction<Integer, Integer, Integer> biFunction,
                                                Function<Integer, Integer> function) {
        return biFunction.andThen(function).apply(num1, num2);
    }

}
