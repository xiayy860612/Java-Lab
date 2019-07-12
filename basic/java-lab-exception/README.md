# 深入理解Java异常处理机制

在一个**健壮(robust)**的程序中, 当错误出现时,
程序应该能返回到一种**安全状态**.

安全状态

- 程序可以继续正确执行, 而不会奔溃, 导致用户无法继续操作
- 保证数据的正确性
    - 通过回滚操作所产生的修改, 回到原始状态
    - 保存操作的结果, 并通过某种**妥善的方式**来结束操作, 比如状态控制

异常处理机制就是将**控制权**从错误产生的地方
转移到能够处理该错误的**错误处理器**,
通过错误处理器对错误进行处理.

错误的分类

- 用户的输入错误
- 程序依赖的外部设备, 系统或者服务的错误, 比如硬盘满了, 服务关闭了等等
- 程序的逻辑代码错误
    - 可预期的错误, 即程序自检后发现的
    - 未预期的错误, 即程序运行中出现的, 在应该正确的地方程序出现错误.

## Java的异常设计

[Java Exception Design](doc/java-exception-design.puml)

Java中的异常对象都继承自**Throwable**类.

- Error, 描述Java运行时系统的内部错误和资源耗尽错误,
Java程序无法处理这种错误, 所以不应在程序中抛出.
- Exception, Java程序需要关注导致这类异常产生的相关错误
    - RuntimeException, 由程序本身业务逻辑导致的错误, 如果出现则会中断处理流程, 返回程序的安全状态. 之偶需要调查导致业务逻辑的错误并解决
    - 其他Exception, 程序所依赖的外部元素导致, 或者是某些不可预期的错误; 并非由程序本身的业务逻辑导致的, 实现者无法决定如何去处理, 需要上层调用者作出决定

Java语言规范定义了2类异常

- 非受查异常(unchecked), 包括Error和RuntimeException,
如果在有可能发生的地方不提供错误处理器, 则程序将错误继续向上抛出,
直到遇到错误处理器进行处理
- 受查异常(checked), 在有可能发生的地方,
必须显式提供错误处理器进行处理

对于受查异常和RuntimeException的应用场景的建议:

- 受查异常通常在依赖库中使用居多, 由于本身无法处理这些错误,
要求调用者必须进行处理
- RuntimeException面向用户的产品级中使用, 避免因为异常导致程序终端,
并给与用户合理的错误提示
- 在面向用户的产品级中, 所有的受查异常必须被处理, 处理方式如下:
    - 包装成为一个RuntimeException, 给与用户提示
    - 捕获后, 通知相关人员进行处理
    - 捕获后, 关闭相关的功能并给与用户合理的提示,
    或者切换到另一个可用的服务, 进行重试

**一个方法不仅要告知参数和返回值, 还需要告知所有有可能发生的受查异常;
而非受查异常要么(Error)不可控制,
要么就(RuntimeException)应该避免发生**

Java中不允许子类的throws说明符中出现超过父类方法所列出的异常类范围.

catch单个异常, 异常变量不是final, 可修改.

Java支持catch多个**不相关(不存在继承关系)**的异常,
并且异常变量隐含为final, 不可修改.

当需要将异常包装位一个新异常时, 强烈建议将原先的异常设置位新异常的原因.

```
try{
    do something
    raise sql exception
} catch(SQLException e) {
    Throwable ex = new CustomException();
    // 将原先的异常设置位新异常的原因
    ex.initCause(e);
    throw ex;
}

// 获取原始异常
Throwable e = se.getCause();
```

异常的捕获需要花费比较多的时间, 所以只在异常情况下进行处理.

对于异常建议遵循"早抛出, 晚捕获",
即在应该抛出的地方抛出, 在合适的地方进行捕获处理.

## try-with-resources

Java 7引入了try-with-resources, 即如果资源实现了**AutoCloseable**接口,
则在try块结束时, 自动调用资源的**close**方法.

try-with-resources可以很好的处理
**在final中close如果也抛出异常也要进行处理**的问题;
它会将close中抛出的异常捕获, 可以通过**getSuppressed**方法来查看这些异常.

try-with-resources也可以带有catch和finally子句,
但是资源会先关闭后, 才会调用catch或者finally子句.

## 断言

- 断言失败是致命的, 不可恢复的错误.
- 断言检查只用于开发和测试阶段

可以通过`java -ea app.jar`来开启断言, 默认位关闭.
开启或者关闭断言, 不需要重新编译.

## Reference

- [Java核心技术 卷I](https://book.douban.com/subject/26880667/) --- 第七章 异常,断言和日志
- [深入理解java异常处理机制](https://blog.csdn.net/hguisu/article/details/6155636)