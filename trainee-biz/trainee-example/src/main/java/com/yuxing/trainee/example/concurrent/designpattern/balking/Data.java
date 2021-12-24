package com.yuxing.trainee.example.concurrent.designpattern.balking;

/**
 * @author yuxing
 * @since 2021/12/24
 */
public class Data {

    private final String fileName;
    private String content;
    private boolean changed;

    public Data(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.changed = true;
    }

    public synchronized void change(String newContent) {
        if (this.content.equals(newContent)) {
            this.changed = false;
            return;
        }
        this.content = newContent;
        this.changed = true;
    }

    public synchronized void save() {
        if (!this.changed) {
            return;
        }
        this.doSave();
        this.changed = false;
    }

    private void doSave() {
        System.err.println(Thread.currentThread().getName() + " calls doSave, content = " + this.content);
        // do save content to file
    }
}
