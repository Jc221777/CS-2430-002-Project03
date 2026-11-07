public class SuperMask {
    byte[] matches;

    SuperMask(int maxSize) {
        matches = new byte[maxSize / 8];
    }

    boolean match(int index) {
        return (matches[index / 8] & (1 << (index % 8))) > 0;
    }

    void add(int key) {
        int index = key;
        while (index < matches.length * 8) {
            matches[index / 8] |= (byte)(1 << (index % 8));
            index = (index + 1) | key;
        }
    }
}
