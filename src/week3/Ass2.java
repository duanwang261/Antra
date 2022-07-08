package week3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

interface SongCache {
    void recordSongPlays(String songId, int numPlays);
    int getPlaysForSong(String songId);
    List<String> getTopNSongsPlayed(int n);
}

public class Ass2 implements SongCache{

    private Map<String,Integer> map;
    private PriorityBlockingQueue<String> TopSong;

    public Ass2() {
        map = new ConcurrentHashMap<>();
        TopSong = new PriorityBlockingQueue<String>(15, (a,b) -> {
            if (map.get(b).equals(map.get(a))) {
                return a.compareTo(b);
            }
            return map.get(b) - map.get(a);
        });
    }
    @Override
    public void recordSongPlays(String songId, int numPlays) {
        map.put(songId, map.getOrDefault(songId, 0) + numPlays);
        TopSong.add(songId);
    }

    @Override
    public int getPlaysForSong(String songId) {
        if (!map.containsKey(songId)) {
            return -1;
        }
        return map.get(songId);
    }

    @Override
    public List<String> getTopNSongsPlayed(int n) {
        int size = Math.min(n, map.size());

        List<String> res = new ArrayList<>();
        Set<String> set = new HashSet<>();

        while (size >= 0) {
            String temp = TopSong.poll();
            if (set.contains(temp)) {
                continue;
            }
            set.add(temp);
            res.add(temp);
            size--;
        }

        for (String song : res) {
            TopSong.offer(song);
        }
        return res;
    }

    public static void main(String[] args) {
        SongCache cache = new Ass2();
//        cache.recordSongPlays("ID-1", 3);
//        cache.recordSongPlays("ID-1", 1);
//        cache.recordSongPlays("ID-2", 2);
//        cache.recordSongPlays("ID-3", 5);
//        assertThat(cache.getPlaysForSong("ID-1"), is(4));
//        assertThat(cache.getPlaysForSong("ID-9"), is(-1));
//        assertThat(cache.getTopNSongsPlayed(2), contains("ID-3",
//                "ID-1"));
//        assertThat(cache.getTopNSongsPlayed(0), is(empty()));

//        Thread t1 = new Thread(() -> {
//            cache.recordSongPlays("ID-1", 3);
//        });
//
//        Thread t2 = new Thread(() -> {
//            cache.recordSongPlays("ID-1", 1);
//        });
//        Thread t3 = new Thread(() -> {
//            cache.recordSongPlays("ID-2", 2);
//        });
//        Thread t4 = new Thread(() -> {
//            cache.recordSongPlays("ID-3", 5);
//        });
//        Thread t5 = new Thread(() -> {
//            System.out.println(cache.getPlaysForSong("ID-3"));
//        });
//
//
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
//        t5.start();

    }
}
