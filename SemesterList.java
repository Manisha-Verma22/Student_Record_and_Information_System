import java.io.*;
import java.util.*;

/**
 * @author Jeshurun Ray Flores
 * @version 1.0
 * @see Hashtable
 */

public class SemesterList extends Hashtable implements Serializable {

    /**
     * Sorts the Semester List according to Semester Code
     * in ascending order.
     *
     * @return Vector containing sorted elements.
     */
    public Vector sort() {

        Set keys = this.keySet();
        Vector temp = new Vector(keys);

        Collections.sort(temp);

        Vector sorted = new Vector();

        for (int index = 0; index < temp.size(); index++) {
            sorted.add(this.get(temp.elementAt(index)));
        }

        return sorted;
    }
}

