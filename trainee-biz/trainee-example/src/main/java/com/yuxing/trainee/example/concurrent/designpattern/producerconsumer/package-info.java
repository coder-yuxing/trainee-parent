/**
 * Producer-Consumer模式(生产者-消费者模式)
 * - Producer 即生产者，指的是生成任务/数据的线程
 * - Consumer 即消费者，指的是使用任务/数据的线程
 *
 * 生产者安全地将数据交给消费者。虽然仅是这样看似简单的操作，但当生产者与消费者以不同线程运行时，两者之间的处理速度差异便会引起问题。
 * 例如，消费者想要获取数据，但数据还未生成，或者生产者要交付数据，但消费者的状态还无法接受数据等。
 *
 * Producer-Consumer模式在生产者与消费者之间加入了一个“桥梁角色”。该桥梁角色用于消除线程间处理速度的差异。
 * 通常而言，在该模式下，生产者与消费者都有多个，当两者都只有一个树，我们也称之为“Pipe模式”。
 *
 *
 * @author yuxing
 * @since 2021/12/24
 */
package com.yuxing.trainee.example.concurrent.designpattern.producerconsumer;