package be.groupe18.windowing.strategies.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.groupe18.windowing.models.CompositeDouble;
import be.groupe18.windowing.models.PST;
import be.groupe18.windowing.models.QueryWindow;
import be.groupe18.windowing.models.Segment;

public class SimpleQueryStrategy implements QueryStrategy {
    @Override
    public List<Segment> query(PST tree, QueryWindow queryWindow) {
        Objects.requireNonNull(tree, "PST arrived null in " + this.getClass().getSimpleName());
        Objects.requireNonNull(queryWindow, "Query window arrived null in " + this.getClass().getSimpleName());
        List<Segment> results = new ArrayList<>();
        search(tree, queryWindow, results);
        return results;
    }

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

    private void checkAndReport(PST node, QueryWindow window, List<Segment> results) {
        if (node != null && node.getSegment() != null) {
            if (window.contains(node.getSegment())) {
                results.add(node.getSegment());
            }
        }
    }

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

    private boolean isValidNode(PST node) {
        return node != null && node.getSegment() != null;
    }
}
