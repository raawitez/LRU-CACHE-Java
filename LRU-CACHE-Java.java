import java.util.*;

class CDLLNode {
    int k;
    CDLLNode prev, next;

    public CDLLNode(int k) {
        this.k = k;
    }
}

class CDLL {
    CDLLNode head;

    public CDLL() {
        head = null;
    }

    CDLLNode insAtBegin(int k) {
        CDLLNode nn = new CDLLNode(k);
        nn.next = nn;
        nn.prev = nn;
        if (head == null) {
            head = nn;
        } else {
            CDLLNode last = head.prev;
            nn.next = head;
            head.prev = nn;
            last.next = nn;
            nn.prev = last;
            head = nn;
        }
        return head;
    }

    int delLast() {
        if (head == null) {
            return -1;
        }
        CDLLNode last = head.prev;
        if (last == head) {
            head = null;
            return last.k;
        }
        head.prev = last.prev;
        last.prev.next = head;
        return last.k;
    }

    void moveAtFront(CDLLNode nodeToMove) {
        if (nodeToMove == head) {
            return;
        }
        nodeToMove.prev.next = nodeToMove.next;
        nodeToMove.next.prev = nodeToMove.prev;

        nodeToMove.prev = head.prev;
        head.prev.next = nodeToMove;
        nodeToMove.next = head;
        head.prev = nodeToMove;
        head = nodeToMove;
    }

    List<Integer> getCacheElements() {
        List<Integer> result = new ArrayList<>();
        if (head == null) return result;

        CDLLNode temp = head;
        do {
            result.add(temp.k);
            temp = temp.next;
        } while (temp != head);
        return result;
    }
}

class LRUCache {
    int capacity, size;
    CDLL list;
    Map<Integer, CDLLNode> mp;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.list = new CDLL();
        this.mp = new HashMap<>();
    }

    void refer(int k) {
        if (mp.containsKey(k)) {
            CDLLNode node = mp.get(k);
            list.moveAtFront(node);
        } else {
            if (size < capacity) {
                CDLLNode node = list.insAtBegin(k);
                mp.put(k, node);
                size++;
            } else {
                int del = list.delLast();
                mp.remove(del);
                CDLLNode node = list.insAtBegin(k);
                mp.put(k, node);
            }
        }
    }

    List<Integer> getCacheElements() {
        return list.getCacheElements();
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            int N = sc.nextInt();
            int K = sc.nextInt();
            LRUCache cache = new LRUCache(K);

            for (int i = 0; i < N; i++) {
                cache.refer(sc.nextInt());
            }

            List<Integer> result = cache.getCacheElements();
            for (int i : result) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        sc.close();
    }
}


//edited
