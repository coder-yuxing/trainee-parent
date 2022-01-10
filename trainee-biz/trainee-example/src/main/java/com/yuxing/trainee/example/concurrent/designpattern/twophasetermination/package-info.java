/**
 * Two-Phase Termination 模式，分两阶段终止。它是一种先执行完终止处理再终止线程的模式。
 * 我们称线程在进行正常处理时的状态为“操作中”。在要停止该线程时，我们会发出“终止请求”。这样
 * 线程不会突然终止，而是会先开始进行“打扫工作”。我们称这种状态为“终止处理中”。从“操作中”变
 * 为“终止处理中”是线程终止的第一阶段。
 * 在“终止处理中”状态下，线程不会再进行正常操作了。它虽然仍然在运行，但是只会进行终止处理。终
 * 止处理完成后，就会真正地终止线程。“终止处理中”状态结束是线程终止的第二阶段。
 *
 * 该阶段的要点如下：
 * 1. 安全地终止线程(安全性)
 * 2. 必定会进行终止处理(生存性)
 * 3. 发出终止请求后尽快进行终止处理(响应性)
 * @author yuxing
 * @since 2022/1/10
 */
package com.yuxing.trainee.example.concurrent.designpattern.twophasetermination;