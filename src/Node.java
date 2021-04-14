/**
 * This is the self-defined variable Node
 * @author *%$#@ (3104969674@qq.com)
 */
public class Node {

    private int nodeX, nodeY;

    /**
     * This class draws the main frame where egg and snake runs
     * @param nodeX draw rows
     * @param nodeY draw columns
     */
    public Node(int nodeX, int nodeY){
        this.nodeX = nodeX;
        this.nodeY = nodeY;
    }

    /**
     * The function that gets the row of coordination
     * @return nodeX coordination
     */
    public int getNodeX() {
        return nodeX;
    }

    /**
     * The function that gets the column of coordination
     * @return noteY coordination
     */
    public int getNodeY() {
        return nodeY;
    }
    /**
     * The function that sets the row of coordination
     * @param nodeX set the nodeX
     */
    public void setNodeX(int nodeX) {
        this.nodeX = nodeX;
    }

    /**
     * The function that sets the column of coordination
     * @param nodeY set the nodeY
     */
    public void setNodeY(int nodeY) {
        this.nodeY = nodeY;
    }

}
