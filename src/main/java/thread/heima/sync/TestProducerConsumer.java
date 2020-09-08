package thread.heima.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class TestProducerConsumer {
    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(5);

        for(int i = 0; i < 5; ++i){
            int id = i;
            new Thread(() -> {
                queue.put(new Message(1, String.valueOf(id)));
            }, "producer" + id).start();
        }

        for(int i = 0; i < 5; ++i){
            int id = i;
            new Thread(() -> {
                Message msg = queue.take();
                log.debug("producer{}:{}", msg.getId(), (String)msg.getValue());
            }, "consumer" + id).start();
        }
    }
}
@Slf4j
class MessageQueue{
    private LinkedList<Message> list = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take(){
        synchronized (list){
            while(list.isEmpty()){
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.notifyAll();
            return list.poll();
        }
    }

    public void put(Message msg){
        synchronized (list){
            while(list.size() == capacity){
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(msg);
            list.notifyAll();
        }
    }
}

class Message{
    private int id;
    private String value;

    public Message(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }
}
