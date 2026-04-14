package be.groupe18.windowing.application.strategy.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.groupe18.windowing.domain.model.PST;
import be.groupe18.windowing.domain.model.QueryWindow;
import be.groupe18.windowing.domain.model.Segment;

/**
 * A concrete simple query implementation of the query strategy
 */
public class SimpleQueryStrategy implements QueryStrategy {
    @Override
    public List<Segment> query(PST tree, QueryWindow queryWindow) {
        Objects.requireNonNull(tree, "PST arrived null in " + this.getClass().getSimpleName());
        Objects.requireNonNull(queryWindow, "Query window arrived null in " + this.getClass().getSimpleName());
        List<Segment> results = new ArrayList<>();
        search(tree, queryWindow, results);
        return results;
    }

    /**
     * The main algorithm ""orchestrator"" which perform the general structure of the querying algorithm
     * @param node The root node of the {@link PST}
     * @param window The query window on which to search the segments
     * @param results The list of segment on which to report those within the query window
     */
    private void search(PST node, QueryWindow window, List<Segment> results) {
        if (!isValidNode(node)) {
            return;
        }

        PST splitNode = findSplitNode(node, window.getOriginMin(), window.getOriginMax());
        if(splitNode == null) return;

        checkAndReport(splitNode, window, results);
        if(splitNode.isLeaf()) return;

        PST vLeft = splitNode.getLeftChild();
        while (vLeft != null) {
            checkAndReport(vLeft, window, results);
            if (vLeft.isLeaf() || vLeft.getSegment() == null) break;

            // do if the search path goes left at ν
            if (vLeft.getMedian() >= window.getOriginMin()) {
                reportInSubtree(vLeft.getRightChild(), window, results);
                vLeft = vLeft.getLeftChild();
            } else {
                vLeft = vLeft.getRightChild();
            }
        }

        PST vRight = splitNode.getRightChild();
        while (vRight != null) {
            checkAndReport(vRight, window, results);
            if (vRight.isLeaf() || vRight.getSegment() == null) break;

            // do if the search path goes right at ν
            if (vRight.getMedian() <= window.getOriginMax()) {
                reportInSubtree(vRight.getLeftChild(), window, results);
                vRight = vRight.getRightChild();
            } else {
                vRight = vRight.getLeftChild();
            }
        }
    }

    /**
     * Go down the tree until the searching path splits up
     * @param node The current node on which to go down
     * @param originMin The min origin of the query window
     * @param originMax The max origin of the query window
     * @return The node where the search path splits
     */
    private PST findSplitNode(PST node, double originMin, double originMax) {
        while (node != null && !node.isLeaf()) {
            // Going left
            if (node.getMedian() >= originMax) {
                node = node.getLeftChild();
            }
            // Going right
            else if (originMin > node.getMedian()) {
                node = node.getRightChild();
            }
            // Splitting
            else {
                break;
            }
        }
        return node;
    }

    /**
     * Check if a node is within the query window and if it is reports it
     * @param node The node to perform the check on
     * @param window The query window that provided the bounds to check
     * @param results The list on which to report the segment
     */
    private void checkAndReport(PST node, QueryWindow window, List<Segment> results) {
        if (node != null && node.getSegment() != null) {
            if (window.contains(node.getSegment())) {
                results.add(node.getSegment());
            }
        }
    }

    /**
     * Report all nodes which are in the correct interval, using this method is done when we are certain the origin
     * is in the correct bounds
     * @param node The current node on which to run report logic
     * @param window The query window that provided the bounds for the interval
     * @param results The list on which to report the segments
     */
    private void reportInSubtree(PST node, QueryWindow window, List<Segment> results) {
        if (!isValidNode(node)) {
            return;
        }

        Segment segment = node.getSegment();

        if (window.isIntervalTooBig(segment.getInterval())) {
            return;
        }

        checkAndReport(node, window, results);

        if (!node.isLeaf()) {
            reportInSubtree(node.getLeftChild(), window, results);
            reportInSubtree(node.getRightChild(), window, results);
        }
    }

    /**
     * Simple helper method for defensive programming
     * @param node The node on which to perform the checks
     * @return True if the node is valid, false otherwise
     */
    private boolean isValidNode(PST node) {
        return node != null && node.getSegment() != null;
    }
}
