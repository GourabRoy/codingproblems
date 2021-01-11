package leetcode.hard;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class MergeKSortedLists {

    static class ListItem {
        int listIndex;
        ListNode itemNode;
        int item;

        public ListItem(int listIndex, ListNode itemNode, int item) {
            this.listIndex = listIndex;
            this.itemNode = itemNode;
            this.item = item;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListItem> minHeap = new PriorityQueue<>(Comparator.comparingInt(x -> x.item));
        initHeap(lists, minHeap);
        ListNode root = new ListNode();
        ListNode node = root;
        while (!minHeap.isEmpty()) {
            ListItem item = minHeap.poll();
            ListItem nextItem = getNextItem(lists, item);
            node = addNext(node, item.item);
            if (Objects.nonNull(nextItem)) {
                minHeap.add(nextItem);
            }
        }
        return root.next;
    }

    ListNode addNext(ListNode node, int val) {
        node.next = new ListNode(val);
        return node.next;
    }

    void initHeap(ListNode[] lists, PriorityQueue<ListItem> minHeap) {
        for (int i = 0; i < lists.length; i++) {
            ListNode node = lists[i];
            if (Objects.nonNull(node)) minHeap.add(new ListItem(i, node, node.val));
        }
    }

    ListItem getNextItem(ListNode[] lists, ListItem current) {
        ListNode next = current.itemNode.next;
        if (Objects.nonNull(next)) {
            return new ListItem(current.listIndex, next, next.val);
        } else {
            return null;
        }
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MergeKSortedLists t = new MergeKSortedLists();
        int[] l1 = {1, 1, 2, 3, 5, 8};
        int[] l2 = {2, 2, 4, 6};
        int[] l3 = {};
        int[] l4 = null;

        ListNode[] lists = new ListNode[4];
        lists[0] = t.createList(l1);
        lists[1] = t.createList(l2);
        lists[2] = t.createList(l3);
        lists[3] = t.createList(l4);

        t.printListNodeList(lists);
        t.printListNode(t.mergeKLists(lists));
    }

    ListNode createList(int[] ints) {
        if(Objects.isNull(ints) || ints.length == 0) return null;
        ListNode root = new ListNode();
        ListNode node = root;
        for(int i : ints) {
            node = addNext(node, i);
        }
        return root.next;
    }

    void printListNodeList(ListNode[] lists) {
        Stream.of(lists).forEach(this::printListNode);
    }

    void printListNode(ListNode node) {
        StringBuffer sb = new StringBuffer();
        while(Objects.nonNull(node)) {
            sb.append(node.val).append(" ");
            node = node.next;
        }
        System.out.println(sb);
    }

}
