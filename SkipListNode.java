//Giovanni Joubert u18009035
public class SkipListNode<T>
{

	public T key;
	public SkipListNode<T>[] next;

	@SuppressWarnings("unchecked")
	SkipListNode(T i, int n)
	{
		key = i;
		next = new SkipListNode[n];

		for (int j = 0; j < n; j++)
			next[j] = null;
	}

}