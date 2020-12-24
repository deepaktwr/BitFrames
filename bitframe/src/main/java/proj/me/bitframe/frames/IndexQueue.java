package proj.me.bitframe.frames;

/**
 * Created by root on 22/7/17.
 */
public class IndexQueue<T> {
    private T[] array;

    private int front = -1;
    private int rear = -1;

    public IndexQueue(int size){
        array = (T[])new Object[size];
    }

    public boolean enqueue(T node){
        int index = (front + 1) % array.length;
        if(index == rear) return false;
        array[index] = node;
        front = index;
        if(rear == -1) rear = index;
        return true;
    }

    public T dequeue(){
        if(rear == -1) return null;
        T obj = array[rear];
        array[rear] = null;
        if(rear == front){
            front = -1;
            rear = -1;
        }else rear = (rear + 1) % array.length;

        return obj;
    }

    public T peak(){
        if(front == -1) return null;
        return array[front];
    }

    public T itemAt(int index){
        if(index >= array.length) return null;
        return array[index];
    }

    public boolean isEmpty(){
        return front == -1;
    }

    public int currentFront(){
        return front;
    }

}
