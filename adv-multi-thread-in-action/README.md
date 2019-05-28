# Java 多线程高级应用

- Java 4的JSP-133重新定义了内存模型, 引入了NIO
- Java 5的JSR-166引入了java.util.concurrent包, 加入了Future/Promise模型, 包括ExecutorService等
- Java 7引入了Fork/Join
- Java 8引入了Stream, 加入了parallelStream

## ExecutorService

基于[Future/Promise模型][Future与promise]

ExecutorService主要接口:

- execute/submit, 提交任务到队列, 并不一定立马执行, 不等待执行完毕
- invokeAny/invokeAll, 提交任务到队列, 并等待执行完毕获得结果
- shutdown, 禁止提交任务, 等待剩余任务完成后销毁
- shutdownNow, 立即销毁

### Future

submit/invokeAll都会返回Future, 通过Future可以知道相应task的状态和结果.

- isCancelled/isDone, 返回Future的状态
- cancel, 取消task
- get, 等待task结束并返回结果

## Fork/Join

基于[Fork/Join模型][Fork–join model]

使用ForkJoinTask来描述task

- RecursiveAction, task无返回值
- RecursiveTask<V>, task的返回值是V类型的

ForkJoinTask的操作:

- execute/submit, 提交任务, 但不等待执行结束, 返回Future
- invoke/invokeAll, 提交任务, 并等待执行结束, 返回结果
- fork, 提交认为, 返回ForkJoinTask
- join, 等待任务结束
- get, 等待任务结束, 并返回结果

## Stream

默认情况下Stream API使用Fork/Join框架来执行并行操作.

---

[java.util.concurrent]: https://www.baeldung.com/java-util-concurrent
[A Guide to the Java ExecutorService]: https://www.baeldung.com/java-executor-service-tutorial
[Future与promise]: https://zh.wikipedia.org/zh-hans/Future%E4%B8%8Epromise
[Guide to the Fork/Join Framework in Java]: https://www.baeldung.com/java-fork-join
[Fork–join model]: https://en.wikipedia.org/wiki/Fork%E2%80%93join_model