import java.util.*;

public class Main {

    static List<Integer>[] graph;
    static String[] words;
    static int N, Q;

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // START
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        Q = sc.nextInt();

        words = new String[N];
        for (int i = 0; i < N; i++) {
            words[i] = sc.next();
        }

        // Bygg grafen
        graph = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            graph[i] = new ArrayList<>();
        }

        buildGraph();

        // Map för snabb lookup (ord -> index)
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            indexMap.put(words[i], i);
        }

        // Queries
        for (int i = 0; i < Q; i++) {
            String start = sc.next();
            String end = sc.next();

            int result = bfs(indexMap.get(start), indexMap.get(end));

            if (result == -1) {
                System.out.println("Impossible");
            } else {
                System.out.println(result);
            }
        }

        sc.close();

        long endTime = System.nanoTime(); // STOP

        double timeMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("Körtid: " + timeMs + " ms");
    }

    //Bygg graf
    static void buildGraph() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j && hasEdge(words[i], words[j])) {
                    graph[i].add(j);
                }
            }
        }
    }

    //Kolla om edge finns
    static boolean hasEdge(String u, String v) {
        int[] count = new int[26];

        // sista 4 bokstäver i u
        for (int i = 1; i < 5; i++) {
            count[u.charAt(i) - 'a']++;
        }

        // räkna bort från v
        for (int i = 0; i < 5; i++) {
            count[v.charAt(i) - 'a']--;
        }

        // om något > 0 saknas bokstäver
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) return false;
        }

        return true;
    }

    //BFS
    static int bfs(int start, int end) {
        Queue<Integer> queue = new LinkedList<>();
        int[] dist = new int[N];
        Arrays.fill(dist, -1);

        queue.add(start);
        dist[start] = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            if (u == end) return dist[u];

            for (int v : graph[u]) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    queue.add(v);
                }
            }
        }

        return -1;
    }
}