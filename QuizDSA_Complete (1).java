import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════════════╗
 * ║      DSA-Powered Quiz Builder & Management System               ║
 * ║      SINGLE FILE — All Course Outcomes in One Place             ║
 * ╠══════════════════════════════════════════════════════════════════╣
 * ║  CO1 — Algorithm Analysis: Big-O/Ω/Θ, Sorting, Searching        ║
 * ║  CO2 — ADTs: Singly, Doubly, Circular Linked Lists              ║
 * ║  CO3 — Stack, Queue, Circular Queue, Deque                      ║
 * ║  CO4 — Hashing, HashMap, Java Collections                       ║
 * ║  CO5 — Max Heap, Priority Queue (Leaderboard)                   ║
 * ║  CO6 — Complete Quiz Management Application                     ║
 * ╚══════════════════════════════════════════════════════════════════╝
 *
 *  HOW TO RUN IN VS CODE:
 *  1. Save this file as:  QuizDSA_Complete.java
 *  2. Open terminal in VS Code  (Ctrl + `)
 *  3. Compile:  javac QuizDSA_Complete.java
 *  4. Run:      java QuizDSA_Complete
 *  5. Login:    username = admin   |   password = admin123
 */
public class QuizDSA_Complete {

    // ════════════════════════════════════════════════════════════════
    //  MAIN ENTRY POINT
    // ════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║     DSA Quiz Builder & Management System             ║");
        System.out.println("║     All Course Outcomes  CO1 - CO6                  ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        new QuizManagementSystem().run();
    }

    // ════════════════════════════════════════════════════════════════
    //  ██████╗  ██████╗  ██████╗       ██████╗   ██╗
    //  CO2 — MODEL: QUESTION
    // ════════════════════════════════════════════════════════════════
    static class Question implements Comparable<Question> {
        private static int idCounter = 1;
        private int    id;
        private String text, optionA, optionB, optionC, optionD, category;
        private char   correctOption;
        private int    difficulty; // 1=Easy 2=Medium 3=Hard

        Question(String text, String a, String b, String c, String d,
                 char correct, int diff, String cat) {
            this.id            = idCounter++;
            this.text          = text;
            this.optionA       = a; this.optionB = b;
            this.optionC       = c; this.optionD = d;
            this.correctOption = Character.toUpperCase(correct);
            this.difficulty    = diff;
            this.category      = cat;
        }

        @Override
        public int compareTo(Question o) {
            return Integer.compare(this.difficulty, o.difficulty);
        }

        void display() {
            String diff = difficulty == 1 ? "Easy" : difficulty == 2 ? "Medium" : "Hard";
            System.out.println("  [Q" + id + "] (" + diff + " | " + category + ")");
            System.out.println("  " + text);
            System.out.println("    A) " + optionA);
            System.out.println("    B) " + optionB);
            System.out.println("    C) " + optionC);
            System.out.println("    D) " + optionD);
        }

        void displayWithAnswer() {
            display();
            System.out.println("  >> Correct Answer: " + correctOption);
            System.out.println();
        }

        String getDiffLabel() {
            return difficulty == 1 ? "Easy" : difficulty == 2 ? "Medium" : "Hard";
        }

        int    getId()            { return id; }
        String getText()          { return text; }
        char   getCorrectOption() { return correctOption; }
        int    getDifficulty()    { return difficulty; }
        String getCategory()      { return category; }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO2 — MODEL: USER
    // ════════════════════════════════════════════════════════════════
    static class User implements Comparable<User> {
        private String username, password;
        private int    totalScore, quizzesTaken, highScore;

        User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public int compareTo(User o) {
            return Integer.compare(this.highScore, o.highScore);
        }

        void recordScore(int score) {
            totalScore += score;
            quizzesTaken++;
            if (score > highScore) highScore = score;
        }

        double getAvgScore() {
            return quizzesTaken == 0 ? 0.0 : (double) totalScore / quizzesTaken;
        }

        String getUsername()  { return username; }
        String getPassword()  { return password; }
        int    getHighScore() { return highScore; }
        int    getQuizzesTaken() { return quizzesTaken; }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO2 — ADT 1: SINGLY LINKED LIST
    //  Used to store the MASTER QUESTION LIST
    //
    //  insertFront  O(1)  |  insertEnd O(n)  |  deleteByIndex O(n)
    //  search O(n)  |  reverse O(n)  |  detectCycle O(n) Floyd's
    // ════════════════════════════════════════════════════════════════
    static class SinglyLinkedList<T> {
        static class Node<T> {
            T       data;
            Node<T> next;
            Node(T data) { this.data = data; }
        }

        private Node<T> head;
        private int     size;

        void insertFront(T data) {              // O(1)
            Node<T> node = new Node<>(data);
            node.next = head; head = node; size++;
        }

        void insertEnd(T data) {               // O(n)
            Node<T> node = new Node<>(data);
            if (head == null) { head = node; size++; return; }
            Node<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node; size++;
        }

        boolean deleteByIndex(int index) {     // O(n)
            if (head == null || index < 0 || index >= size) return false;
            if (index == 0) { head = head.next; size--; return true; }
            Node<T> cur = head;
            for (int i = 0; i < index - 1; i++) cur = cur.next;
            cur.next = cur.next.next; size--;
            return true;
        }

        Node<T> search(T data) {               // O(n)
            Node<T> cur = head;
            while (cur != null) {
                if (cur.data.equals(data)) return cur;
                cur = cur.next;
            }
            return null;
        }

        void reverse() {                       // O(n) - three-pointer
            Node<T> prev = null, cur = head, next;
            while (cur != null) {
                next = cur.next; cur.next = prev; prev = cur; cur = next;
            }
            head = prev;
        }

        boolean detectCycle() {               // O(n) - Floyd's algorithm
            Node<T> slow = head, fast = head;
            while (fast != null && fast.next != null) {
                slow = slow.next; fast = fast.next.next;
                if (slow == fast) return true;
            }
            return false;
        }

        void traverse(java.util.function.Consumer<T> action) {
            Node<T> cur = head;
            while (cur != null) { action.accept(cur.data); cur = cur.next; }
        }

        Object[] toArray() {
            Object[] arr = new Object[size];
            Node<T> cur = head;
            for (int i = 0; i < size; i++) { arr[i] = cur.data; cur = cur.next; }
            return arr;
        }

        Node<T> getHead() { return head; }
        int     size()    { return size; }
        boolean isEmpty() { return size == 0; }

        void displayInfo() {
            System.out.println("  [SinglyLinkedList] size=" + size
                + " | insertFront O(1) | insertEnd O(n) | search O(n) | reverse O(n) | detectCycle O(n)");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO2 — ADT 2: DOUBLY LINKED LIST
    //  Used to store REGISTERED USERS — bidirectional traversal
    //
    //  insertFront O(1)  |  insertEnd O(1) tail ptr  |  delete O(n)
    //  traverseBack O(n)  |  reverse O(n)
    // ════════════════════════════════════════════════════════════════
    static class DoublyLinkedList<T> {
        static class Node<T> {
            T       data;
            Node<T> prev, next;
            Node(T data) { this.data = data; }
        }

        private Node<T> head, tail;
        private int     size;

        void insertFront(T data) {             // O(1)
            Node<T> node = new Node<>(data);
            if (head == null) { head = tail = node; size++; return; }
            node.next = head; head.prev = node; head = node; size++;
        }

        void insertEnd(T data) {               // O(1) - tail pointer
            Node<T> node = new Node<>(data);
            if (tail == null) { head = tail = node; size++; return; }
            tail.next = node; node.prev = tail; tail = node; size++;
        }

        boolean delete(T data) {               // O(n)
            Node<T> cur = head;
            while (cur != null) {
                if (cur.data.equals(data)) {
                    if (cur.prev != null) cur.prev.next = cur.next; else head = cur.next;
                    if (cur.next != null) cur.next.prev = cur.prev; else tail = cur.prev;
                    size--; return true;
                }
                cur = cur.next;
            }
            return false;
        }

        void traverseForward(java.util.function.Consumer<T> action) {
            Node<T> cur = head;
            while (cur != null) { action.accept(cur.data); cur = cur.next; }
        }

        void traverseBackward(java.util.function.Consumer<T> action) {
            Node<T> cur = tail;
            while (cur != null) { action.accept(cur.data); cur = cur.prev; }
        }

        void reverse() {                       // O(n)
            Node<T> cur = head, tmp;
            while (cur != null) {
                tmp = cur.prev; cur.prev = cur.next; cur.next = tmp; cur = cur.prev;
            }
            tmp = head; head = tail; tail = tmp;
        }

        int     size()    { return size; }
        boolean isEmpty() { return size == 0; }

        void displayInfo() {
            System.out.println("  [DoublyLinkedList] size=" + size
                + " | insertEnd O(1) tail ptr | delete O(n) | bidirectional traversal O(n)");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO2 — ADT 3: CIRCULAR LINKED LIST
    //  Used to cycle through QUIZ CATEGORIES infinitely
    //
    //  insert O(1)  |  delete O(n)  |  rotate O(1)  |  traverseN O(n)
    // ════════════════════════════════════════════════════════════════
    static class CircularLinkedList<T> {
        static class Node<T> {
            T       data;
            Node<T> next;
            Node(T data) { this.data = data; }
        }

        private Node<T> tail; // tail.next == head
        private int     size;

        void insert(T data) {                  // O(1)
            Node<T> node = new Node<>(data);
            if (tail == null) {
                tail = node; tail.next = tail;
            } else {
                node.next = tail.next; tail.next = node; tail = node;
            }
            size++;
        }

        boolean delete(T data) {               // O(n)
            if (tail == null) return false;
            Node<T> cur = tail.next, prev = tail;
            do {
                if (cur.data.equals(data)) {
                    if (size == 1) { tail = null; size--; return true; }
                    prev.next = cur.next;
                    if (cur == tail) tail = prev;
                    size--; return true;
                }
                prev = cur; cur = cur.next;
            } while (cur != tail.next);
            return false;
        }

        void traverseN(int n, java.util.function.Consumer<T> action) { // O(n)
            if (tail == null) return;
            Node<T> cur = tail.next;
            for (int i = 0; i < n; i++) { action.accept(cur.data); cur = cur.next; }
        }

        void rotate() { if (tail != null) tail = tail.next; } // O(1)

        T       getHead()  { return tail == null ? null : tail.next.data; }
        int     size()     { return size; }
        boolean isEmpty()  { return size == 0; }

        void displayInfo() {
            System.out.println("  [CircularLinkedList] size=" + size
                + " | insert O(1) | rotate O(1) | delete O(n) | tail.next = head always");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO3 — STACK (Array-based)
    //  Application: UNDO LAST ANSWER during a quiz
    //
    //  push O(1)  |  pop O(1)  |  peek O(1)
    // ════════════════════════════════════════════════════════════════
    static class Stack<T> {
        private Object[] data;
        private int      top, capacity;

        Stack(int capacity) {
            this.capacity = capacity;
            data = new Object[capacity];
            top  = -1;
        }

        void push(T item) {                    // O(1)
            if (top == capacity - 1) throw new RuntimeException("Stack Overflow");
            data[++top] = item;
        }

        @SuppressWarnings("unchecked")
        T pop() {                              // O(1)
            if (isEmpty()) throw new RuntimeException("Stack Underflow");
            return (T) data[top--];
        }

        @SuppressWarnings("unchecked")
        T peek() { return isEmpty() ? null : (T) data[top]; } // O(1)

        boolean isEmpty() { return top == -1; }
        int     size()    { return top + 1; }

        void displayInfo() {
            System.out.println("  [Stack] size=" + size() + "/" + capacity
                + " | push/pop/peek O(1) | Application: Undo Answer");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO3 — QUEUE (LinkedList-based)
    //  Application: MANAGE QUIZ ATTEMPT ORDER (FIFO)
    //
    //  enqueue O(1)  |  dequeue O(1)
    // ════════════════════════════════════════════════════════════════
    static class MyQueue<T> {
        private LinkedList<T> list = new LinkedList<>();

        void enqueue(T item) { list.addLast(item); }  // O(1)

        T dequeue() {                                  // O(1)
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            return list.removeFirst();
        }

        T       peek()    { return list.isEmpty() ? null : list.getFirst(); }
        boolean isEmpty() { return list.isEmpty(); }
        int     size()    { return list.size(); }

        void displayInfo() {
            System.out.println("  [Queue] size=" + size()
                + " | enqueue/dequeue O(1) | Application: Attempt Queue (FIFO)");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO3 — CIRCULAR QUEUE (Array-based, fixed size)
    //  Application: FIXED-SIZE ATTEMPT BUFFER
    //
    //  enqueue O(1)  |  dequeue O(1)  — wraps around, no shifting
    // ════════════════════════════════════════════════════════════════
    static class CircularQueue<T> {
        private Object[] data;
        private int front, rear, size, capacity;

        CircularQueue(int capacity) {
            this.capacity = capacity;
            data = new Object[capacity];
            front = rear = size = 0;
        }

        void enqueue(T item) {                 // O(1)
            if (isFull()) throw new RuntimeException("Circular Queue Full");
            data[rear] = item;
            rear = (rear + 1) % capacity;
            size++;
        }

        @SuppressWarnings("unchecked")
        T dequeue() {                          // O(1)
            if (isEmpty()) throw new RuntimeException("Circular Queue Empty");
            T val = (T) data[front];
            front = (front + 1) % capacity;
            size--;
            return val;
        }

        boolean isEmpty() { return size == 0; }
        boolean isFull()  { return size == capacity; }
        int     size()    { return size; }

        void displayInfo() {
            System.out.println("  [CircularQueue] size=" + size + "/" + capacity
                + " | enqueue/dequeue O(1) | No shifting — index wraps via modulo");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO3 — DEQUE (Double-Ended Queue)
    //  Application: NAVIGATE FORWARD/BACKWARD between questions
    //
    //  addFront/addRear/removeFront/removeRear  all O(1)
    // ════════════════════════════════════════════════════════════════
    static class MyDeque<T> {
        private ArrayDeque<T> deque = new ArrayDeque<>();

        void addFront(T item)  { deque.addFirst(item); }   // O(1)
        void addRear(T item)   { deque.addLast(item); }    // O(1)
        T    removeFront()     { return deque.isEmpty() ? null : deque.removeFirst(); } // O(1)
        T    removeRear()      { return deque.isEmpty() ? null : deque.removeLast(); }  // O(1)
        T    peekFront()       { return deque.isEmpty() ? null : deque.peekFirst(); }
        T    peekRear()        { return deque.isEmpty() ? null : deque.peekLast(); }
        boolean isEmpty()      { return deque.isEmpty(); }
        int     size()         { return deque.size(); }

        void displayInfo() {
            System.out.println("  [Deque] size=" + size()
                + " | addFront/Rear O(1) | removeFront/Rear O(1) | Application: Question Navigation");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO5 — MAX HEAP (Priority Queue for Leaderboard)
    //  Highest scoring user always at root (index 1)
    //
    //  insert O(log n)  |  extractMax O(log n)  |  peekMax O(1)
    // ════════════════════════════════════════════════════════════════
    static class MaxHeap {
        private User[] heap;
        private int    size, capacity;

        MaxHeap(int capacity) {
            this.capacity = capacity;
            this.heap     = new User[capacity + 1]; // 1-indexed
        }

        private int parent(int i) { return i / 2; }
        private int left(int i)   { return 2 * i; }
        private int right(int i)  { return 2 * i + 1; }

        private void swap(int i, int j) {
            User tmp = heap[i]; heap[i] = heap[j]; heap[j] = tmp;
        }

        void insert(User user) {               // O(log n)
            if (size == capacity) {
                capacity *= 2;
                User[] bigger = new User[capacity + 1];
                System.arraycopy(heap, 0, bigger, 0, heap.length);
                heap = bigger;
            }
            heap[++size] = user;
            heapifyUp(size);
        }

        private void heapifyUp(int i) {        // Bubble up O(log n)
            while (i > 1 && heap[parent(i)].getHighScore() < heap[i].getHighScore()) {
                swap(i, parent(i)); i = parent(i);
            }
        }

        User extractMax() {                    // O(log n)
            if (size == 0) return null;
            User max = heap[1];
            heap[1]  = heap[size--];
            heapifyDown(1);
            return max;
        }

        private void heapifyDown(int i) {      // Bubble down O(log n)
            int largest = i, l = left(i), r = right(i);
            if (l <= size && heap[l].getHighScore() > heap[largest].getHighScore()) largest = l;
            if (r <= size && heap[r].getHighScore() > heap[largest].getHighScore()) largest = r;
            if (largest != i) { swap(i, largest); heapifyDown(largest); }
        }

        User peekMax() { return size == 0 ? null : heap[1]; } // O(1)

        void update(User user) {               // O(log n)
            for (int i = 1; i <= size; i++) {
                if (heap[i].getUsername().equals(user.getUsername())) {
                    heap[i] = user; heapifyUp(i); heapifyDown(i); return;
                }
            }
            insert(user);
        }

        void displayLeaderboard(int k) {
            System.out.println("\n  ╔═══════════════════════════════════════════════╗");
            System.out.println("  ║          LEADERBOARD  (Max Heap)              ║");
            System.out.println("  ╠═══════════════════════════════════════════════╣");
            System.out.printf ("  ║  %-4s  %-15s  %9s  %8s   ║%n","Rank","Username","HighScore","Avg");
            System.out.println("  ╠═══════════════════════════════════════════════╣");

            // Clone heap into temp — don't destroy original
            MaxHeap tmp = new MaxHeap(size + 1);
            for (int i = 1; i <= size; i++) tmp.insert(heap[i]);

            int rank = 1;
            while (rank <= k && !tmp.isEmpty()) {
                User u = tmp.extractMax();
                System.out.printf("  ║  %-4d  %-15s  %9d  %8.1f   ║%n",
                        rank++, u.getUsername(), u.getHighScore(), u.getAvgScore());
            }
            if (size == 0) System.out.println("  ║  No scores yet. Take a quiz first!            ║");
            System.out.println("  ╚═══════════════════════════════════════════════╝");
            System.out.println("  [MaxHeap] insert O(log n) | extractMax O(log n) | peekMax O(1)");
        }

        int     size()    { return size; }
        boolean isEmpty() { return size == 0; }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO4 — CUSTOM HASH TABLE (Separate Chaining)
    //  Maps questionId -> difficulty using multiplication hash method
    //  Rehashes when loadFactor > 0.75
    //
    //  put/get/delete  O(1) avg  |  O(n) worst (all keys collide)
    // ════════════════════════════════════════════════════════════════
    static class HashTable {
        private LinkedList<int[]>[] buckets; // [key, value] pairs
        private int capacity, size;

        @SuppressWarnings("unchecked")
        HashTable(int capacity) {
            this.capacity = capacity;
            this.buckets  = new LinkedList[capacity];
        }

        private int hash(int key) {           // Multiplication method
            double A = 0.6180339887;          // (sqrt(5)-1)/2  Knuth's constant
            return (int)(capacity * ((key * A) % 1));
        }

        void put(int key, int value) {        // O(1) avg
            int idx = hash(key);
            if (buckets[idx] == null) buckets[idx] = new LinkedList<>();
            for (int[] pair : buckets[idx]) {
                if (pair[0] == key) { pair[1] = value; return; }
            }
            buckets[idx].add(new int[]{key, value});
            size++;
            if ((float) size / capacity > 0.75f) rehash();
        }

        int get(int key) {                    // O(1) avg
            int idx = hash(key);
            if (buckets[idx] == null) return -1;
            for (int[] pair : buckets[idx]) if (pair[0] == key) return pair[1];
            return -1;
        }

        boolean delete(int key) {             // O(1) avg
            int idx = hash(key);
            if (buckets[idx] == null) return false;
            Iterator<int[]> it = buckets[idx].iterator();
            while (it.hasNext()) { if (it.next()[0] == key) { it.remove(); size--; return true; } }
            return false;
        }

        boolean contains(int key) { return get(key) != -1; }

        @SuppressWarnings("unchecked")
        private void rehash() {
            int oldCap = capacity; capacity *= 2;
            LinkedList<int[]>[] old = buckets;
            buckets = new LinkedList[capacity]; size = 0;
            for (LinkedList<int[]> b : old) {
                if (b == null) continue;
                for (int[] pair : b) put(pair[0], pair[1]);
            }
        }

        void displayInfo() {
            System.out.printf("  [Custom HashTable] size=%d | capacity=%d | loadFactor=%.2f | collision=chaining%n",
                    size, capacity, (float) size / capacity);
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO1 — SORTING ALGORITHMS (sort Question[] by difficulty)
    // ════════════════════════════════════════════════════════════════
    static class Sorting {

        /** Bubble Sort  — Best O(n) with early-exit | Avg/Worst O(n²) | Stable */
        static Question[] bubbleSort(Question[] arr) {
            Question[] a = arr.clone();
            int n = a.length;
            for (int i = 0; i < n - 1; i++) {
                boolean swapped = false;
                for (int j = 0; j < n - i - 1; j++) {
                    if (a[j].getDifficulty() > a[j+1].getDifficulty()) {
                        Question tmp = a[j]; a[j] = a[j+1]; a[j+1] = tmp; swapped = true;
                    }
                }
                if (!swapped) break; // early exit -> O(n) best case
            }
            return a;
        }

        /** Selection Sort  — Always O(n²) | Min swaps O(n) | Not stable */
        static Question[] selectionSort(Question[] arr) {
            Question[] a = arr.clone();
            int n = a.length;
            for (int i = 0; i < n - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < n; j++)
                    if (a[j].getDifficulty() < a[minIdx].getDifficulty()) minIdx = j;
                Question tmp = a[i]; a[i] = a[minIdx]; a[minIdx] = tmp;
            }
            return a;
        }

        /** Insertion Sort  — O(n) best (sorted input) | O(n²) worst | Stable */
        static Question[] insertionSort(Question[] arr) {
            Question[] a = arr.clone();
            int n = a.length;
            for (int i = 1; i < n; i++) {
                Question key = a[i]; int j = i - 1;
                while (j >= 0 && a[j].getDifficulty() > key.getDifficulty()) {
                    a[j+1] = a[j]; j--;
                }
                a[j+1] = key;
            }
            return a;
        }

        /** Merge Sort  — O(n log n) all cases | Stable | O(n) extra space */
        static Question[] mergeSort(Question[] arr) {
            Question[] a = arr.clone();
            mergeSortHelper(a, 0, a.length - 1);
            return a;
        }

        private static void mergeSortHelper(Question[] a, int left, int right) {
            if (left >= right) return;
            int mid = left + (right - left) / 2;
            mergeSortHelper(a, left, mid);
            mergeSortHelper(a, mid + 1, right);
            merge(a, left, mid, right);
        }

        private static void merge(Question[] a, int left, int mid, int right) {
            int n1 = mid - left + 1, n2 = right - mid;
            Question[] L = new Question[n1], R = new Question[n2];
            System.arraycopy(a, left,    L, 0, n1);
            System.arraycopy(a, mid + 1, R, 0, n2);
            int i = 0, j = 0, k = left;
            while (i < n1 && j < n2)
                a[k++] = L[i].getDifficulty() <= R[j].getDifficulty() ? L[i++] : R[j++];
            while (i < n1) a[k++] = L[i++];
            while (j < n2) a[k++] = R[j++];
        }

        /** Quick Sort  — O(n log n) avg | O(n²) worst (bad pivot) | Lomuto partition */
        static Question[] quickSort(Question[] arr) {
            Question[] a = arr.clone();
            quickSortHelper(a, 0, a.length - 1);
            return a;
        }

        private static void quickSortHelper(Question[] a, int low, int high) {
            if (low < high) {
                int pi = partition(a, low, high);
                quickSortHelper(a, low, pi - 1);
                quickSortHelper(a, pi + 1, high);
            }
        }

        private static int partition(Question[] a, int low, int high) {
            int pivot = a[high].getDifficulty(), i = low - 1;
            for (int j = low; j < high; j++) {
                if (a[j].getDifficulty() <= pivot) {
                    i++; Question tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
            }
            Question tmp = a[i+1]; a[i+1] = a[high]; a[high] = tmp;
            return i + 1;
        }

        /** Benchmark all 5 sorting algorithms with runtime */
        static void benchmark(Question[] questions) {
            System.out.println("\n  ── CO1: Sorting Algorithm Benchmark ────────────────────────────────");
            System.out.printf ("  %-16s | %-12s | %-12s | %-12s | %-9s | Time(us)%n",
                    "Algorithm","Best","Average","Worst","Space");
            System.out.println("  " + "-".repeat(80));

            String[][] info = {
                {"Bubble Sort",    "Omega(n)",      "Theta(n^2)",     "O(n^2)",      "O(1)"},
                {"Selection Sort", "Omega(n^2)",    "Theta(n^2)",     "O(n^2)",      "O(1)"},
                {"Insertion Sort", "Omega(n)",      "Theta(n^2)",     "O(n^2)",      "O(1)"},
                {"Merge Sort",     "Omega(n log n)","Theta(n log n)", "O(n log n)",  "O(n)"},
                {"Quick Sort",     "Omega(n log n)","Theta(n log n)", "O(n^2)",      "O(log n)"},
            };

            long[] times = {
                time(() -> bubbleSort(questions)),
                time(() -> selectionSort(questions)),
                time(() -> insertionSort(questions)),
                time(() -> mergeSort(questions)),
                time(() -> quickSort(questions)),
            };

            for (int i = 0; i < info.length; i++)
                System.out.printf("  %-16s | %-12s | %-12s | %-12s | %-9s | %d us%n",
                        info[i][0], info[i][1], info[i][2], info[i][3], info[i][4], times[i]);
        }

        private static long time(Runnable r) {
            long start = System.nanoTime();
            for (int i = 0; i < 200; i++) r.run();
            return (System.nanoTime() - start) / 200_000;
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO1 — SEARCHING ALGORITHMS
    // ════════════════════════════════════════════════════════════════
    static class Searching {

        /** Linear Search by keyword  — O(n) — no sort needed */
        static List<Integer> linearSearchAll(Question[] q, String keyword) {
            String lower = keyword.toLowerCase();
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < q.length; i++) {
                if (q[i].getText().toLowerCase().contains(lower)
                 || q[i].getCategory().toLowerCase().contains(lower))
                    res.add(i);
            }
            return res;
        }

        /** Binary Search by difficulty on sorted array  — O(log n) */
        static int binarySearchByDifficulty(Question[] sorted, int diff) {
            int low = 0, high = sorted.length - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2; // avoids integer overflow
                int d   = sorted[mid].getDifficulty();
                if      (d == diff) return mid;
                else if (d < diff)  low  = mid + 1;
                else                high = mid - 1;
            }
            return -1;
        }

        /** Binary Search by ID on ID-sorted array  — O(log n) */
        static int binarySearchById(Question[] sorted, int id) {
            int low = 0, high = sorted.length - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                int cid = sorted[mid].getId();
                if      (cid == id) return mid;
                else if (cid < id)  low  = mid + 1;
                else                high = mid - 1;
            }
            return -1;
        }

        /** Benchmark Linear vs Binary search */
        static void benchmark(Question[] questions, String keyword, int targetDiff) {
            System.out.println("\n  ── CO1: Search Algorithm Benchmark ─────────────────────────────────");

            long t1 = System.nanoTime();
            List<Integer> r1 = linearSearchAll(questions, keyword);
            long t2 = System.nanoTime();

            Question[] sorted = Sorting.mergeSort(questions);
            long t3 = System.nanoTime();
            int  r2 = binarySearchByDifficulty(sorted, targetDiff);
            long t4 = System.nanoTime();

            System.out.printf("  %-20s | Found: %-3d | Time: %7d ns | Omega(1) best | O(n) worst%n",
                    "Linear Search", r1.size(), (t2 - t1));
            System.out.printf("  %-20s | Found: %-3d | Time: %7d ns | Omega(1) best | O(log n) worst%n",
                    "Binary Search", r2 == -1 ? 0 : 1, (t4 - t3));
            System.out.println();
            System.out.println("  Key insight: Binary Search is faster but needs pre-sorted data.");
            System.out.println("  Trade-off  : Sort cost O(n log n) + search O(log n) vs O(n) linear.");
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO3 — QUIZ ENGINE (Uses Stack + Deque + Queue during quiz)
    // ════════════════════════════════════════════════════════════════
    static class QuizEngine {
        private User                    user;
        private Question[]              questions;
        private Stack<Character>        answerStack;        // CO3: undo answer
        private Stack<Integer>          indexStack;         // CO3: undo index
        private MyDeque<Integer>        navDeque;           // CO3: navigation
        private Map<Integer, Character> answers;            // CO4: HashMap
        private int                     currentIdx;
        private boolean                 quizActive;
        private Scanner                 sc;

        QuizEngine(User user, Question[] questions, Scanner sc) {
            this.user        = user;
            this.questions   = questions;
            this.sc          = sc;
            this.answerStack = new Stack<>(questions.length + 5);
            this.indexStack  = new Stack<>(questions.length + 5);
            this.navDeque    = new MyDeque<>();
            this.answers     = new HashMap<>();
            this.currentIdx  = 0;
            this.quizActive  = false;
            // Load question indices into Deque for navigation
            for (int i = 0; i < questions.length; i++) navDeque.addRear(i);
        }

        int startQuiz() {
            quizActive = true;
            System.out.println("\n  ╔══════════════════════════════════════════════════╗");
            System.out.println("  ║  QUIZ STARTED — User: " + user.getUsername());
            System.out.println("  ║  Total Questions : " + questions.length);
            System.out.println("  ║  DSA in action:");
            System.out.println("  ║    Stack -> U (Undo last answer)      O(1)");
            System.out.println("  ║    Deque -> N (Next) / P (Prev)       O(1)");
            System.out.println("  ║    HashMap -> stores your answers     O(1)");
            System.out.println("  ╠══════════════════════════════════════════════════╣");
            System.out.println("  ║  A/B/C/D = Answer | U = Undo | N = Next         ║");
            System.out.println("  ║  P = Prev | Q = Finish & Show Score             ║");
            System.out.println("  ╚══════════════════════════════════════════════════╝");

            while (quizActive && currentIdx < questions.length) {
                displayQuestion();
                processInput();
            }
            return showResults();
        }

        private void displayQuestion() {
            System.out.println();
            System.out.println("  ── Question " + (currentIdx + 1) + "/" + questions.length + " ──");
            questions[currentIdx].display();
            Character ans = answers.get(questions[currentIdx].getId());
            System.out.println("  Your answer: " + (ans != null ? ans : "(not answered)"));
            System.out.println("  Undo stack depth: " + answerStack.size());
            System.out.print("  >> Choice (A/B/C/D | U | N | P | Q): ");
        }

        private void processInput() {
            String input = sc.nextLine().trim().toUpperCase();
            if (input.isEmpty()) return;
            char cmd = input.charAt(0);
            switch (cmd) {
                case 'A': case 'B': case 'C': case 'D':
                    answerStack.push(cmd);                                  // Stack push O(1)
                    indexStack.push(currentIdx);
                    answers.put(questions[currentIdx].getId(), cmd);        // HashMap put O(1)
                    System.out.println("  Recorded: " + cmd);
                    if (currentIdx < questions.length - 1) currentIdx++;
                    else { System.out.println("\n  All questions answered!"); quizActive = false; }
                    break;
                case 'U':
                    if (answerStack.isEmpty()) { System.out.println("  Nothing to undo."); break; }
                    char  undoneAns = answerStack.pop();                    // Stack pop O(1)
                    int   undoneIdx = indexStack.pop();
                    answers.remove(questions[undoneIdx].getId());
                    if (currentIdx > 0) currentIdx--;
                    System.out.println("  Undone answer '" + undoneAns + "' for Q" + (undoneIdx + 1));
                    System.out.println("  [Stack O(1) pop — undo depth: " + answerStack.size() + "]");
                    break;
                case 'N':
                    if (currentIdx < questions.length - 1) {
                        navDeque.addRear(currentIdx); currentIdx++;         // Deque O(1)
                    } else System.out.println("  Already at last question.");
                    break;
                case 'P':
                    if (currentIdx > 0) {
                        navDeque.addFront(currentIdx); currentIdx--;        // Deque O(1)
                    } else System.out.println("  Already at first question.");
                    break;
                case 'Q':
                    quizActive = false;
                    break;
                default:
                    System.out.println("  Invalid input.");
            }
        }

        private int showResults() {
            int correct = 0, total = questions.length;
            System.out.println("\n  ╔════════════════════════════════════════════════╗");
            System.out.println("  ║                 QUIZ RESULTS                   ║");
            System.out.println("  ╠════════════════════════════════════════════════╣");
            for (Question q : questions) {
                Character ua = answers.get(q.getId());
                boolean   ok = ua != null && ua == q.getCorrectOption();
                if (ok) correct++;
                System.out.printf("  Q%-3d | Your: %-2s | Correct: %c | %s%n",
                        q.getId(), ua != null ? String.valueOf(ua) : "-",
                        q.getCorrectOption(), ok ? "CORRECT" : "WRONG");
            }
            int score = total == 0 ? 0 : (int)((double) correct / total * 100);
            System.out.println("  ╠════════════════════════════════════════════════╣");
            System.out.printf ("  ║  Score: %d/%d  =>  %d%%%n", correct, total, score);
            System.out.println("  ╚════════════════════════════════════════════════╝");
            user.recordScore(score);
            return score;
        }
    }

    // ════════════════════════════════════════════════════════════════
    //  CO6 — QUIZ MANAGEMENT SYSTEM (Main Application Controller)
    //  Ties ALL CO1–CO5 together in one menu-driven system
    // ════════════════════════════════════════════════════════════════
    static class QuizManagementSystem {

        // CO2: Linked List ADTs
        private SinglyLinkedList<Question>  questionList  = new SinglyLinkedList<>();
        private DoublyLinkedList<User>      userList      = new DoublyLinkedList<>();
        private CircularLinkedList<String>  categoryWheel = new CircularLinkedList<>();

        // CO4: HashMap-based fast lookup
        private Map<Integer, Question> questionMap = new HashMap<>(); // O(1) by ID
        private Map<String,  User>     userMap     = new HashMap<>(); // O(1) by username

        // CO4: Custom Hash Table
        private HashTable customHashTable = new HashTable(32);

        // CO5: Max Heap for leaderboard
        private MaxHeap leaderboard = new MaxHeap(50);

        // CO3: Attempt queue
        private MyQueue<String>    attemptQueue    = new MyQueue<>();
        private CircularQueue<String> circAttempts = new CircularQueue<>(10);

        private User    loggedInUser;
        private Scanner sc = new Scanner(System.in);

        // ── Seed default data ─────────────────────────────────────
        QuizManagementSystem() {
            String[] cats = {"DSA", "Java", "OS", "Networks", "DBMS"};
            for (String c : cats) categoryWheel.insert(c);

            addUser("admin", "admin123");

            // Seed 15 sample questions
            seed("What is the time complexity of binary search?",
                    "O(n)","O(log n)","O(n log n)","O(1)",'B',1,"DSA");
            seed("Which data structure uses LIFO order?",
                    "Queue","Stack","Deque","Heap",'B',1,"DSA");
            seed("What does HashMap guarantee for get()?",
                    "O(log n)","O(n)","O(1) average","O(n^2)",'C',1,"Java");
            seed("Merge Sort worst case complexity is?",
                    "O(n^2)","O(n log n)","O(n)","O(log n)",'B',2,"DSA");
            seed("Floyd's cycle detection uses?",
                    "Two stacks","Slow & fast pointers","Recursion only","BFS",'B',2,"DSA");
            seed("Which sorting algorithm is NOT stable?",
                    "Merge Sort","Bubble Sort","Quick Sort","Insertion Sort",'C',2,"DSA");
            seed("Dijkstra's algorithm uses which data structure?",
                    "Stack","Queue","Priority Queue","Deque",'C',3,"DSA");
            seed("In a Max Heap, insert has complexity:",
                    "O(1)","O(log n)","O(n)","O(n log n)",'B',2,"DSA");
            seed("Which collision resolution uses chaining?",
                    "Open addressing","Linear probing","Separate chaining","Quadratic probing",'C',3,"DSA");
            seed("A circular queue of size N can hold at most:",
                    "N elements","N-1 elements","N+1 elements","2N elements",'B',2,"DSA");
            seed("Insertion Sort best case is?",
                    "O(n^2)","O(n log n)","O(n)","O(1)",'C',1,"DSA");
            seed("Which data structure is used in BFS?",
                    "Stack","Queue","Heap","Tree",'B',2,"DSA");
            seed("What is the height of a complete binary tree with n nodes?",
                    "O(n)","O(log n)","O(n log n)","O(1)",'B',3,"DSA");
            seed("A doubly linked list node has how many pointers?",
                    "1","2","3","0",'B',1,"DSA");
            seed("Which Java class implements a Queue as FIFO?",
                    "Stack","TreeMap","LinkedList","PriorityQueue",'C',1,"Java");
        }

        private void seed(String t, String a, String b, String c, String d,
                          char cor, int diff, String cat) {
            addQuestion(new Question(t, a, b, c, d, cor, diff, cat));
        }

        private void addQuestion(Question q) {
            questionList.insertEnd(q);          // SinglyLinkedList O(n)
            questionMap.put(q.getId(), q);      // HashMap O(1)
            customHashTable.put(q.getId(), q.getDifficulty()); // Custom HashTable O(1)
        }

        private void addUser(String username, String password) {
            User u = new User(username, password);
            userList.insertEnd(u);              // DoublyLinkedList O(1)
            userMap.put(username, u);           // HashMap O(1)
        }

        // ════════════════════════════════════════════════════════
        //  MAIN MENU LOOP
        // ════════════════════════════════════════════════════════
        void run() {
            while (true) {
                if (loggedInUser == null) showAuthMenu();
                else                     showMainMenu();
            }
        }

        // ── Auth Menu ─────────────────────────────────────────
        private void showAuthMenu() {
            System.out.println("\n┌───────────────────────────────────────────────┐");
            System.out.println("│        DSA Quiz Builder & Management          │");
            System.out.println("├───────────────────────────────────────────────┤");
            System.out.println("│   1. Register                                 │");
            System.out.println("│   2. Login                                    │");
            System.out.println("│   3. Exit                                     │");
            System.out.println("└───────────────────────────────────────────────┘");
            System.out.print("  Choice: ");
            switch (readInt()) {
                case 1  -> registerMenu();
                case 2  -> loginMenu();
                case 3  -> { System.out.println("  Goodbye!"); System.exit(0); }
                default -> System.out.println("  Invalid choice.");
            }
        }

        private void registerMenu() {
            System.out.print("  New Username: ");
            String u = sc.nextLine().trim();
            System.out.print("  Password    : ");
            String p = sc.nextLine().trim();
            if (u.isEmpty() || p.isEmpty()) { System.out.println("  Cannot be empty."); return; }
            if (userMap.containsKey(u))     { System.out.println("  Username taken."); return; }
            addUser(u, p);
            System.out.println("  Registered! [DoublyLL.insertEnd O(1) + HashMap.put O(1)]");
        }

        private void loginMenu() {
            System.out.print("  Username: ");
            String u = sc.nextLine().trim();
            System.out.print("  Password: ");
            String p = sc.nextLine().trim();
            User found = userMap.get(u);        // O(1) HashMap lookup
            if (found == null || !found.getPassword().equals(p)) {
                System.out.println("  Invalid credentials.");
            } else {
                loggedInUser = found;
                System.out.println("  Welcome, " + u + "!  [HashMap.get O(1)]");
            }
        }

        // ── Main Menu ─────────────────────────────────────────
        private void showMainMenu() {
            System.out.println("\n╔═══════════════════════════════════════════════════╗");
            System.out.println("║  Logged in: " + String.format("%-37s","\""+loggedInUser.getUsername()+"\"") + "║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║  ── QUIZ ──                                       ║");
            System.out.println("║   1.  Take Quiz                                  ║");
            System.out.println("║   2.  View Leaderboard        (CO5: Max Heap)     ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║  ── QUESTION MANAGEMENT ──                        ║");
            System.out.println("║   3.  Add Question            (CO2: Linked List)  ║");
            System.out.println("║   4.  Remove Question         (CO4: HashMap)      ║");
            System.out.println("║   5.  View All Questions      (CO2: SinglyLL)     ║");
            System.out.println("║   6.  Search Questions        (CO1: Lin/Bin)      ║");
            System.out.println("║   7.  Sort Questions          (CO1: 5 Algorithms) ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║  ── DSA DEMONSTRATIONS ──                         ║");
            System.out.println("║   8.  Demo All Data Structures (CO2+CO3+CO4+CO5) ║");
            System.out.println("║   9.  Sorting Benchmark        (CO1)              ║");
            System.out.println("║   10. Search Benchmark         (CO1)              ║");
            System.out.println("║   11. Big-O / Omega / Theta Summary              ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║   12. Logout                                     ║");
            System.out.println("╚═══════════════════════════════════════════════════╝");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case  1 -> takeQuiz();
                case  2 -> leaderboard.displayLeaderboard(10);
                case  3 -> addQuestionMenu();
                case  4 -> removeQuestionMenu();
                case  5 -> viewAllQuestions();
                case  6 -> searchMenu();
                case  7 -> sortMenu();
                case  8 -> demoAllStructures();
                case  9 -> { Question[] a = getArr(); if (a.length>0) Sorting.benchmark(a); }
                case 10 -> searchBenchmark();
                case 11 -> showComplexitySummary();
                case 12 -> { loggedInUser = null; System.out.println("  Logged out."); }
                default -> System.out.println("  Invalid choice.");
            }
        }

        // ── Take Quiz ─────────────────────────────────────────
        private void takeQuiz() {
            if (questionList.isEmpty()) { System.out.println("  No questions available."); return; }

            System.out.println("\n  Select difficulty:");
            System.out.println("   1. Easy   2. Medium   3. Hard   4. Mixed (All)");
            System.out.print("  Choice: ");
            int diffChoice = readInt();

            Question[] all = getArr();
            Question[] filtered;

            if (diffChoice >= 1 && diffChoice <= 3) {
                final int diff = diffChoice;
                filtered = Arrays.stream(all)
                        .filter(q -> q.getDifficulty() == diff)
                        .toArray(Question[]::new);
            } else {
                filtered = all;
            }

            if (filtered.length == 0) {
                System.out.println("  No questions for that difficulty."); return;
            }

            System.out.print("  How many questions? (max " + filtered.length + "): ");
            int count = Math.max(1, Math.min(readInt(), filtered.length));

            // Shuffle questions
            List<Question> list = new ArrayList<>(Arrays.asList(filtered));
            Collections.shuffle(list);
            Question[] quiz = list.subList(0, count).toArray(new Question[0]);

            // CO3: Enqueue attempt
            attemptQueue.enqueue(loggedInUser.getUsername());
            if (!circAttempts.isFull())
                circAttempts.enqueue(loggedInUser.getUsername());

            // CO6: Start quiz engine (uses CO3 Stack + Deque + CO4 HashMap internally)
            QuizEngine engine = new QuizEngine(loggedInUser, quiz, sc);
            int score = engine.startQuiz();

            // CO5: Update leaderboard
            leaderboard.update(loggedInUser);
            System.out.println("  [CO5: MaxHeap.update O(log n) — Leaderboard refreshed]");
        }

        // ── Add Question ──────────────────────────────────────
        private void addQuestionMenu() {
            System.out.println("\n  ── Add New Question (CO2: SinglyLL + CO4: HashMap) ──");
            System.out.print("  Question text       : "); String text = sc.nextLine().trim();
            System.out.print("  Option A            : "); String a    = sc.nextLine().trim();
            System.out.print("  Option B            : "); String b    = sc.nextLine().trim();
            System.out.print("  Option C            : "); String c    = sc.nextLine().trim();
            System.out.print("  Option D            : "); String d    = sc.nextLine().trim();
            System.out.print("  Correct (A/B/C/D)   : ");
            String cor = sc.nextLine().trim().toUpperCase();
            if (cor.isEmpty()) { System.out.println("  Invalid."); return; }
            System.out.print("  Difficulty (1/2/3)  : "); int diff = readInt();
            System.out.print("  Category            : "); String cat = sc.nextLine().trim();
            if (diff < 1 || diff > 3) { System.out.println("  Difficulty must be 1, 2, or 3."); return; }
            Question q = new Question(text, a, b, c, d, cor.charAt(0), diff, cat);
            addQuestion(q);
            System.out.println("  Question added! ID=" + q.getId());
            System.out.println("  [SinglyLL.insertEnd O(n) + HashMap.put O(1) + CustomHashTable.put O(1)]");
        }

        // ── Remove Question ───────────────────────────────────
        private void removeQuestionMenu() {
            System.out.print("  Enter Question ID to remove: ");
            int id = readInt();
            if (!questionMap.containsKey(id)) {
                System.out.println("  Not found. [HashMap.containsKey O(1)]"); return;
            }
            Question q = questionMap.remove(id);   // HashMap O(1)
            customHashTable.delete(id);             // Custom HashTable O(1)
            // Remove from SinglyLinkedList O(n)
            SinglyLinkedList.Node<Question> node = questionList.getHead();
            int idx = 0;
            while (node != null) {
                if (node.data.getId() == id) { questionList.deleteByIndex(idx); break; }
                node = node.next; idx++;
            }
            System.out.println("  Removed Q" + id + ": " + q.getText());
            System.out.println("  [HashMap.remove O(1) + SinglyLL.deleteByIndex O(n)]");
        }

        // ── View All Questions ────────────────────────────────
        private void viewAllQuestions() {
            if (questionList.isEmpty()) { System.out.println("  No questions."); return; }
            System.out.println("\n  ── All Questions (CO2: SinglyLinkedList traversal O(n)) ──");
            final int[] cnt = {0};
            questionList.traverse(q -> { cnt[0]++; q.displayWithAnswer(); });
            System.out.println("  Total: " + cnt[0] + " questions.");
        }

        // ── Search ────────────────────────────────────────────
        private void searchMenu() {
            System.out.println("\n  Search by:");
            System.out.println("   1. Keyword     (CO1: Linear Search   O(n))");
            System.out.println("   2. Question ID (CO4: HashMap.get     O(1))");
            System.out.println("   3. Difficulty  (CO1: Binary Search   O(log n))");
            System.out.print("  Choice: ");

            Question[] arr = getArr();

            switch (readInt()) {
                case 1 -> {
                    System.out.print("  Enter keyword: ");
                    String kw = sc.nextLine().trim();
                    List<Integer> idxs = Searching.linearSearchAll(arr, kw);  // CO1
                    if (idxs.isEmpty()) System.out.println("  No results.");
                    else { System.out.println("  Found " + idxs.size() + " result(s):"); idxs.forEach(i -> arr[i].display()); }
                    System.out.println("  [CO1: Linear Search O(n)]");
                }
                case 2 -> {
                    System.out.print("  Question ID: ");
                    int id = readInt();
                    Question q = questionMap.get(id);   // CO4: O(1)
                    if (q == null) System.out.println("  Not found.");
                    else q.displayWithAnswer();
                    System.out.println("  [CO4: HashMap.get O(1)]");
                }
                case 3 -> {
                    System.out.print("  Difficulty (1=Easy 2=Medium 3=Hard): ");
                    int diff       = readInt();
                    Question[] srt = Sorting.mergeSort(arr);              // CO1: sort first
                    int idx        = Searching.binarySearchByDifficulty(srt, diff); // CO1: binary search
                    if (idx == -1) System.out.println("  No questions with that difficulty.");
                    else { System.out.println("  First match:"); srt[idx].displayWithAnswer(); }
                    System.out.println("  [CO1: MergeSort O(n log n) + BinarySearch O(log n)]");
                }
                default -> System.out.println("  Invalid choice.");
            }
        }

        // ── Sort ──────────────────────────────────────────────
        private void sortMenu() {
            System.out.println("\n  CO1: Sort questions by difficulty:");
            System.out.println("   1. Bubble Sort    — O(n^2) | stable");
            System.out.println("   2. Selection Sort — O(n^2) | not stable, min swaps");
            System.out.println("   3. Insertion Sort — O(n^2) | O(n) best case");
            System.out.println("   4. Merge Sort     — O(n log n) | stable, O(n) space");
            System.out.println("   5. Quick Sort     — O(n log n) avg | O(n^2) worst");
            System.out.print("  Choice: ");

            Question[] arr = getArr();
            Question[] sorted; String name, bigO;

            switch (readInt()) {
                case 1 -> { sorted=Sorting.bubbleSort(arr);    name="Bubble Sort";    bigO="O(n^2)"; }
                case 2 -> { sorted=Sorting.selectionSort(arr); name="Selection Sort"; bigO="O(n^2)"; }
                case 3 -> { sorted=Sorting.insertionSort(arr); name="Insertion Sort"; bigO="O(n^2)"; }
                case 4 -> { sorted=Sorting.mergeSort(arr);     name="Merge Sort";     bigO="O(n log n)"; }
                case 5 -> { sorted=Sorting.quickSort(arr);     name="Quick Sort";     bigO="O(n log n) avg"; }
                default -> { System.out.println("  Invalid."); return; }
            }

            System.out.println("\n  ── Sorted by " + name + " [" + bigO + "] ──");
            for (Question q : sorted)
                System.out.printf("  [%-6s] [Q%-3d] %s%n", q.getDiffLabel(), q.getId(), q.getText());
        }

        // ── Demo All Data Structures ──────────────────────────
        private void demoAllStructures() {
            System.out.println("\n  ══════════════════════════════════════════════════════");
            System.out.println("   LIVE DSA DEMO — All Structures in Action");
            System.out.println("  ══════════════════════════════════════════════════════");

            // CO2: SinglyLinkedList
            System.out.println("\n  CO2 ─ [1] SinglyLinkedList<Question>:");
            questionList.displayInfo();
            System.out.println("       Cycle detected (Floyd's): " + questionList.detectCycle());

            // CO2: DoublyLinkedList
            System.out.println("\n  CO2 ─ [2] DoublyLinkedList<User>:");
            userList.displayInfo();
            System.out.print("       Forward  : ");
            userList.traverseForward(u -> System.out.print(u.getUsername() + " -> "));
            System.out.println("NULL");
            System.out.print("       Backward : ");
            userList.traverseBackward(u -> System.out.print(u.getUsername() + " -> "));
            System.out.println("NULL");

            // CO2: CircularLinkedList
            System.out.println("\n  CO2 ─ [3] CircularLinkedList<Category>:");
            categoryWheel.displayInfo();
            System.out.print("       5 rotations: ");
            categoryWheel.traverseN(5, c -> System.out.print(c + " -> "));
            System.out.println("(wraps)");

            // CO3: Stack demo
            System.out.println("\n  CO3 ─ [4] Stack (Undo System):");
            Stack<Character> demoStack = new Stack<>(5);
            demoStack.push('A'); demoStack.push('B'); demoStack.push('C');
            demoStack.displayInfo();
            System.out.println("       Push A, B, C | peek=" + demoStack.peek()
                + " | pop=" + demoStack.pop() + " | size after pop=" + demoStack.size());

            // CO3: Queue demo
            System.out.println("\n  CO3 ─ [5] Queue (Attempt Queue FIFO):");
            MyQueue<String> demoQ = new MyQueue<>();
            demoQ.enqueue("alice"); demoQ.enqueue("bob"); demoQ.enqueue("carol");
            demoQ.displayInfo();
            System.out.println("       Enqueued: alice, bob, carol | dequeue=" + demoQ.dequeue());

            // CO3: CircularQueue demo
            System.out.println("\n  CO3 ─ [6] CircularQueue (Fixed Buffer):");
            CircularQueue<String> cq = new CircularQueue<>(3);
            cq.enqueue("attempt1"); cq.enqueue("attempt2");
            cq.displayInfo();
            System.out.println("       rear=(front+" + cq.size() + ")%" + 3 + " | isFull=" + cq.isFull());

            // CO3: Deque demo
            System.out.println("\n  CO3 ─ [7] Deque (Question Navigation):");
            MyDeque<Integer> demoDeque = new MyDeque<>();
            demoDeque.addRear(1); demoDeque.addRear(2); demoDeque.addRear(3); demoDeque.addFront(0);
            demoDeque.displayInfo();
            System.out.println("       addFront(0), addRear(1,2,3) | peekFront=" + demoDeque.peekFront()
                + " | peekRear=" + demoDeque.peekRear());

            // CO4: HashMap stats
            System.out.println("\n  CO4 ─ [8] HashMap<Integer,Question> + HashMap<String,User>:");
            System.out.println("       Questions in HashMap: " + questionMap.size() + " | get/put O(1) avg");
            System.out.println("       Users in HashMap    : " + userMap.size()     + " | get/put O(1) avg");

            // CO4: Custom HashTable
            System.out.println("\n  CO4 ─ [9] Custom HashTable (multiplication hash + chaining):");
            customHashTable.displayInfo();

            // CO3 + CO4: Attempt Queue
            System.out.println("\n  CO3/CO4 ─ [10] Attempt Queue:");
            System.out.println("       Queued attempts: " + attemptQueue.size()
                + " | circBuffer size: " + circAttempts.size());

            // CO5: MaxHeap
            System.out.println("\n  CO5 ─ [11] MaxHeap (Leaderboard):");
            System.out.println("       Heap size: " + leaderboard.size());
            System.out.println("       Top scorer: " + (leaderboard.peekMax() != null ?
                    leaderboard.peekMax().getUsername() + " (score " + leaderboard.peekMax().getHighScore() + ")"
                    : "No scores yet — take a quiz first"));

            System.out.println("\n  ══════════════════════════════════════════════════════");
        }

        // ── Search Benchmark ──────────────────────────────────
        private void searchBenchmark() {
            Question[] arr = getArr();
            if (arr.length == 0) { System.out.println("  No questions."); return; }
            System.out.print("  Enter keyword for linear search test: ");
            String kw = sc.nextLine().trim();
            if (kw.isEmpty()) kw = "sort";
            Searching.benchmark(arr, kw, 2);
        }

        // ── Big-O Complexity Summary ──────────────────────────
        private void showComplexitySummary() {
            System.out.println("\n  ══════════════════════════════════════════════════════════════════════");
            System.out.println("   CO1: Big-O / Omega / Theta — Complete Complexity Reference");
            System.out.println("  ══════════════════════════════════════════════════════════════════════");
            System.out.println();
            System.out.println("  Notation Guide:");
            System.out.println("    O(f)     = Upper bound   — worst case   — 'no slower than'");
            System.out.println("    Omega(f) = Lower bound   — best case    — 'no faster than'");
            System.out.println("    Theta(f) = Tight bound   — exact order  — 'exactly as fast as'");
            System.out.println();
            System.out.printf ("  %-26s | %-13s | %-13s | %-13s | %s%n",
                    "Structure / Operation","Omega(best)","Theta(avg)","O(worst)","Notes");
            System.out.println("  " + "-".repeat(84));

            String[][] rows = {
                // CO2 Linked Lists
                {"CO2: SinglyLL insertFront",   "Omega(1)",      "Theta(1)",      "O(1)",       "Direct head pointer change"},
                {"CO2: SinglyLL insertEnd",     "Omega(n)",      "Theta(n)",      "O(n)",       "Traverse to tail"},
                {"CO2: SinglyLL search",        "Omega(1)",      "Theta(n)",      "O(n)",       "Linear scan"},
                {"CO2: SinglyLL reverse",       "Omega(n)",      "Theta(n)",      "O(n)",       "3-pointer technique"},
                {"CO2: SinglyLL detectCycle",   "Omega(n)",      "Theta(n)",      "O(n)",       "Floyd slow/fast pointers"},
                {"CO2: DoublyLL insertEnd",     "Omega(1)",      "Theta(1)",      "O(1)",       "Tail pointer maintained"},
                {"CO2: DoublyLL traverseBack",  "Omega(n)",      "Theta(n)",      "O(n)",       "via prev pointer"},
                {"CO2: CircularLL insert",      "Omega(1)",      "Theta(1)",      "O(1)",       "tail.next = head always"},
                {"CO2: CircularLL rotate",      "Omega(1)",      "Theta(1)",      "O(1)",       "tail = tail.next"},
                // CO3 Stack/Queue
                {"CO3: Stack push/pop/peek",    "Omega(1)",      "Theta(1)",      "O(1)",       "Array-based LIFO"},
                {"CO3: Queue enq/deq",          "Omega(1)",      "Theta(1)",      "O(1)",       "LinkedList FIFO"},
                {"CO3: CircularQueue enq/deq",  "Omega(1)",      "Theta(1)",      "O(1)",       "No shifting — modulo wrap"},
                {"CO3: Deque addFront/Rear",    "Omega(1)",      "Theta(1)",      "O(1)",       "ArrayDeque"},
                // CO4 Hashing
                {"CO4: HashMap get/put",        "Omega(1)",      "Theta(1)",      "O(n)*",      "*all keys collide (rare)"},
                {"CO4: Custom HashTable get",   "Omega(1)",      "Theta(1)",      "O(n)*",      "Multiplication hash"},
                // CO5 Heap
                {"CO5: MaxHeap insert",         "Omega(1)",      "Theta(log n)",  "O(log n)",   "Heapify up"},
                {"CO5: MaxHeap extractMax",     "Omega(log n)",  "Theta(log n)",  "O(log n)",   "Heapify down"},
                {"CO5: MaxHeap peekMax",        "Omega(1)",      "Theta(1)",      "O(1)",       "Root element"},
                // CO1 Sorting
                {"CO1: Bubble Sort",            "Omega(n)",      "Theta(n^2)",    "O(n^2)",     "Stable, early-exit"},
                {"CO1: Selection Sort",         "Omega(n^2)",    "Theta(n^2)",    "O(n^2)",     "Not stable, min swaps"},
                {"CO1: Insertion Sort",         "Omega(n)",      "Theta(n^2)",    "O(n^2)",     "Stable, great nearly sorted"},
                {"CO1: Merge Sort",             "Omega(n log n)","Theta(n log n)","O(n log n)", "Stable, O(n) extra space"},
                {"CO1: Quick Sort",             "Omega(n log n)","Theta(n log n)","O(n^2)",     "Not stable, pivot dependent"},
                // CO1 Searching
                {"CO1: Linear Search",          "Omega(1)",      "Theta(n)",      "O(n)",       "No sort needed"},
                {"CO1: Binary Search",          "Omega(1)",      "Theta(log n)",  "O(log n)",   "Requires sorted input"},
            };

            for (String[] row : rows)
                System.out.printf("  %-26s | %-13s | %-13s | %-13s | %s%n",
                        row[0], row[1], row[2], row[3], row[4]);

            System.out.println("\n  Recurrence Relations:");
            System.out.println("    Merge Sort : T(n) = 2T(n/2) + O(n)  => O(n log n)  by Master Theorem");
            System.out.println("    Quick Sort : T(n) = 2T(n/2) + O(n)  => O(n log n)  avg case");
            System.out.println("    Binary Srch: T(n) = T(n/2)  + O(1)  => O(log n)");
            System.out.println("    MaxHeap ins: T(n) = T(n/2)  + O(1)  => O(log n)    heapify path = tree height");
        }

        // ── Utilities ─────────────────────────────────────────
        private Question[] getArr() {
            Object[]   raw = questionList.toArray();
            Question[] arr = new Question[raw.length];
            for (int i = 0; i < raw.length; i++) arr[i] = (Question) raw[i];
            return arr;
        }

        private int readInt() {
            try   { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { return -1; }
        }
    }
}
