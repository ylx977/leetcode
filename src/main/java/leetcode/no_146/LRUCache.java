package leetcode.no_146;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LRUCache
 * @Author ylx977
 * @Date 2023/10/5
 * @Time 22:21
 * @Version 1.0
 * @Description Created by ylx977 on 2023/10/5 TODO
 */
public class LRUCache {

    private Map<Integer, Integer> cacheMap = new HashMap<>();

    private int capacity;

    private Map<Integer, Node> keyToNode = new HashMap<>();

    private Node head;

    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (cacheMap.containsKey(key)) {
            updateKey(key);
            return cacheMap.get(key);
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cacheMap.containsKey(key)) {
            // 更新使用频率，从原先对列抽出来，放对列头
            updateKey(key);
        } else {
            insertKey(key);
        }
        cacheMap.put(key, value);
    }

    private void insertKey(int key) {
        Node node = new Node();
        node.setKey(key);
        keyToNode.put(key, node);

        if (head == null && tail == null) {
            head = node;
            tail = node;
            return;
        }
        // 放入头部，满了踢掉末尾
        node.setPost(head);
        head.setPre(node);
        head = node;

        if (keyToNode.size() > capacity) {
            // 干掉tail
            int tailKey = tail.getKey();
            keyToNode.remove(tailKey);
            cacheMap.remove(tailKey);
            tail = tail.getPre();
            tail.setPost(null);
        }
    }

    private void updateKey(int key) {
        Node node = keyToNode.get(key);
        if (head == node) {
            // 头节点，不管
            return;
        }
        if (tail == node) {
            // 尾节点
            tail = node.getPre();
            tail.setPost(null);
            node.setPre(null);
            node.setPost(head);
            head.setPre(node);
            head = node;
            return;
        }
        // 中间段
        Node pre = node.getPre();
        Node post = node.getPost();
        pre.setPost(post);
        post.setPre(pre);

        node.setPre(null);
        node.setPost(head);
        head.setPre(node);
        head = node;
    }

}

class Node {
    private int key;
    private Node pre;
    private Node post;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Node getPre() {
        return pre;
    }

    public void setPre(Node pre) {
        this.pre = pre;
    }

    public Node getPost() {
        return post;
    }

    public void setPost(Node post) {
        this.post = post;
    }
}
