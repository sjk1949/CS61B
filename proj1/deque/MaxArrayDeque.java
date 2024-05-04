/** ArrayDeque with max method
 *  @author sjk1949
 */

package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparator;

    /** Create a MaxArrayDeque with the given Comparator */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    /** Return the maximum element in the deque as governed by the previously given Comparator
     * If MaxArrayDeque is empty, return null
     */
    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        T maxItem = null;
        T thisItem;
        for (int i = 0; i < size(); i++) {
            thisItem = get(i);
            if (maxItem == null || thisItem == null) {
                if (maxItem == null) {
                    maxItem = get(i);
                }
            } else {
                if (c.compare(thisItem, maxItem) > 0) {
                    maxItem = thisItem;
                }
            }
        }
        return maxItem;
    }
}
