import java.util.Iterator;

public class Link<S> implements Iterable<S> {
    @Override
    public Iterator iterator() {
        return new Itr();
    }

    class Itr implements Iterator {
        private int z = 0;
        //因为在使用迭代器后还有场景需要用到root 所以用node来代替root去执行next方法 保证导入链表内容后的初始root不会被root=root.next破坏
        private Node node = root;

        @Override
        public boolean hasNext() {
            return node.next != null;
        }

        @Override
        public S next() {
            if (z > 0) {
                node = node.next;
            }
            z++;
            return node.data;
        }
    }

    class Node {
        private S data;
        private Node next;

        public Node(S data) {
            this.data = data;
        }

        public void add(Node newNode) {
            if (this.next == null) {
                this.next = newNode;
            } else {
                this.next.add(newNode);
            }
        }

        public void print() {
            System.out.print(this.data);
            if (this.next != null) {
                this.next.print();
            }
        }

    }

    public Node root;

    public Node getRoot() {
        return root;
    }

    public void addNode(S data) {
        Node newNode = new Node(data);
        if (this.root == null) {
            this.root = newNode;
        } else {
            this.root.add(newNode);
        }
    }

    public void printData() {
        root.print();
    }
}
