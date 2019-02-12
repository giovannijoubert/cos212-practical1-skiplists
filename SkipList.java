//Giovanni Joubert u18009035
import java.util.Random;

@SuppressWarnings("unchecked")
public class SkipList < T extends Comparable < ? super T >> {

 public int maxLevel;
 public SkipListNode < T > [] root;
 private int[] powers;
 private Random rd = new Random();

 SkipList(int i) {
  maxLevel = i;
  root = new SkipListNode[maxLevel];
  powers = new int[maxLevel];
  for (int j = 0; j < i; j++)
   root[j] = null;
  choosePowers();
  rd.setSeed(1230456789);
 }

 SkipList() {
  this(4);
 }

 public void choosePowers() {
  powers[maxLevel - 1] = (2 << (maxLevel - 1)) - 1;
  for (int i = maxLevel - 2, j = 0; i >= 0; i--, j++)
   powers[i] = powers[i + 1] - (2 << j);
 }

 public int chooseLevel() {
  int i, r = Math.abs(rd.nextInt()) % powers[maxLevel - 1] + 1;
  for (i = 1; i < maxLevel; i++) {
   if (r < powers[i])
    return i - 1;
  }
  return i - 1;
 }

 public boolean isEmpty() {
  return root[0] == null;
 }

 public void insert(T key) {
  SkipListNode < T > [] curr = new SkipListNode[maxLevel];
  SkipListNode < T > [] prev = new SkipListNode[maxLevel];
  SkipListNode < T > newNode;
  int lvl, i;
  curr[maxLevel - 1] = root[maxLevel - 1];
  prev[maxLevel - 1] = null;
  for (lvl = maxLevel - 1; lvl >= 0; lvl--) {
   while (curr[lvl] != null && curr[lvl].key.compareTo(key) < 0) {
    prev[lvl] = curr[lvl];
    curr[lvl] = curr[lvl].next[lvl];
   }
   if (curr[lvl] != null && key.equals(curr[lvl].key))
    return;
   if (lvl > 0)
    if (prev[lvl] == null) {
     curr[lvl - 1] = root[lvl - 1];
     prev[lvl - 1] = null;
    }
   else {
    curr[lvl - 1] = prev[lvl].next[lvl - 1];
    prev[lvl - 1] = prev[lvl];
   }
  }
  lvl = chooseLevel();
  newNode = new SkipListNode < T > (key, lvl + 1);
  for (i = 0; i <= lvl; i++) {
   newNode.next[i] = curr[i];
   if (prev[i] == null)
    root[i] = newNode;
   else prev[i].next[i] = newNode;
  }
 }



 public T first() {
  return root[0].key;
 }

 public T last() {
  SkipListNode < T > [] temp = new SkipListNode[maxLevel];
  temp[0] = root[0];
  while (temp[0].next[0] != null) {
   temp[0] = temp[0].next[0];
  }
  return temp[0].key;
 }

 public T search(T key) {
  int selectedLevel;
  SkipListNode < T > previous, current;
  for (selectedLevel = maxLevel - 1; selectedLevel >= 0 && root[selectedLevel] == null; selectedLevel--);
  previous = current = root[selectedLevel];
  while (true) {
   if (key.equals(current.key))
    return current.key;
   else if (key.compareTo(current.key) < 0) {
    if (selectedLevel == 0)
     return null;
    else if (current == root[selectedLevel])
     current = root[--selectedLevel];
    else current = previous.next[--selectedLevel];
   } else {
    previous = current;
    if (current.next[selectedLevel] != null)
     current = current.next[selectedLevel];
    else {
     for (selectedLevel--; selectedLevel >= 0 && current.next[selectedLevel] == null; selectedLevel--);
     if (selectedLevel >= 0)
      current = current.next[selectedLevel];
     else return null;
    }
   }
  }
 }
}